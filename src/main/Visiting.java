public class Visiting {
    private FarmMap farmMap;
    private WorldMap worldMap;
    private WorldState worldState;

    public Visiting(FarmMap farmMap, WorldMap worldMap, WorldState worldState) {
        this.farmMap = farmMap;
        this.worldMap = worldMap;
        this.worldState = worldState;
    }

    /**
     * Aksi visiting: hanya bisa dilakukan di pojok kanan atas FarmMap.
     * Efek: -10 energi, +15 menit, player berpindah ke WorldMap di Village.
     */
    public void execute(Player player) {
        // Validasi posisi: hanya boleh di pojok kanan atas
        if (!farmMap.canVisit()) {
            System.out.println("Kamu harus berada di pojok kanan atas FarmMap untuk bisa visiting ke World Map!");
            return;
        }
        // Validasi energi cukup
        if (player.getEnergy() < 10) {
            System.out.println("Energi tidak cukup! Minimal 10 energi dibutuhkan untuk visiting.");
            return;
        }
        // Efek visiting
        worldState.getCurrentTime().advanceMinutes(15);
        player.setEnergy(player.getEnergy() - 10);
        player.setLocation("WorldMap");
        worldMap.movePlayerToVillage();
        System.out.println("Kamu telah visiting ke World Map (Village). -10 energi, +15 menit.");
        worldMap.displayMap();
    }
}