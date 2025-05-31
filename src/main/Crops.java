package main;

public class Crops extends Item{
    private int cropPerHarvest;
    public Crops(String cropname, int BuyPrice, int SellPrice, int CropPerHarvest){
        super(cropname,BuyPrice,SellPrice);
        this.cropPerHarvest = CropPerHarvest;
    }
    /**
     * memberikan crop
     * @return Crops
     */
    public Crops getCrops(){
        return this;
    }
    public int getCropPerHarvest(){
        return this.cropPerHarvest;
    }
    /**
     * mengubah nilai qty harvest (crop per harvest)
     * @param crop
     * @param newQty
     */
    public void setCropPerHarevest(Crops crop, int newQty){
        crop.cropPerHarvest = newQty;
    }
}