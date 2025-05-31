// Main.java (Versi Final yang Benar)

import java.util.List;

public class CookingDemo {

    public static void main(String[] args) {
        
        // =================================================================
        // 1. INISIALISASI DUNIA GAME
        // =================================================================
        System.out.println("Memuat semua data game...");
        
        DefaultItemLoader itemLoader = new DefaultItemLoader();
        List<Item> allGameItems = itemLoader.loadInitialItems();

        DefaultRecipeLoader recipeLoader = new DefaultRecipeLoader();
        List<Recipe> allGameRecipes = recipeLoader.loadAllRecipes();

        WorldState world = new WorldState();
        Player asep = new Player("Asep Spakbor", "Male", "Kebon Asep");
        asep.setLocation("House");

        System.out.println("Inisialisasi selesai. Selamat datang, " + asep.getName() + "!");
        System.out.println("=================================================================\n");


        // =================================================================
        // 2. PERSIAPAN SKENARIO: Asep akan memasak "Fish Stew"
        // =================================================================
        System.out.println("Skenario: Asep akan memasak 'Fish Stew'. Menyiapkan bahan...");

        Item carp = findItemByName(allGameItems, "Carp");
        Item hotPepper = findItemByName(allGameItems, "Hot Pepper");
        Item cauliflower = findItemByName(allGameItems, "Cauliflower");
        Item firewood = findItemByName(allGameItems, "Firewood");

        // Pemeriksaan penting untuk memastikan item tidak null sebelum digunakan
        if (carp == null || hotPepper == null || cauliflower == null || firewood == null) {
            System.out.println("ERROR: Satu atau lebih item untuk skenario tidak ditemukan. Program berhenti.");
            return;
        }

        asep.getInventory().addItem(carp, 2);
        asep.getInventory().addItem(hotPepper, 1);
        asep.getInventory().addItem(cauliflower, 2);
        asep.getInventory().addItem(firewood, 1);

        // Tampilkan kondisi awal pemain
        System.out.println("--- KONDISI AWAL ---");
        System.out.println("Status Waktu: " + world.getCurrentStatus());
        System.out.println(asep);
        asep.showInventory();
        System.out.println("---------------------\n");


        // =================================================================
        // 3. EKSEKUSI AKSI MEMASAK
        // =================================================================
        
        Recipe fishStewRecipe = findRecipeByName(allGameRecipes, "Fish Stew");

        if (fishStewRecipe != null) {
            System.out.println("Asep menemukan resep 'Fish Stew' dan akan mulai memasak...");
            Action cookAction = new CookingAction(asep, world, fishStewRecipe);
            cookAction.execute();
        } else {
            System.out.println("ERROR: Resep untuk 'Fish Stew' tidak ditemukan!");
        }


        // =================================================================
        // 4. TAMPILKAN HASIL AKHIR
        // =================================================================
        System.out.println("\n--- KONDISI AKHIR ---");
        System.out.println("Status Waktu: " + world.getCurrentStatus());
        System.out.println(asep);
        asep.showInventory();
        System.out.println("---------------------");
    }

    /**
     * Helper method untuk mencari objek Item dari daftar berdasarkan namanya.
     * @param items Daftar semua item game
     * @param name Nama item yang dicari
     * @return Objek Item jika ditemukan, null jika tidak.
     */
    public static Item findItemByName(List<Item> items, String name) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
    
    /**
     * Helper method untuk mencari objek Recipe dari daftar berdasarkan namanya.
     * @param recipes Daftar semua resep game
     * @param name Nama resep yang dicari
     * @return Objek Recipe jika ditemukan, null jika tidak.
     */
    public static Recipe findRecipeByName(List<Recipe> recipes, String name) {
        for (Recipe recipe : recipes) {
            if (recipe.getRecipeName().equalsIgnoreCase(name)) {
                return recipe;
            }
        }
        return null;
    }
}