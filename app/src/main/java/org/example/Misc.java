package org.example;
public class Misc extends Item {
    public Misc(String name, int buyPrice, int sellPrice) {
        super("Misc", name, buyPrice, sellPrice);
    }

    public Misc getMisc() {
        return this;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
