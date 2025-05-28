public abstract class EdibleItem extends Item {
    protected int energyRestored;

    public EdibleItem(int energyRestored) {
        super(""); 
        this.energyRestored = energyRestored;
    }

    public int getEnergyRestored() {
        return energyRestored;
    }
}
