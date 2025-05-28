package main;
public class CookingAction implements Action {
    private Recipe recipe;
    private Item[] ingredients;
    private Item fuel;

    public CookingAction(Recipe recipe, Item[] ingredients, Item fuel) {
        this.recipe = recipe;
        this.ingredients = ingredients;
        this.fuel = fuel;
    }

    @Override
    public void execute(Player player) {
        if (!canExecute(player)) return;

        // Kurangi energi saat inisiasi
        player.decreaseEnergy(getEnergyCost());

        // Tambahkan hasil masakan ke inventory
        Item cookedResult = new Item(recipe.GetRecipeName(recipe.GetRecipe().Item_ID));
        player.getInventory().add(cookedResult);

        // Buang fuel yang digunakan
        player.getInventory().remove(fuel, 1);

        // Buang bahan-bahan resep yang digunakan
        for (Item ingredient : ingredients) {
            player.getInventory().remove(ingredient, 1);
        }

        // Tandai resep telah tersedia
        recipe.SetAvailability(recipe.GetRecipe().Item_ID, true);
    }

    @Override
    public int getEnergyCost() {
        return 10; // -10 energi untuk inisiasi memasak
    }

    @Override
    public int getTimeCost() {
        return 60; // 1 jam waktu proses memasak
    }

    @Override
    public boolean canExecute(Player player) {
        // Masak hanya bisa dilakukan di House atau Stove
        if (!player.isInHouseOrStove()) return false;

        // Resep harus tersedia dan cocok dengan bahan
        if (recipe == null || fuel == null || ingredients == null) return false;

        Item[] requiredIngredients = recipe.GetIngredient(recipe.GetRecipe().Item_ID);
        if (requiredIngredients.length != ingredients.length) return false;

        // Cek apakah semua bahan ada di inventory
        for (Item ingredient : requiredIngredients) {
            if (!player.getInventory().contains(ingredient)) {
                return false;
            }
        }

        // Fuel harus tersedia dan mencukupi
        String fuelName = fuel.getName().toLowerCase();
        boolean validFuel = fuelName.equals("firewood") || fuelName.equals("coal");
        if (!validFuel || !player.getInventory().contains(fuel)) return false;

        // Cek cukup fuel untuk masak 1 resep
        return canFuelCookRecipe(fuel);
    }

    // Helper: fuel bisa masak resep atau tidak
    private boolean canFuelCookRecipe(Item fuel) {
        String fuelName = fuel.getName().toLowerCase();
        if (fuelName.equals("firewood")) {
            return true; // 1 firewood = 1 resep
        } else if (fuelName.equals("coal")) {
            return true; // 1 coal = 2 resep, cukup untuk 1
        }
        return false;
    }
}
