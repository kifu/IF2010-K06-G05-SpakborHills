package org.example;
import java.util.List;
import java.util.Map;

public class Recipe {
    private String recipe_id;
    private String recipe_name;
    private Map<String, Integer> ingredients; 
    
    public Recipe(String recipe_id, String recipe_name, Map<String, Integer> ingredients) {
        this.recipe_id = recipe_id;
        this.recipe_name = recipe_name;
        this.ingredients = ingredients;
    }

    public String getRecipeId() {
        return recipe_id;
    }

    public String getRecipeName() {
        return recipe_name;
    }

    public Map<String, Integer> getIngredients() {
        return ingredients;
    }

    public void setRecipeId(String recipe_id) {
        this.recipe_id = recipe_id;
    }

    public void setRecipeName(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    public void setIngredients(Map<String, Integer> ingredients) {
        this.ingredients = ingredients;
    }

    public static Recipe findRecipeByName(List<Recipe> recipes, String name) {
        for (Recipe recipe : recipes) {
            if (recipe.getRecipeName().equalsIgnoreCase(name)) {
                return recipe;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Recipe ID: ").append(recipe_id).append("\n");
        sb.append("Recipe Name: ").append(recipe_name).append("\n");
        sb.append("Ingredients:\n");
        for (Map.Entry<String, Integer> entry : ingredients.entrySet()) {
            sb.append("- ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}
