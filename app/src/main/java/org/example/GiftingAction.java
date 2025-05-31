package org.example;
public class GiftingAction extends Action {

    private static final int ENERGY_COST = 5;
    private static final int TIME_COST_MINUTES = 10;
    
    private NPC target;
    private Item gift;
    private String failureReason;

    public GiftingAction(Player player, WorldState world, NPC target, Item gift) {
        super(player, world);
        this.target = target;
        this.gift = gift;
    }

    @Override
    protected boolean validate() {
        // Cek apakah item yang diberikan null
        if (gift == null) {
            this.failureReason = "Tidak ada item yang dipilih untuk diberikan.";
            return false;
        }

        // Tentukan lokasi yang dibutuhkan
        String requiredLocation;
        if (target.getName().equalsIgnoreCase("Emily")) {
            requiredLocation = "Store";
        } else {
            requiredLocation = target.getName() + " House";
        }

        // Validasi lokasi pemain
        if (!player.getLocation().equalsIgnoreCase(requiredLocation)) {
            this.failureReason = "Kamu harus berada di " + requiredLocation + " untuk memberikan hadiah kepada " + target.getName() + ".";
            return false;
        }

        // Validasi kepemilikan item
        if (player.getInventory().getItemQuantity(this.gift) <= 0) {
            this.failureReason = "Kamu tidak memiliki " + gift.getName() + " di inventory.";
            return false;
        }

        // Validasi energi pemain
        if (player.getEnergy() < ENERGY_COST) {
            this.failureReason = "Energi kamu tidak cukup untuk memberikan hadiah.";
            return false;
        }

        return true;
    }

    @Override
    protected void performAction() {
        // Menampilkan pesan proses
        System.out.println(player.getName() + " memberikan " + gift.getName() + " kepada " + target.getName() + "...");
    }

    @Override
    protected void applyEffects() {
        // 1. NPC menerima hadiah (logika heart point ada di dalam metode ini)
        target.receiveGift(this.gift);

        // 2. Hapus item dari inventory pemain
        player.getInventory().removeItem(this.gift, 1);

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
        return "Hadiah telah diberikan. (-" + ENERGY_COST + " Energi, +" + TIME_COST_MINUTES + " menit)";
    }

    @Override
    protected String getFailureMessage() {
        return this.failureReason;
    }
}