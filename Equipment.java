public class Equipment extends Item {
    public Equipment(String name) {
        super(name, 0, 0);
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "name='" + getName() + '\'' +
                ", buyPrice=" + getBuyPrice() +
                ", sellPrice=" + getSellPrice() +
                '}';
    }
}
