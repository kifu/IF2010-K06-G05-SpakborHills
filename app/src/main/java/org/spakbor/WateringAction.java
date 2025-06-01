package org.spakbor;

public class WateringAction extends Action {
    public static final int ENERGY_COST = 5; 
    public static final int TIME_COST_MINUTES = 5; 

    private Tile targetTileObject;   
    private String failureReason;

    public WateringAction(Player player, WorldState world, Tile targetTileObject) {
        super(player, world);
        this.targetTileObject = targetTileObject;
    }

    @Override
    protected boolean validate() {
        if (targetTileObject == null) {
            this.failureReason = "Tidak ada petak tanah (objek Tile) yang dipilih untuk disiram.";
            return false;
        }

        if (!player.hasItemInInventory("Watering Can")) { 
            this.failureReason = "Kamu membutuhkan 'Watering Can' untuk menyiram!";
            return false;
        }

        if ((player.getEnergy() - ENERGY_COST) < Player.MIN_ENERGY) {
            this.failureReason = "Energi tidak cukup untuk menyiram. Kamu akan pingsan!";
            return false;
        }

        if (!player.getLocation().equalsIgnoreCase(player.getFarmName())) {
            this.failureReason = "Kamu hanya bisa menyiram di ladangmu (" + player.getFarmName() + ").";
            return false;
        }

        if (targetTileObject.isWatered()) {
            this.failureReason = "Petak tanah ini sudah basah (disiram).";
            return false;
        }

        Tile.TileType currentTileType = targetTileObject.getType();
        if (currentTileType != Tile.TileType.Tilled && currentTileType != Tile.TileType.Planted) {
            this.failureReason = "Hanya tanah yang sudah dicangkul (Tilled) atau sudah ditanami (Planted) yang bisa disiram. Tipe saat ini: " +
                                 currentTileType.getDisplayName();
            return false;
        }
        
        return true;
    }

    @Override
    protected void performAction() {
        System.out.println(player.getName() + " menggunakan Watering Can dan menyiram petak tanah...");
    }

    @Override
    protected void applyEffects() {
        // Ubah status isWatered pada objek Tile menjadi true
        this.targetTileObject.setIsWatered(true);

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
        return "Petak tanah berhasil disiram! (-" + ENERGY_COST + " Energi, +" + TIME_COST_MINUTES + " menit)";
    }

    @Override
    protected String getFailureMessage() {
        return this.failureReason;
    }
}