import java.util.Objects;

public class Visiting implements Action {
    private FarmMap farmMap;
    private WorldMap worldMap;
    private WorldState worldState;
    private Statistics statistics;

    // areaName: nama area yang sedang dikunjungi, null jika visiting ke WorldMap (dari FarmMap)
    private String areaName;

    /**
     * Constructor untuk visiting ke WorldMap (dari FarmMap)
     */
    public Visiting(FarmMap farmMap, WorldMap worldMap, WorldState worldState, Statistics statistics) {
        this(farmMap, worldMap, worldState, statistics, null);
    }

    /**
     * Constructor untuk visiting ke area tertentu (rumah NPC, Mountain Lake, Forest River, Ocean)
     */
    public Visiting(FarmMap farmMap, WorldMap worldMap, WorldState worldState, Statistics statistics, String areaName) {
        this.farmMap = farmMap;
        this.worldMap = worldMap;
        this.worldState = worldState;
        this.statistics = statistics;
        this.areaName = areaName;
    }

    /**
     * Efek visiting:
     * - -10 energi
     * - +15 menit
     * - visitingFrequency statistik (per NPC jika areaName NPC, atau per area jika ingin)
     */
    @Override
    public void execute(Player player) {
        if (!canExecute(player)) {
            return;
        }

        // Efek visiting
        player.setEnergy(player.getEnergy() - getEnergyCost());
        worldState.getCurrentTime().advanceMinutes(getTimeCost());

        // Statistics: update visiting frequency jika areaName adalah rumah NPC
        if (areaName != null) {
            // Cek apakah areaName adalah rumah NPC berdasarkan statistik
            Statistics.NPCData npcData = statistics.getNpcStatus().get(areaName);
            if (npcData == null) {
                // Jika belum ada, buat data baru dengan default
                npcData = new Statistics.NPCData("Stranger", 0, 0, 0);
                statistics.updateNpcStatus(areaName, npcData);
            }
            npcData.setVisitingFrequency(npcData.getVisitingFrequency() + 1);
            System.out.println("Visiting ke " + areaName + ". Visiting frequency sekarang: " + npcData.getVisitingFrequency());
        } else {
            System.out.println("Visiting ke WorldMap (FarmMap).");
        }

        System.out.println("-" + getEnergyCost() + " energi, +" + getTimeCost() + " menit.");

        // Jika visiting dari FarmMap ke WorldMap, pindahkan player ke WorldMap
        if (areaName == null) {
            player.setLocation("WorldMap");
            worldMap.movePlayerToFarmMap();
            worldMap.displayMap();
        }
    }

    @Override
    public int getEnergyCost() {
        return 10;
    }

    @Override
    public int getTimeCost() {
        return 15;
    }

    @Override
    public boolean canExecute(Player player) {
        // Jika visiting ke WorldMap (dari FarmMap)
        if (areaName == null) {
            if (!farmMap.canVisit()) {
                System.out.println("Kamu harus berada di pojok kanan atas FarmMap untuk bisa visiting ke World Map!");
                return false;
            }
            if (player.getEnergy() < getEnergyCost()) {
                System.out.println("Energi tidak cukup! Minimal " + getEnergyCost() + " energi dibutuhkan untuk visiting.");
                return false;
            }
            return true;
        } else {
            // Ketentuan validasi visiting ke area di WorldMap
            if (player.getEnergy() < getEnergyCost()) {
                System.out.println("Energi tidak cukup! Minimal " + getEnergyCost() + " energi dibutuhkan untuk visiting.");
                return false;
            }
            // Validasi lokasi: harus di WorldMap dan posisi di samping areaName (misal rumah NPC, Mountain Lake, Ocean, Forest River)
            if (!Objects.equals(player.getLocation(), "WorldMap")) {
                System.out.println("Kamu harus berada di WorldMap untuk visiting area ini!");
                return false;
            }
            if (!worldMap.isPlayerAdjacentToArea(areaName)) {
                System.out.println("Kamu harus berada di dekat " + areaName + " untuk visiting ke sini!");
                return false;
            }
            return true;
        }
    }
}