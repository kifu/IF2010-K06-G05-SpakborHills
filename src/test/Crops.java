package test;

public class Crops extends Item{
    private Gold CropBuyPrice;
    private Gold CropSellPrice;
    private Int CropPerHarvest;

    public Crops(String name, Gold BuyPrice, Gold SellPrice, int CropPerHarvest){
        super(name);
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
     * @return Gold
     */
    public Gold getCropsBuyPrice(){
        return this.CropBuyPrice;
    }

    /**
     * mengembalikan Crop sell Price
     * @return
     */
    public Gold getCropsSellPrice(){
        return this.CropSellPrice;
    }
    

    /**
     * mengubah nilai Crop Buy Price
     * @param Crop
     * @param Gold
     */
    public setCropBuyPrice(Crops crop, Gold newPrice){
        crop.CropBuyPrice = newPrice;
    }

    /**
     * mengubah nilai crop sell price
     * @param crop
     * @param newPrice
     */
    public setCropSellPrice(Crops crop, Gold newPrice){
        crop.CropSellPrice = newPrice;
    }

    /**
     * mengubah nilai qty harvest (crop per harvest)
     * @param crop
     * @param newQty
     */
    public setCropPerHarevest(Crops crop, int newQty){
        crop.CropPerHarvest = newQty;
    }
}