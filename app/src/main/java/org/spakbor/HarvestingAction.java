import java.util.List;

public class HarvestingAction extends Action {
    public static final int ENERGY_COST = 5; 
    public static final int TIME_COST_MINUTES = 5; 

    private FarmMap farmMapContext;
    private Tile targetTileObject;
    private List<Item> allGameItems; 

    private String failureReason;
    private Crops harvestedCrop;      
    private int quantityHarvested;   

    public HarvestingAction(Player player, WorldState world, FarmMap farmMapContext, 
                            Tile targetTileObject, List<Item> allGameItems) {
        super(player, world);
        this.farmMapContext = farmMapContext;
        this.targetTileObject = targetTileObject;
        this.allGameItems = allGameItems;
    }

    @Override
    protected boolean validate() {
        if (farmMapContext == null || targetTileObject == null || allGameItems == null) {
            this.failureReason = "Konteks atau data game tidak lengkap untuk panen.";
            return false;
        }

        if ((player.getEnergy() - ENERGY_COST) < Player.MIN_ENERGY) {
            this.failureReason = "Energi tidak cukup untuk memanen. Kamu akan pingsan!";
            return false;
        }

        if (!player.getLocation().equalsIgnoreCase(player.getFarmName())) {
            this.failureReason = "Kamu hanya bisa memanen di ladangmu (" + player.getFarmName() + ").";
            return false;
        }

        if (targetTileObject.getType() != Tile.TileType.Planted) {
            this.failureReason = "Tidak ada tanaman untuk dipanen di petak ini (bukan Planted). Tipe saat ini: " +
                                 targetTileObject.getType().getDisplayName();
            return false;
        }
        Seeds plantedSeed = targetTileObject.getPlanted();
        if (plantedSeed == null) {
            this.failureReason = "Petak ini ditandai Planted tapi tidak ada info benih yang ditanam.";
            return false;
        }

        if (targetTileObject.daysGrown() < plantedSeed.getHarvestDuration()) {
            this.failureReason = "Tanaman '" + plantedSeed.getName() + "' belum siap panen. (Tumbuh: " +
                                 targetTileObject.daysGrown() + "/" + plantedSeed.getHarvestDuration() + " hari)";
            return false;
        }

        char charOnMap = farmMapContext.getCharMapTile(farmMapContext.getPlayerX(), farmMapContext.getPlayerY());
        if (charOnMap != FarmMap.PLANTED_LAND) { 
            this.failureReason = "Petak di peta (" + farmMapContext.getPlayerX() + "," + farmMapContext.getPlayerY() +
                                 ") bukan tanah yang ditanami (simbol peta: '" + charOnMap + "').";
            return false;
        }
        
        return true;
    }

    @Override
    protected void performAction() {
        Seeds plantedSeed = this.targetTileObject.getPlanted();
        String seedName = plantedSeed.getName(); // Misal "Parsnip Seeds"
        String cropName = seedName.replace(" Seeds", "").replace(" Seed", ""); // Menjadi "Parsnip"

        // Cari objek Crops yang sesuai dari allGameItems
        this.harvestedCrop = null;
        for (Item item : this.allGameItems) {
            if (item instanceof Crops && item.getName().equalsIgnoreCase(cropName)) {
                this.harvestedCrop = (Crops) item;
                break;
            }
        }

        if (this.harvestedCrop == null) {
            this.failureReason = "Tidak ditemukan data tanaman (Crops) untuk '" + cropName + "' di daftar item game.";
            // Ini akan menyebabkan applyEffects tidak melakukan apa-apa jika dicek di sana.
            System.out.println(player.getName() + " mencoba memanen, tapi ada masalah dengan data tanaman '" + cropName + "'.");
        } else {
            this.quantityHarvested = this.harvestedCrop.getCropPerHarvest();
            System.out.println(player.getName() + " memanen " + this.harvestedCrop.getName() + "...");
        }
    }

    @Override
    protected void applyEffects() {
        if (this.harvestedCrop == null || this.quantityHarvested <= 0) {
            // Jika performAction gagal menemukan crop, return tanpa efek
            return;
        }

        // Tambahkan hasil panen ke inventory pemain
        player.getInventory().addItem(this.harvestedCrop, this.quantityHarvested);

        // Ubah state objek Tile kembali ke Soil 
        this.targetTileObject.setType(Tile.TileType.Soil);
        this.targetTileObject.setPlanted(null); // Hapus tanaman dari tile
        this.targetTileObject.resetDaysGrown(); // Reset hari tumbuh

        // Ubah representasi karakter di FarmMap kembali menjadi TILLABLE_LAND ('.')
        farmMapContext.setCharMapTile(farmMapContext.getPlayerX(), farmMapContext.getPlayerY(), FarmMap.TILLABLE_LAND);

        // Kurangi energi pemain
        player.setEnergy(player.getEnergy() - ENERGY_COST);

        // Majukan waktu game
        int dayBefore = world.getCurrentTime().getDay();
        world.getCurrentTime().advanceMinutes(TIME_COST_MINUTES);
        if (world.getCurrentTime().getDay() > dayBefore) {
            world.handleNewDay();
        }
    }

    @Override
    protected String getSuccessMessage() {
        if (this.harvestedCrop != null && this.quantityHarvested > 0) {
            return "Berhasil memanen " + this.quantityHarvested + " " + this.harvestedCrop.getName() + "!" +
                   " Petak tanah kembali menjadi Soil." +
                   " (-" + ENERGY_COST + " Energi, +" + TIME_COST_MINUTES + " menit)";
        } else if (this.failureReason != null && !this.failureReason.isEmpty()){ 
             return "Gagal memanen: " + this.failureReason;
        }
        return "Proses panen selesai dengan hasil tidak terduga."; 
    }

    @Override
    protected String getFailureMessage() {
        return this.failureReason; 
    }
}