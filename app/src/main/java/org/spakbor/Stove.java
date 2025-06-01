package org.spakbor;

public class Stove extends Furniture {
    private final int price = 7500;
    
    public Stove() {
        super("stove", "Stove", "Tungku untuk memasak makanan\nMaks makanan yang bisa dimasak hanya satu.\nPerlu fuel untuk menyalakannya.", 1, 1, 'V');
        super.setBuyPrice(price);
    }

    @Override
    public void use(Player player) {
        System.out.println("Kamu menggunakan Stove untuk memasak. Pastikan ada fuel dan bahan makanan!");
    }
}