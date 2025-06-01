package org.spakbor;

public class Crops extends Item implements Edible {
    private int cropPerHarvest;
    public Crops(String name, int BuyPrice, int SellPrice, int cropPerHarvest){
        super("Crops", name, BuyPrice, SellPrice);
        this.cropPerHarvest = cropPerHarvest;
    }

    @Override
    public int getEnergyRes() {
        return 3;
    }

    public Crops getCrops(){
        return this;
    }

    public int getCropPerHarvest(){
        return this.cropPerHarvest;
    }

    public void setCropPerHarevest(Crops crop, int newQty){
        crop.cropPerHarvest = newQty;
    }

    @Override
    public String toString() {
        return super.toString() + ", Crop Per Harvest: " + cropPerHarvest;
    }
}