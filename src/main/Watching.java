public class Watching implements Action {
    private Time time;
    private WorldState worldState;

    public Watching(Time time, WorldState worldState) {
        this.time = time;
        this.worldState = worldState;
    }

    @Override
    public void execute(Player player) {
        // Cek apakah player cukup energi
        if (player.getEnergy() < getEnergyCost()) {
            System.out.println("Energi kamu tidak cukup untuk menonton TV.");
            return;
        }

        // Tambah waktu dan kurangi energi
        time.advanceMinutes(getTimeCost());
        player.setEnergy(player.getEnergy() - getEnergyCost());

        // Ambil cuaca hari ini dari worldState
        Weather todayWeather = worldState.getCurrentWeather();
        System.out.println("Kamu menonton TV selama 15 menit.");
        System.out.println("Energi berkurang 5. Sisa energi: " + player.getEnergy());
        System.out.println("Cuaca hari ini: " + todayWeather.getDisplayName());
    }

    @Override
    public int getEnergyCost() {
        return 5;
    }

    @Override
    public int getTimeCost() {
        return 15;
    }

    @Override
    public boolean canExecute(Player player) {
        // Hanya dapat menonton jika di rumah dan energi cukup
        return player.getLocation().equalsIgnoreCase("house") && player.getEnergy() >= getEnergyCost();
    }
}