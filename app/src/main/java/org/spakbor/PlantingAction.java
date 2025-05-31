import java.util.Scanner; 

public class PlantingAction extends Action {
    public static final int ENERGY_COST = 5; 
    public static final int TIME_COST_MINUTES = 5; 

    private FarmMap farmMapContext;
    private Tile targetTileObject;
    private String failureReason;

    private Seeds selectedSeedToPlant; 
    private boolean cancelledOrFailedSelection; 

    public PlantingAction(Player player, WorldState world, FarmMap farmMapContext, Tile targetTileObject) {
        super(player, world);
        this.farmMapContext = farmMapContext;
        this.targetTileObject = targetTileObject;
        this.cancelledOrFailedSelection = false;
    }

    @Override
    protected boolean validate() {
        if (farmMapContext == null || targetTileObject == null) {
            this.failureReason = "Konteks peta ladang atau petak tanah tidak valid.";
            return false;
        }

        if ((player.getEnergy() - ENERGY_COST) < Player.MIN_ENERGY) {
            this.failureReason = "Energi tidak cukup untuk menanam. Kamu akan pingsan!";
            return false;
        }

        if (!player.getLocation().equalsIgnoreCase(player.getFarmName())) {
            this.failureReason = "Kamu hanya bisa menanam di ladangmu (" + player.getFarmName() + ").";
            return false;
        }

        // Kondisi Tile Object (harus Tilled)
        if (targetTileObject.getType() != Tile.TileType.Tilled) {
            this.failureReason = "Kamu hanya bisa menanam di tanah yang sudah dicangkul (Tilled). Tipe saat ini: " +
                                 targetTileObject.getType().getDisplayName();
            return false;
        }

        // Sinkronisasi dengan Char Map di FarmMap (harus TILLED_LAND)
        char charOnMap = farmMapContext.getCharMapTile(farmMapContext.getPlayerX(), farmMapContext.getPlayerY());
        if (charOnMap != FarmMap.TILLED_LAND) { 
            this.failureReason = "Tanah di peta (" + farmMapContext.getPlayerX() + "," + farmMapContext.getPlayerY() +
                                 ") bukan tanah yang sudah dicangkul (simbol peta: '" + charOnMap + "').";
            return false;
        }

        // Pemain harus punya setidaknya satu jenis benih
        boolean hasAnySeeds = false;
        for (Item item : player.getInventory().getInventory().keySet()) {
            if (item instanceof Seeds) {
                hasAnySeeds = true;
                break;
            }
        }
        if (!hasAnySeeds) {
            this.failureReason = "Kamu tidak memiliki benih apapun di inventory untuk ditanam.";
            return false;
        }
        
        return true;
    }

    @Override
    protected void performAction() {
        Scanner scanner = new Scanner(System.in); 
        Inventory inventory = player.getInventory();

        System.out.println("\n--- Memulai Proses Penanaman ---");
        System.out.println("Pilih benih dari inventory untuk ditanam di petak (" + 
                           farmMapContext.getPlayerX() + ", " + farmMapContext.getPlayerY() + "):");
        inventory.displayInventory(); 

        while (true) {
            System.out.print("Masukkan nama benih yang ingin ditanam (atau ketik 'batal'): ");
            String itemNameInput = scanner.nextLine().trim();

            if (itemNameInput.equalsIgnoreCase("batal")) {
                this.cancelledOrFailedSelection = true;
                this.failureReason = "Penanaman dibatalkan oleh pengguna."; 
                return;
            }

            Item chosenItem = inventory.getItemByName(itemNameInput); 

            if (chosenItem == null) {
                System.out.println("Item '" + itemNameInput + "' tidak ditemukan di inventory. Coba lagi.");
            } else if (!(chosenItem instanceof Seeds)) {
                System.out.println("'" + chosenItem.getName() + "' bukan benih. Pilih benih yang valid.");
            } else {
                Seeds currentSeed = (Seeds) chosenItem;
                // Validasi musim benih
                if (currentSeed.getSeason() != world.getCurrentSeason() && currentSeed.getSeason() != null) {
                    System.out.println("Benih '" + currentSeed.getName() + "' tidak cocok untuk musim saat ini (" + 
                                       world.getCurrentSeason().getDisplayName() + "). Musim benih: " + 
                                       currentSeed.getSeason().getDisplayName());
                } else {
                    this.selectedSeedToPlant = currentSeed;
                    System.out.println("Kamu memilih untuk menanam: " + this.selectedSeedToPlant.getName());
                    return; // Keluar loop jika benih valid dipilih
                }
            }
        }
        // Sebaiknya jangan scanner.close() jika dibuat dari System.in
    }

    @Override
    protected void applyEffects() {
        if (this.cancelledOrFailedSelection || this.selectedSeedToPlant == null) {
            return; 
        }

        // Kurangi benih dari inventory pemain
        player.getInventory().removeItem(this.selectedSeedToPlant, 1);

        // Ubah state objek Tile
        this.targetTileObject.setType(Tile.TileType.Planted);
        this.targetTileObject.setPlanted(this.selectedSeedToPlant);
        this.targetTileObject.resetDaysGrown(); // Reset hari tumbuh menjadi 0

        // Ubah representasi karakter di FarmMap
        farmMapContext.interact(); 

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
        if (this.cancelledOrFailedSelection) {
            return this.failureReason; 
        }
        if (this.selectedSeedToPlant != null) {
            return "Berhasil menanam " + this.selectedSeedToPlant.getName() + " di petak tanah!" +
                   " (-" + ENERGY_COST + " Energi, +" + TIME_COST_MINUTES + " menit)";
        }
        return "Proses penanaman selesai."; 
    }

    @Override
    protected String getFailureMessage() {
        return this.failureReason; 
    }
}