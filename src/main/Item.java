public abstract class Item {
    private String name;
    private String category;
    private int buyPrice;
    private int sellPrice;

    public Item(String name, String category, int buyPrice, int sellPrice) {
        this.name = name;
        this.category = category;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }
    
    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
