// TillingAction.java (Modifikasi)

public class TillingAction extends Action {
    public static final int ENERGY_COST = 5;
    public static final int TIME_COST_MINUTES = 5;

    private FarmMap farmMapContext;
    private Tile targetTileObject;
    private String failureReason;

    public TillingAction(Player player, WorldState world, FarmMap farmMapContext, Tile targetTileObject) {
        super(player, world);
        this.farmMapContext = farmMapContext;
        this.targetTileObject = targetTileObject;
    }

    @Override
    protected boolean validate() {
        // Validasi 0: Objek penting tidak null
        if (farmMapContext == null) {
            this.failureReason = "Konteks peta ladang tidak tersedia.";
            return false;
        }
        if (targetTileObject == null) {
            this.failureReason = "Tidak ada petak tanah (objek Tile) yang dipilih untuk dicangkul.";
            return false;
        }

        // Validasi 1: Pemain harus memiliki "Hoe"
        if (!player.hasItemInInventory("Hoe")) {
            this.failureReason = "Kamu membutuhkan 'Hoe' (Cangkul) untuk mencangkul tanah!";
            return false;
        }

        // Validasi 2: Energi Pemain
        if ((player.getEnergy() - ENERGY_COST) < Player.MIN_ENERGY) {
            this.failureReason = "Energi tidak cukup untuk mencangkul. Kamu akan pingsan!";
            return false;
        }

        // Validasi 3: Lokasi Pemain
        if (!player.getLocation().equalsIgnoreCase(player.getFarmName())) {
            this.failureReason = "Kamu hanya bisa mencangkul di ladangmu (" + player.getFarmName() + ").";
            return false;
        }

        // Validasi 4: Kondisi Tile Object
        if (targetTileObject.getType() != Tile.TileType.Soil) {
            this.failureReason = "Petak ini (objek Tile) tidak bisa dicangkul. Tipe saat ini: " + 
                                 targetTileObject.getType().getDisplayName() + ". Seharusnya Soil.";
            return false;
        }

        // Validasi 5: Sinkronisasi dengan Char Map di FarmMap
        char charOnMap = farmMapContext.getCharMapTile(farmMapContext.getPlayerX(), farmMapContext.getPlayerY());
        if (charOnMap != FarmMap.TILLABLE_LAND) { // TILLABLE_LAND biasanya '.'
            this.failureReason = "Tanah di peta (" + farmMapContext.getPlayerX() + "," + farmMapContext.getPlayerY() + 
                                 ") bukan tanah yang bisa dicangkul (simbol peta: '" + charOnMap + "').";
            return false;
        }
        
        return true;
    }

    @Override
    protected void performAction() {
        System.out.println(player.getName() + " menggunakan cangkulnya dan mulai mencangkul tanah di (" +
                           farmMapContext.getPlayerX() + ", " + farmMapContext.getPlayerY() + ")...");
    }

    @Override
    protected void applyEffects() {
        // 1. Ubah representasi karakter di FarmMap
        farmMapContext.interact(); // Asumsi ini mengubah '.' menjadi 't' jika valid

        // 2. Ubah tipe pada objek Tile yang sebenarnya
        this.targetTileObject.setType(Tile.TileType.Tilled);

        // 3. Kurangi energi pemain
        player.setEnergy(player.getEnergy() - ENERGY_COST);

        // 4. Majukan waktu game
        int dayBefore = world.getCurrentTime().getDay();
        world.getCurrentTime().advanceMinutes(TIME_COST_MINUTES);
        if (world.getCurrentTime().getDay() > dayBefore) {
            world.handleNewDay();
        }
    }

    @Override
    protected String getSuccessMessage() {
        return "Tanah berhasil dicangkul dengan 'Hoe' dan sekarang menjadi '" + Tile.TileType.Tilled.getDisplayName() + 
               "'. (-" + ENERGY_COST + " Energi, +" + TIME_COST_MINUTES + " menit)";
    }

    @Override
    protected String getFailureMessage() {
        return this.failureReason;
    }
}