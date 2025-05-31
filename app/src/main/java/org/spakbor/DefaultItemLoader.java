import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DefaultItemLoader {
    public WorldState worldState;

    public List<Item> loadInitialItems() {
        Set<Item> initialItemSet = new LinkedHashSet<>();

        // Seeds
        // Spring Seeds
        initialItemSet.add(new Seeds("Parsnip Seeds", Season.SPRING, 1, 20));
        initialItemSet.add(new Seeds("Cauliflower Seeds", Season.SPRING, 5, 80));
        initialItemSet.add(new Seeds("Potato Seeds", Season.SPRING, 3, 50));
        initialItemSet.add(new Seeds("Wheat Seeds", Season.SPRING, 1, 60));
        // Summer Seeds
        initialItemSet.add(new Seeds("Blueberry Seeds", Season.SUMMER, 7, 80));
        initialItemSet.add(new Seeds("Tomato Seeds", Season.SUMMER, 3, 50));
        initialItemSet.add(new Seeds("Hot Pepper Seeds", Season.SUMMER, 1, 40));
        initialItemSet.add(new Seeds("Melon Seeds", Season.SUMMER, 4, 80));
        // Fall Seeds
        initialItemSet.add(new Seeds("Cranberry Seeds", Season.FALL, 2, 100));
        initialItemSet.add(new Seeds("Pumpkin Seeds", Season.FALL, 7, 150));
        initialItemSet.add(new Seeds("Wheat Seeds", Season.FALL, 1, 60));
        initialItemSet.add(new Seeds("Grape Seeds", Season.FALL, 3, 60));

        // Fish
        // Common Fish
        initialItemSet.add(new Fish("Bullhead", "Any", "Any", "Any", "Mountain Lake", FishType.COMMON));
        initialItemSet.add(new Fish("Carp", "Any", "Any", "Any", "Mountain Lake, Pond", FishType.COMMON));
        initialItemSet.add(new Fish("Chub", "Any", "Any", "Any", "Forest River, Mountain Lake", FishType.COMMON));

        // Regular Fish
        initialItemSet.add(new Fish("Largemouth Bass", "Any", "06.00-18.00", "Any", "Mountain Lake", FishType.REGULAR));
        initialItemSet.add(new Fish("Rainbow Trout", "Summer", "06.00-18.00", "Sunny", "Forest River, Mountain Lake", FishType.REGULAR));
        initialItemSet.add(new Fish("Sturgeon", "Summer, Winter", "06.00-18.00", "Any", "Mountain Lake", FishType.REGULAR));
        initialItemSet.add(new Fish("Midnight Carp", "Winter, Fall", "20.00-02.00", "Any", "Mountain Lake, Pond", FishType.REGULAR));
        initialItemSet.add(new Fish("Flounder", "Spring, Summer", "06.00-22.00", "Any", "Ocean", FishType.REGULAR));
        initialItemSet.add(new Fish("Halibut", "Any", "06.00-11.00, 19.00-02.00", "Any", "Ocean", FishType.REGULAR));
        initialItemSet.add(new Fish("Octopus", "Summer", "06.00-22.00", "Any", "Ocean", FishType.REGULAR));
        initialItemSet.add(new Fish("Pufferfish", "Summer", "00.00-16.00", "Sunny", "Ocean", FishType.REGULAR));
        initialItemSet.add(new Fish("Sardine", "Any", "06.00-18.00", "Any", "Ocean", FishType.REGULAR));
        initialItemSet.add(new Fish("Super Cucumber", "Summer, Fall, Winter", "18.00-02.00", "Any", "Ocean", FishType.REGULAR));
        initialItemSet.add(new Fish("Catfish", "Spring, Summer, Fall", "06.00-22.00", "Rainy", "Forest River, Pond", FishType.REGULAR));
        initialItemSet.add(new Fish("Salmon", "Fall", "06.00-18.00", "Any", "Forest River", FishType.REGULAR));

        // Legendary Fish
        initialItemSet.add(new Fish("Angler", "Fall", "08.00-20.00", "Any", "Pond", FishType.LEGENDARY));
        initialItemSet.add(new Fish("Crimsonfish", "Summer", "08.00-20.00", "Any", "Ocean", FishType.LEGENDARY));
        initialItemSet.add(new Fish("Glacierfish", "Winter", "08.00-20.00", "Any", "Forest River", FishType.LEGENDARY));
        initialItemSet.add(new Fish("Legend", "Spring", "08.00-20.00", "Rainy", "Mountain Lake", FishType.LEGENDARY));

        // Crops
        initialItemSet.add(new Crops("Parsnip", 50, 35, 1));
        initialItemSet.add(new Crops("Cauliflower", 200, 150, 1));
        initialItemSet.add(new Crops("Potato", -1, 80, 1));
        initialItemSet.add(new Crops("Wheat", 50, 30, 3));
        initialItemSet.add(new Crops("Blueberry", 150, 40, 3));
        initialItemSet.add(new Crops("Tomato", 90, 60, 1));
        initialItemSet.add(new Crops("Hot Pepper", -1, 40, 1));
        initialItemSet.add(new Crops("Melon", -1, 250, 1));
        initialItemSet.add(new Crops("Cranberry", -1, 25, 10));
        initialItemSet.add(new Crops("Pumpkin", 300, 250, 1));
        initialItemSet.add(new Crops("Grape", 100, 10, 20));

        // Food
        initialItemSet.add(new Food("Fish n' Chips", 150, 135, 50));
        initialItemSet.add(new Food("Baguette", 100, 80, 25));
        initialItemSet.add(new Food("Sashimi", 300, 275, 70));
        initialItemSet.add(new Food("Fugu", -1, 135, 50));
        initialItemSet.add(new Food("Wine", 100, 90, 20));
        initialItemSet.add(new Food("Pumpkin Pie", 120, 100, 35));
        initialItemSet.add(new Food("Veggie Soup", 140, 120, 40));
        initialItemSet.add(new Food("Fish Stew", 280, 260, 70));
        initialItemSet.add(new Food("Spakbor Salad", -1, 250, 70));
        initialItemSet.add(new Food("Fish Sandwich", 200, 180, 50));
        initialItemSet.add(new Food("The Legends of Spakbor", -1, 2000, 100));
        initialItemSet.add(new Food("Cooked Pig's Head", 1000, 0, 100));

        // Equipment
        initialItemSet.add(new Equipment("Hoe"));
        initialItemSet.add(new Equipment("Watering Can"));
        initialItemSet.add(new Equipment("Pickaxe"));
        initialItemSet.add(new Equipment("Fishing Rod"));

        // Misc
        initialItemSet.add(new Misc("Firewood", 100, 50));
        initialItemSet.add(new Misc("Coal", 200, 100));
        initialItemSet.add(new Misc("Proposal Ring", 3000, -1));

        // Furniture
        initialItemSet.add(new QueenBed(worldState));
        initialItemSet.add(new KingBed(worldState));
        initialItemSet.add(new Stove());


        // Resep yang bisa dibeli
        initialItemSet.add(new RecipeItem("Recipe: Fish n' Chips", 50, "Fish n’ Chips"));
        initialItemSet.add(new RecipeItem("Recipe: Fish Sandwich", 100, "Fish Sandwich"));

        return new ArrayList<>(initialItemSet);
    }      
}