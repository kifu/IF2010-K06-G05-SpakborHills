package org.spakbor;

public class SingleBed extends Furniture {
    private final int maxPeople = 1;
    private WorldState worldState;

    public SingleBed(WorldState worldState) {
        super("bed_1", "Single Bed", "Kasur ukuran single yang mampu ditempati maks 1 orang", 2, 4, 'S');
        this.worldState = worldState;
    }

    @Override
    public void use(Player player) {
        System.out.println("Kamu merebahkan diri di Single Bed...");
        new Sleeping(worldState).execute(player);
    }

    public int getMaxPeople() {
        return maxPeople;
    }
}