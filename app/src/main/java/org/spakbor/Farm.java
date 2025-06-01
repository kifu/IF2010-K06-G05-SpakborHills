package org.spakbor;

public class Farm {
    private String name;
    private Player player;
    private FarmMap farmMap;
    private WorldState worldState;

    public Farm(String name, Player player, FarmMap farmMap, WorldState worldState) {
        this.name = name;
        this.player = player;
        this.farmMap = farmMap;
        this.worldState = worldState;
    }

    public String getName() {
        return name;
    }

    public Player getPlayer() {
        return player;
    }

    public FarmMap getFarmMap() {
        return farmMap;
    }

    public WorldState getWorldState() {
        return worldState;
    }

    public Time getCurrentTime() {
        return worldState.getCurrentTime();
    }

    public int getCurrentDay() {
        return worldState.getCurrentTime().getDay();
    }

    public Season getCurrentSeason() {
        return worldState.getCurrentSeason();
    }

    public Weather getCurrentWeather() {
        return worldState.getCurrentWeather();
    }

    public void setName(String name) {
        this.name = name;
        this.player.setFarmName(name);
    }

    public void displayFarmStatus() {
        System.out.println("===== Status Farm: " + name + " =====");
        System.out.println(player.toString());
        System.out.println(worldState.getCurrentStatus()); // Menampilkan informasi waktu, musim, cuaca
        System.out.println("===============================");
    }
}
