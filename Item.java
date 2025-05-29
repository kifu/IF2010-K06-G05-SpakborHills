import java.util.Objects;

public abstract class Item {
    private String category;
    private String name;
    private int buyPrice;
    private int sellPrice;

    public Item(String category, String name, int buyPrice, int sellPrice) {
        this.category = category;
        this.name = name;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    @Override
    public String toString() {
        String buyPriceDisplay = (this.buyPrice == -1) ? "-" : this.buyPrice + "g";
        String sellPriceDisplay = (this.sellPrice == -1) ? "-" : this.sellPrice + "g";

        return "Category: '" + category + '\'' +
               ", Name: '" + name + '\'' +
               ", Buy Price: " + buyPriceDisplay +
               ", Sell Price: " + sellPriceDisplay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        // Kesamaan berdasarkan nama dan kategori
        return Objects.equals(name, item.name) &&
               Objects.equals(category, item.category);
    }

    @Override
    public int hashCode() {
        // Hash code berdasarkan nama dan kategori
        return Objects.hash(name, category);
    }
}
