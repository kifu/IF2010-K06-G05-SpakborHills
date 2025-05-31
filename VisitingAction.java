public class VisitingAction extends Action {
    public static final int ENERGY_COST = 10;  
    public static final int TIME_COST_MINUTES = 15; 

    private String areaNameToVisit; 
    private String failureReason;


    public VisitingAction(Player player, WorldState world, String areaNameToVisit) {
        super(player, world);
        this.areaNameToVisit = areaNameToVisit; // areaNameToVisit tidak boleh null di versi ini
        if (areaNameToVisit == null || areaNameToVisit.trim().isEmpty()){
            // Untuk konsistensi, areaNameToVisit harus selalu ada.
            // Jika ingin logika "null" untuk keluar farm, pemanggil harus meneruskan "WorldMap".
            throw new IllegalArgumentException("areaNameToVisit tidak boleh null atau kosong.");
        }
    }

    @Override
    protected boolean validate() {
        // Validasi dasar: hanya energi.
        if (player.getEnergy() < ENERGY_COST) {
            this.failureReason = "Energi tidak cukup! Minimal " + ENERGY_COST + " energi dibutuhkan untuk visiting.";
            return false;
        }
        return true;
    }

    @Override
    protected void performAction() {
        // Pesan sederhana berdasarkan tujuan
        if (this.areaNameToVisit.equalsIgnoreCase("WorldMap")) {
            System.out.println(player.getName() + " berjalan keluar dari ladangnya menuju World Map...");
        } else if (this.areaNameToVisit.equalsIgnoreCase(player.getFarmName())) {
            System.out.println(player.getName() + " kembali ke ladangnya, " + player.getFarmName() + "...");
        } else {
            System.out.println(player.getName() + " mengunjungi area '" + this.areaNameToVisit + "'...");
        }
    }

    @Override
    protected void applyEffects() {
        // 1. Kurangi energi
        player.setEnergy(player.getEnergy() - ENERGY_COST);

        // 2. Majukan waktu
        int dayBefore = world.getCurrentTime().getDay();
        world.getCurrentTime().advanceMinutes(TIME_COST_MINUTES);
        if (world.getCurrentTime().getDay() > dayBefore) {
            world.handleNewDay();
        }

        // 3. Ubah lokasi pemain
        player.setLocation(this.areaNameToVisit);
        System.out.println("Kamu sekarang berada di: " + this.areaNameToVisit);
    }

    @Override
    protected String getSuccessMessage() {
        return "Berhasil berpindah ke " + this.areaNameToVisit + ". (-" + ENERGY_COST + " Energi, +" + TIME_COST_MINUTES + " menit)";
    }

    @Override
    protected String getFailureMessage() {
        return this.failureReason;
    }
}