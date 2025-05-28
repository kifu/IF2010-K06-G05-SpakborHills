package main;
public abstract class EdibleItem extends Item {
    protected int energyRestored;

    public EdibleItem(int energyRestored) {
        super(""); // name will be assigned if needed
        this.energyRestored = energyRestored;
    }

    public int getEnergyRestored() {
        return energyRestored;
    }
}
