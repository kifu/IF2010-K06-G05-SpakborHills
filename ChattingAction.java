public class ChattingAction extends Action {

    private static final int ENERGY_COST = 10;
    private static final int TIME_COST_MINUTES = 10;
    private static final int HEART_POINT_GAIN = 10;

    private NPC target;
    private String failureReason;

    public ChattingAction(Player player, WorldState world, NPC target) {
        super(player, world);
        this.target = target;
    }

    @Override
    protected boolean validate() {
        // Tentukan lokasi yang dibutuhkan untuk bertemu NPC
        String requiredLocation;
        if (target.getName().equalsIgnoreCase("Emily")) {
            requiredLocation = "Store";
        } else {
            requiredLocation = target.getName() + " House";
        }

        // Validasi lokasi pemain
        if (!player.getLocation().equalsIgnoreCase(requiredLocation)) {
            this.failureReason = "Kamu harus berada di " + requiredLocation + " untuk mengobrol dengan " + target.getName() + ".";
            return false;
        }

        // Validasi energi pemain
        if (player.getEnergy() < ENERGY_COST) {
            this.failureReason = "Energi kamu tidak cukup untuk mengobrol.";
            return false;
        }
        
        return true;
    }

    @Override
    protected void performAction() {
        // Metode ini memanggil metode chat dari NPC,
        // yang akan menampilkan pesan "Ngobrol bareng..." dan menaikkan counter.
        target.chat();
    }

    @Override
    protected void applyEffects() {
        // 1. Tambahkan Heart Point ke NPC
        target.setHeartPoint(target.getHeartPoint() + HEART_POINT_GAIN);

        // 2. Kurangi energi pemain
        player.setEnergy(player.getEnergy() - ENERGY_COST);

        // 3. Majukan waktu game dan tangani pergantian hari
        int dayBefore = world.getCurrentTime().getDay();
        world.getCurrentTime().advanceMinutes(TIME_COST_MINUTES);
        if (world.getCurrentTime().getDay() > dayBefore) {
            world.handleNewDay();
        }
    }

    @Override
    protected String getSuccessMessage() {
        return "Obrolan yang menyenangkan! (+" + HEART_POINT_GAIN + " Heart Points, -" + ENERGY_COST + " Energi, +" + TIME_COST_MINUTES + " menit)";
    }

    @Override
    protected String getFailureMessage() {
        return this.failureReason;
    }
}