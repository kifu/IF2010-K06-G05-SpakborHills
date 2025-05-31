public class WatchingAction extends Action {
    private static final int ENERGY_COST = 5;
    private static final int TIME_COST_MINUTES = 15;
    private static final String REQUIRED_LOCATION = "House"; // Lokasi di mana TV berada

    private String failureReason;

    public WatchingAction(Player player, WorldState world) {
        super(player, world);
    }

    @Override
    protected boolean validate() {
        if (!player.getLocation().equalsIgnoreCase(REQUIRED_LOCATION)) {
            this.failureReason = "Kamu harus berada di '" + REQUIRED_LOCATION + "' untuk menonton TV.";
            return false;
        }

        if (player.getEnergy() < ENERGY_COST) {
            this.failureReason = "Energi kamu tidak cukup untuk menonton TV.";
            return false;
        }
        
        return true;
    }

    @Override
    protected void performAction() {
        System.out.println(player.getName() + " menyalakan TV dan mulai menonton...");
    }

    @Override
    protected void applyEffects() {
        player.setEnergy(player.getEnergy() - ENERGY_COST);

        int dayBefore = world.getCurrentTime().getDay();
        world.getCurrentTime().advanceMinutes(TIME_COST_MINUTES);
        
        if (world.getCurrentTime().getDay() > dayBefore) {
            world.handleNewDay();
        }

        Weather todayWeather = world.getCurrentWeather();
        System.out.println("Setelah menonton, kamu jadi tahu dari berita TV kalau cuaca hari ini adalah: " + todayWeather.getDisplayName());
    }

    @Override
    protected String getSuccessMessage() {
        return "Selesai menonton TV. Energi berkurang " + ENERGY_COST + ". Waktu bertambah " + TIME_COST_MINUTES + " menit.";
    }

    @Override
    protected String getFailureMessage() {
        return this.failureReason;
    }
}