package org.spakbor;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CookingAction extends Action {
    private final Recipe recipe;
    private static final int ENERGY_COST = 10;
    private static final String COOKING_LOCATION = "House";
    private static final int COOKING_DURATION_MINUTES = 60;

    // Variabel untuk menyimpan state antara validasi dan eksekusi
    private String failureReason;
    private Item fuelToConsume;
    private Map<Item, Integer> ingredientsToConsume;

    public CookingAction(Player player, WorldState world, Recipe recipe) {
        super(player, world);
        this.recipe = recipe;
        this.ingredientsToConsume = new HashMap<>();
    }

    @Override
    protected boolean validate() {
        if (!player.getLocation().equals(COOKING_LOCATION)) {
            this.failureReason = "Gagal memasak: Memasak hanya bisa dilakukan di " + COOKING_LOCATION + ".";
            return false;
        }

        // 2. Validasi Energi
        if (player.getEnergy() < ENERGY_COST) {
            this.failureReason = "Gagal memasak: Energi tidak cukup.";
            return false;
        }

        // 3. Validasi dan siapkan Bahan Bakar
        this.fuelToConsume = findFuelInInventory();
        if (this.fuelToConsume == null) {
            this.failureReason = "Gagal memasak: Tidak ada bahan bakar (Firewood/Coal) yang tersedia.";
            return false;
        }

        // 4. Validasi dan siapkan Bahan Baku
        if (!findAndPrepareIngredients()) {
            this.failureReason = "Gagal memasak: Bahan baku tidak mencukupi untuk resep '" + recipe.getRecipeName() + "'.";
            return false;
        }

        // Semua validasi berhasil
        return true;
    }

    @Override
    protected void performAction() {
        // Metode ini untuk menampilkan pesan proses, sesuai Template Method
        System.out.println("Memulai memasak " + recipe.getRecipeName() + "...");
        System.out.println("Proses memasak akan memakan waktu 1 jam dan energi sebesar " + ENERGY_COST + " poin.");
    }

    @Override
    protected void applyEffects() {
        // 1. Catat hari sebelum waktu dimajukan
        int dayBefore = world.getCurrentTime().getDay();

        // 2. Majukan waktu game sebanyak 1 jam
        System.out.println("Waktu akan dimajukan selama " + COOKING_DURATION_MINUTES + " menit...");
        world.getCurrentTime().advanceMinutes(COOKING_DURATION_MINUTES);

        // 3. Konsumsi energi & bahan baku
        player.setEnergy(player.getEnergy() - ENERGY_COST);
        for (Map.Entry<Item, Integer> entry : this.ingredientsToConsume.entrySet()) {
            player.getInventory().removeItem(entry.getKey(), entry.getValue());
        }
        System.out.println("Bahan baku untuk " + recipe.getRecipeName() + " telah digunakan.");

        // 4. Buat item makanan dan tambahkan ke inventory
        Food cookedItem = createFoodFromRecipe(recipe);
        player.getInventory().addItem(cookedItem, 1);

        System.out.println("=========================================");
        System.out.println("Bahan bakar " + this.fuelToConsume.getName() + " x1 digunakan.");
        System.out.println("Bahan baku untuk " + recipe.getRecipeName() + " telah digunakan.");
        System.out.println(cookedItem.getName() + " x1 telah ditambahkan ke inventory!");
        System.out.println("=========================================");

        // 5. Cek apakah terjadi pergantian hari
        if (world.getCurrentTime().getDay() > dayBefore) {
            // Jika hari berganti, panggil handler hari baru dari WorldState
            world.handleNewDay();
        }

    }

    @Override
    protected String getSuccessMessage() {
        return "Ding! " + recipe.getRecipeName() + " berhasil dimasak!";
    }

    @Override
    protected String getFailureMessage() {
        return this.failureReason;
    }

    // --- METODE HELPER ---

    private Item findFuelInInventory() {
        Inventory inv = player.getInventory();
        // Cek Firewood dulu
        for (Item item : inv.getInventory().keySet()) {
            if (item.getName().equalsIgnoreCase("Firewood")) return item;
        }
        // Jika tidak ada, cek Coal
        for (Item item : inv.getInventory().keySet()) {
            if (item.getName().equalsIgnoreCase("Coal")) return item;
        }
        return null; 
    }

    private boolean findAndPrepareIngredients() {
        Inventory playerInventory = player.getInventory();
        Map<String, Integer> requiredIngredients = recipe.getIngredients();

        for (Map.Entry<String, Integer> required : requiredIngredients.entrySet()) {
            String requiredName = required.getKey();
            int requiredQty = required.getValue();
            int foundQty = 0;
            
            List<Item> itemsToUse = new ArrayList<>();

            // Logika pencarian yang sama seperti sebelumnya
            if (requiredName.equalsIgnoreCase("Any Fish")) {
                for (Item item : playerInventory.getInventory().keySet()) {
                    if (item instanceof Fish) {
                        foundQty += playerInventory.getItemQuantity(item);
                        itemsToUse.add(item);
                    }
                }
            } else if (requiredName.equalsIgnoreCase("Legend fish")) {
                 for (Item item : playerInventory.getInventory().keySet()) {
                    if (item instanceof Fish && ((Fish)item).getFishType() == FishType.LEGENDARY) {
                        foundQty += playerInventory.getItemQuantity(item);
                        itemsToUse.add(item);
                    }
                }
            } else { // Item spesifik
                for (Item item : playerInventory.getInventory().keySet()) {
                    if (item.getName().equalsIgnoreCase(requiredName)) {
                        foundQty += playerInventory.getItemQuantity(item);
                        itemsToUse.add(item);
                    }
                }
            }

            if (foundQty < requiredQty) return false;

            int qtyToConsume = requiredQty;
            for(Item item : itemsToUse) {
                int available = playerInventory.getItemQuantity(item);
                int take = Math.min(qtyToConsume, available);
                this.ingredientsToConsume.put(item, take);
                qtyToConsume -= take;
                if (qtyToConsume == 0) break;
            }
        }
        return true;
    }

    private Food createFoodFromRecipe(Recipe recipe) {
        switch(recipe.getRecipeId()) {
            case "recipe_1": return new Food("Fish n' Chips", 150, 135, 50);
            case "recipe_2": return new Food("Baguette", 100, 80, 25);
            case "recipe_3": return new Food("Sashimi", 300, 275, 70);
            case "recipe_4": return new Food("Fugu", -1, 135, 50); 
            case "recipe_5": return new Food("Wine", 100, 90, 20);
            case "recipe_6": return new Food("Pumpkin Pie", 120, 100, 35);
            case "recipe_7": return new Food("Veggie Soup", 140, 120, 40);
            case "recipe_8": return new Food("Fish Stew", 280, 260, 70);
            case "recipe_9": return new Food("Spakbor Salad", -1, 250, 70);
            case "recipe_10": return new Food("Fish Sandwich", 200, 180, 50);
            case "recipe_11": return new Food("The Legends of Spakbor", -1, 2000, 100);
            default: 
                System.out.println("Resep tidak dikenali: " + recipe.getRecipeName());
                return new Food("Unknown Food", 0, 0, 0); // Kembalikan makanan default
        }
    }
}