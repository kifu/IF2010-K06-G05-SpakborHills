package main;

public class Crops extends Item{
    private int cropPerHarvest;
    
    /**
     * Constructor untuk kelas Crops
     * @param cropname nama dari crop
     * @param BuyPrice harga beli dari crop
     * @param SellPrice harga jual dari crop
     * @param CropPerHarvest jumlah hasil panen per panen
     */
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