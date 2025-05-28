public class Equipment extends Item {
    public Equipment(String name) {
        super(name, "Equipment", 0, 0);
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "name='" + getName() + '\'' +
                ", category='" + getCategory() + '\'' +
                ", buyPrice=" + getBuyPrice() +
                ", sellPrice=" + getSellPrice() +
                '}';
    }
}