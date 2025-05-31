package org.example;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultRecipeLoader {
    public List<Recipe> loadAllRecipes() {
        List<Recipe> allRecipes = new ArrayList<>();
        
        // Resep Fish n' Chips
        Map<String, Integer> fishNChipsIngredients = new HashMap<>();
        fishNChipsIngredients.put("Any Fish", 2); 
        fishNChipsIngredients.put("Wheat", 1);
        fishNChipsIngredients.put("Potato", 1);
        allRecipes.add(new Recipe("recipe_1", "Fish n' Chips", fishNChipsIngredients));
        
        // Resep Baguette
        Map<String, Integer> baguetteIngredients = new HashMap<>();
        baguetteIngredients.put("Wheat", 3);
        allRecipes.add(new Recipe("recipe_2", "Baguette", baguetteIngredients));

        // Resep Sashimi
        Map<String, Integer> sashimiIngredients = new HashMap<>();
        sashimiIngredients.put("Salmon", 3);
        allRecipes.add(new Recipe("recipe_3", "Sashimi", sashimiIngredients));

        // Resep Fugu
        Map<String, Integer> fuguIngredients = new HashMap<>();
        fuguIngredients.put("Pufferfish", 1);
        allRecipes.add(new Recipe("recipe_4", "Fugu", fuguIngredients));

        // Resep Wine
        Map<String, Integer> wineIngredients = new HashMap<>();
        wineIngredients.put("Grape", 2);
        allRecipes.add(new Recipe("recipe_5", "Wine", wineIngredients));

        // Resep Pumpkin Pie
        Map<String, Integer> pumpkinPieIngredients = new HashMap<>();
        pumpkinPieIngredients.put("Egg", 1);
        pumpkinPieIngredients.put("Wheat", 1);
        pumpkinPieIngredients.put("Pumpkin", 1);
        allRecipes.add(new Recipe("recipe_6", "Pumpkin Pie", pumpkinPieIngredients));

        // Resep Veggie Soup
        Map<String, Integer> veggieSoupIngredients = new HashMap<>();
        veggieSoupIngredients.put("Cauliflower", 1);
        veggieSoupIngredients.put("Parsnip", 1);
        veggieSoupIngredients.put("Potato", 1);
        veggieSoupIngredients.put("Tomato", 1);
        allRecipes.add(new Recipe("recipe_7", "Veggie Soup", veggieSoupIngredients));

        // Resep Fish Stew
        Map<String, Integer> fishStewIngredients = new HashMap<>();
        fishStewIngredients.put("Any Fish", 2);
        fishStewIngredients.put("Hot Pepper", 1);
        fishStewIngredients.put("Cauliflower", 2);
        allRecipes.add(new Recipe("recipe_8", "Fish Stew", fishStewIngredients));

        // Resep Spakbor Salad
        Map<String, Integer> spakborSaladIngredients = new HashMap<>();
        spakborSaladIngredients.put("Melon", 1);
        spakborSaladIngredients.put("Cranberry", 1);
        spakborSaladIngredients.put("Blueberry", 1);
        spakborSaladIngredients.put("Tomato", 1);
        allRecipes.add(new Recipe("recipe_9", "Spakbor Salad", spakborSaladIngredients));

        // Resep Fish Sandwich
        Map<String, Integer> fishSandwichIngredients = new HashMap<>();
        fishSandwichIngredients.put("Any Fish", 1);
        fishSandwichIngredients.put("Wheat", 2);
        fishSandwichIngredients.put("Tomato", 1);
        fishSandwichIngredients.put("Hot Pepper", 1);
        allRecipes.add(new Recipe("recipe_10", "Fish Sandwich", fishSandwichIngredients));

        // Resep The Legends of Spakbor
        Map<String, Integer> legendsOfSpakborIngredients = new HashMap<>();
        legendsOfSpakborIngredients.put("Legend Fish", 1);
        legendsOfSpakborIngredients.put("Potato", 2);
        legendsOfSpakborIngredients.put("Parsnip", 1);
        legendsOfSpakborIngredients.put("Tomato", 1);
        allRecipes.add(new Recipe("recipe_11", "The Legends of Spakbor", legendsOfSpakborIngredients));

        return allRecipes;
    }
}
