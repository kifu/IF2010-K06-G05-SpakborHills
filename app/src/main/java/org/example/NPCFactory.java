package org.example;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class NPCFactory {
    private static List<Item> itemNamesToItems(List<String> itemNames, List<Item> allGameItems) {
        return itemNames.stream()
                        .map(name -> Item.findItemByName(allGameItems, name)) // Cari item berdasarkan nama
                        .filter(Objects::nonNull) // Hanya ambil item yang berhasil ditemukan (tidak null)
                        .collect(Collectors.toList());
    }

    public static NPC createMayorTadi(List<Item> allGameItems) {
        List<Item> loved = itemNamesToItems(Arrays.asList("Legend"), allGameItems);
        List<Item> liked = itemNamesToItems(Arrays.asList("Angler", "Crimsonfish", "Glacierfish"), allGameItems);
        // Hated = semua selain loved & liked, logika ini ada di dalam kelas NPC
        List<Item> hated = List.of(); // NPC.isSpecialHated akan menangani
        return new NPC("Mayor Tadi", loved, liked, hated, RelationshipStatus.SINGLE);
    }

    public static NPC createCaroline(List<Item> allGameItems) {
        List<Item> loved = itemNamesToItems(Arrays.asList("Firewood", "Coal"), allGameItems);
        List<Item> liked = itemNamesToItems(Arrays.asList("Potato", "Wheat"), allGameItems);
        List<Item> hated = itemNamesToItems(Arrays.asList("Hot Pepper"), allGameItems);
        return new NPC("Caroline", loved, liked, hated, RelationshipStatus.SINGLE);
    }

    public static NPC createPerry(List<Item> allGameItems) {
        List<Item> loved = itemNamesToItems(Arrays.asList("Cranberry", "Blueberry"), allGameItems);
        List<Item> liked = itemNamesToItems(Arrays.asList("Wine"), allGameItems);
        // Hated = seluruh item kategori Fish, logika ini ada di dalam kelas NPC
        List<Item> hated = List.of(); // NPC.isSpecialHated akan menangani
        return new NPC("Perry", loved, liked, hated, RelationshipStatus.SINGLE);
    }

    public static NPC createDasco(List<Item> allGameItems) {
        List<Item> loved = itemNamesToItems(Arrays.asList("The Legends of Spakbor", "Cooked Pig's Head", "Wine", "Fugu", "Spakbor Salad"), allGameItems);
        List<Item> liked = itemNamesToItems(Arrays.asList("Fish Sandwich", "Fish Stew", "Baguette", "Fish n’ Chips"), allGameItems);
        List<Item> hated = itemNamesToItems(Arrays.asList("Legend", "Grape", "Cauliflower", "Wheat", "Pufferfish", "Salmon"), allGameItems);
        return new NPC("Dasco", loved, liked, hated, RelationshipStatus.SINGLE);
    }

    public static NPC createEmily(List<Item> allGameItems) {
        List<Item> loved = itemNamesToItems(
            Arrays.asList("Parsnip Seeds", "Cauliflower Seeds", "Potato Seeds", "Wheat Seeds", "Blueberry Seeds", "Grape Seeds"), allGameItems);
        List<Item> liked = itemNamesToItems(Arrays.asList("Catfish", "Salmon", "Sardine"), allGameItems);
        List<Item> hated = itemNamesToItems(Arrays.asList("Coal", "Firewood"), allGameItems); 
        return new NPC("Emily", loved, liked, hated, RelationshipStatus.SINGLE);
    }

    public static NPC createAbigail(List<Item> allGameItems) {
        List<Item> loved = itemNamesToItems(Arrays.asList("Blueberry", "Melon", "Pumpkin", "Grape", "Cranberry"), allGameItems);
        List<Item> liked = itemNamesToItems(Arrays.asList("Baguette", "Pumpkin Pie", "Wine"), allGameItems);
        List<Item> hated = itemNamesToItems(Arrays.asList("Hot Pepper", "Cauliflower", "Parsnip", "Wheat"), allGameItems);
        return new NPC("Abigail", loved, liked, hated, RelationshipStatus.SINGLE);
    }
}