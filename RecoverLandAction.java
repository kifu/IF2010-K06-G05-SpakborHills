public class RecoverLandAction extends Action {
    public static final int ENERGY_COST = 5; // Sesuai RecoverLand.java lama
    public static final int TIME_COST_MINUTES = 5; // Sesuai RecoverLand.java lama

    private FarmMap farmMapContext;  // Konteks FarmMap untuk mengubah char map
    private Tile targetTileObject;   // Objek Tile spesifik yang akan diubah tipenya
    private String failureReason;

    public RecoverLandAction(Player player, WorldState world, FarmMap farmMapContext, Tile targetTileObject) {
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
            this.failureReason = "Tidak ada petak tanah (objek Tile) yang dipilih untuk dipulihkan.";
            return false;
        }

        // Validasi 1: Energi Pemain
        if ((player.getEnergy() - ENERGY_COST) < Player.MIN_ENERGY) {
            this.failureReason = "Energi tidak cukup untuk memulihkan tanah. Kamu akan pingsan!";
            return false;
        }

        // Validasi 2: Lokasi Pemain
        if (!player.getLocation().equalsIgnoreCase(player.getFarmName())) {
            this.failureReason = "Kamu hanya bisa memulihkan tanah di ladangmu (" + player.getFarmName() + ").";
            return false;
        }

        // Validasi 3: Kondisi Tile Object yang akan di-recover
        if (targetTileObject.getType() != Tile.TileType.Tilled) {
            this.failureReason = "Petak ini (objek Tile) tidak bisa dipulihkan ke tanah biasa. Tipe saat ini: " +
                                 targetTileObject.getType().getDisplayName() + ". Seharusnya Tilled.";
            return false;
        }

        // Validasi 4: Sinkronisasi dengan Char Map di FarmMap
        char charOnMap = farmMapContext.getCharMapTile(farmMapContext.getPlayerX(), farmMapContext.getPlayerY());
        if (charOnMap != FarmMap.TILLED_LAND) { // TILLED_LAND biasanya 't'
            this.failureReason = "Tanah di peta (" + farmMapContext.getPlayerX() + "," + farmMapContext.getPlayerY() +
                                 ") bukan tanah yang sudah dicangkul (simbol peta: '" + charOnMap + "').";
            return false;
        }
        
        return true;
    }

    @Override
    protected void performAction() {
        System.out.println(player.getName() + " memulihkan petak tanah di (" +
                           farmMapContext.getPlayerX() + ", " + farmMapContext.getPlayerY() + ") menjadi tanah biasa...");
    }

    @Override
    protected void applyEffects() {
        // 1. Ubah tipe pada objek Tile yang sebenarnya menjadi Soil
        this.targetTileObject.setType(Tile.TileType.Soil);

        // 2. Ubah representasi karakter di FarmMap kembali menjadi TILLABLE_LAND ('.')
        farmMapContext.setCharMapTile(farmMapContext.getPlayerX(), farmMapContext.getPlayerY(), FarmMap.TILLABLE_LAND);

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
        return "Tanah berhasil dipulihkan menjadi '" + Tile.TileType.Soil.getDisplayName() +
               "'. (-" + ENERGY_COST + " Energi, +" + TIME_COST_MINUTES + " menit)";
    }

    @Override
    protected String getFailureMessage() {
        return this.failureReason;
    }
}