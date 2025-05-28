public class Misc extends Item {
    public Misc(String name, int buyPrice, int sellPrice) {
        super(name, buyPrice, sellPrice);
    }

    @Override
    public String toString() {
        return "Misc{" +
                "name='" + getName() + '\'' +
                ", buyPrice=" + getBuyPrice() +
                ", sellPrice=" + getSellPrice() +
                '}';
    }
}
