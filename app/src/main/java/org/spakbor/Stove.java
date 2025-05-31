public class Stove extends Furniture {
    public Stove() {
        super("stove", "Stove", "Tungku untuk memasak makanan\nMaks makanan yang bisa dimasak hanya satu.\nPerlu fuel untuk menyalakannya.", 1, 1, 'V');
    }

    @Override
    public void use(Player player) {
        // Implementasi Pemain memasak makanan (Panggil Cooking)
        System.out.println("Kamu menggunakan Stove untuk memasak. Pastikan ada fuel dan bahan makanan!");
        // Tambahkan logic pengecekan fuel dan memasak 
    }
}