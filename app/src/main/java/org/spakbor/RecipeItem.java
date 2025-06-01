package org.spakbor;

// Ini adalah recipe item yang akan dijual di toko.
public class RecipeItem extends Item {
    // Menyimpan nama resep yang akan di-unlock oleh item ini.
    private String unlocksRecipeName;

    public RecipeItem(String name, int buyPrice, String unlocksRecipeName) {
        // Kategori "Recipe", tidak bisa dijual kembali (sellPrice = -1)
        super("Recipe", name, buyPrice, -1);
        this.unlocksRecipeName = unlocksRecipeName;
    }

    public String getUnlocksRecipeName() {
        return this.unlocksRecipeName;
    }
}