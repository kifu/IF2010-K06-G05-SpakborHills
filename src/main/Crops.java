package test;

public class Crops extends Item{
    private int CropBuyPrice;
    private int  CropSellPrice;
    private int CropPerHarvest;

    public Crops(String cropname, int BuyPrice, int SellPrice, int CropPerHarvest){
        super(cropname);
        this.CropBuyPrice = BuyPrice;
        this.CropSellPrice = SellPrice;
        this.CropPerHarvest = CropPerHarvest;
    }
    /**
     * memberikan crop
     * @return Crops
     */
    public Crops getCrops(){
        return this;
    }
    /**
     * mengembalikan Crop Buy Price
     * @return int
     */
    public int getCropsBuyPrice(){
        return this.CropBuyPrice;
    }
    /**
     * mengembalikan Crop sell Price
     * @return
     */
    public int getCropsSellPrice(){
        return this.CropSellPrice;
    }
    /**
     * mengubah nilai Crop Buy Price
     * @param Crop
     * @param int
     */
    public void setCropBuyPrice(Crops crop, int newPrice){
        crop.CropBuyPrice = newPrice;
    }
    /**
     * mengubah nilai crop sell price
     * @param crop
     * @param newPrice
     */
    public void setCropSellPrice(Crops crop, int newPrice){
        crop.CropSellPrice = newPrice;
    }
    /**
     * mengubah nilai qty harvest (crop per harvest)
     * @param crop
     * @param newQty
     */
    public void setCropPerHarevest(Crops crop, int newQty){
        crop.CropPerHarvest = newQty;
    }
}