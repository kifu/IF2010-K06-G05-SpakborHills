public class Furniture extends Item {
    public Furniture(String name, int buyPrice, int sellPrice) {
        super(name, buyPrice, sellPrice);
    }

    @Override
    public String toString() {
        return "Furniture{" +
                "name='" + getName() + '\'' +
                ", buyPrice=" + getBuyPrice() +
                ", sellPrice=" + getSellPrice() +
                '}';
    }
}
