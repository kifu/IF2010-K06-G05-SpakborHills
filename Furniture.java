public class Furniture extends Item {
    public Furniture(String name, int buyPrice, int sellPrice) {
        super("Furniture", name, buyPrice, sellPrice);
    }

    public Furniture getFurniture() {
        return this;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
