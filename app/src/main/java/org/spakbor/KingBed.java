package org.spakbor;

public class KingBed extends Furniture {
    private final int maxPeople = 2;
    private final int price = 15000; // buyPrice
    private WorldState worldState;

    public KingBed(WorldState worldState) {
        super("bed_3", "King Bed", "Kasur ukuran king yang mampu ditempati maks 2 orang", 6, 6, 'K');
        this.worldState = worldState;
        super.setBuyPrice(price);
    }

    @Override
    public void use(Player player) {
        System.out.println("Kamu merebahkan diri di Queen Bed...");
        new SleepingAction(player, worldState, false).execute();
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public int getPrice() {
        return price;
    }
}
