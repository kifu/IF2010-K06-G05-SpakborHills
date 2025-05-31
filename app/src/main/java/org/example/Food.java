package org.example;
public class Food extends Item implements Edible {
    private int energyValue;

    public Food(String name, int buyPrice, int sellPrice, int energyValue) {
        super("Food", name, buyPrice, sellPrice);
        this.energyValue = energyValue;
    }

    public Food getFood() {
        return this;
    }
    
    public void setEnergyRestored(int energyValue) {
        this.energyValue = energyValue;
    }
    
    @Override
    public int getEnergyRes() {
        return this.energyValue;
    }

    @Override
    public String toString() {
        return super.toString() + ", Energy: " + energyValue;
    }
}
