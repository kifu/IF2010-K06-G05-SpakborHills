package org.example;
public class QueenBed extends Furniture {
    private final int maxPeople = 2;
    private final int price = 10000;
    private WorldState worldState;

    public QueenBed(WorldState worldState) {
        super("bed_2", "Queen Bed", "Kasur ukuran queen yang mampu ditempati maks 2 orang", 4, 6, 'Q');
        this.worldState = worldState;
    }

    @Override
    public void use(Player player) {
        System.out.println("Kamu merebahkan diri di Queen Bed...");
        new Sleeping(worldState).execute(player);
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public int getPrice() {
        return price;
    }
}