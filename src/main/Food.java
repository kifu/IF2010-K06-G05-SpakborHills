public class Food extends EdibleItem {
    private Gold foodBuyPrice;
    private Gold foodSellPrice;

    public Food(int energyRes, Gold foodBuyPrice, Gold foodSellPrice) {
        super(energyRes);
        this.foodBuyPrice = foodBuyPrice;
        this.foodSellPrice = foodSellPrice;
    }

    public int getEnergyRes() {
        return energyRestored;
    }

    public Gold getFoodBuyPrice() {
        return foodBuyPrice;
    }

    public Gold getFoodSellPrice() {
        return foodSellPrice;
    }

    public void setFoodBuyPrice(String name, Gold price) {
        this.foodBuyPrice = price;
    }

    public void setFoodSellPrice(String name, Gold price) {
        this.foodSellPrice = price;
    }
}
