import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import NPC.RelationshipStatus;

public class NPCFactory {

    // Helper buat convert List<String> nama item jadi List<Item>
    private static List<Item> createItemList(List<String> itemNames, String category) {
        return itemNames.stream().map(name -> new BasicItem(name, category)).collect(Collectors.toList());
    }
    
    private static List<Item> createItemList(List<String> itemNames, List<String> categories) {
        return itemNames.stream().map(name -> { 
            String n = "Misc";
            for (String c : categories) {
                if (name.toLowerCase().contains(c.toLowerCase())) {
                    n = c;
                    break;
                }
            }
            if (n.equals("Misc")) {
                return new Misc(name, 20, 10); // contoh harga, sesuaikan kebutuhan
            } else {
                return new BasicItem(name, n);
            }
        }).collect(Collectors.toList());
    }

    public static NPC createMayorTadi() {
        List<Item> loved = createItemList(Arrays.asList("Legend"), "Fish");
        List<Item> liked = createItemList(Arrays.asList("Angler", "Crimsonfish", "Glacierfish"), "Fish");
        // hated = semua selain loved & liked, di-handle di NPC
        List<Item> hated = List.of();
        return new NPC("Mayor Tadi", loved, liked, hated, RelationshipStatus.SINGLE);
    }

    public static NPC createCaroline() {
        List<Item> loved = createItemList(Arrays.asList("Firewood", "Coal"), "Material");
        List<Item> liked = createItemList(Arrays.asList("Potato", "Wheat"), "Crop");
        List<Item> hated = createItemList(Arrays.asList("Hot Pepper"), "Crop");
        return new NPC("Caroline", loved, liked, hated, RelationshipStatus.SINGLE);
    }

    public static NPC createPerry() {
        List<Item> loved = createItemList(Arrays.asList("Cranberry", "Blueberry"), "Fruit");
        List<Item> liked = createItemList(Arrays.asList("Wine"), "Drink");
        // hated = seluruh item kategori Fish, di-handle di NPC, jadi list kosong saja
        List<Item> hated = List.of();
        return new NPC("Perry", loved, liked, hated, RelationshipStatus.SINGLE);
    }

    public static NPC createDasco() {
        List<Item> loved = createItemList(Arrays.asList("The Legends of Spakbor", "Cooked Pig's Head", "Wine", "Fugu", "Spakbor Salad"),
            Arrays.asList("Book", "Food", "Drink", "Fish", "Salad"));
        List<Item> liked = createItemList(Arrays.asList("Fish Sandwich", "Fish Stew", "Baguette", "Fish n’ Chips"),
            Arrays.asList("Food", "Fish", "Bread"));
        List<Item> hated = createItemList(Arrays.asList("Legend", "Grape", "Cauliflower", "Wheat", "Pufferfish", "Salmon"),
            Arrays.asList("Fish", "Fruit", "Crop", "Fish", "Fish", "Fish"));
        return new NPC("Dasco", loved, liked, hated, RelationshipStatus.SINGLE);
    }

    public static NPC createEmily() {
        List<Item> loved = createItemList(
            Arrays.asList("Parsnip Seeds", "Cauliflower Seeds", "Potato Seeds", "Wheat Seeds", "Blueberry Seeds", "Grape Seeds"),"Seed");
        List<Item> liked = createItemList(Arrays.asList("Catfish", "Salmon", "Sardine"), "Fish");
        List<Item> hated = createItemList(Arrays.asList("Coal", "Wood"), "Material");
        return new NPC("Emily", loved, liked, hated, RelationshipStatus.SINGLE);
    }

    public static NPC createAbigail() {
        List<Item> loved = createItemList(Arrays.asList("Blueberry", "Melon", "Pumpkin", "Grape", "Cranberry"), "Fruit");
        List<Item> liked = createItemList(Arrays.asList("Baguette", "Pumpkin Pie", "Wine"), "Food");
        List<Item> hated = createItemList(Arrays.asList("Hot Pepper", "Cauliflower", "Parsnip", "Wheat"), "Crop");
        return new NPC("Abigail", loved, liked, hated, RelationshipStatus.SINGLE);
    }
}