import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NPCFactory {

    // Helper buat convert List<String> nama item jadi List<Item>
    private static List<Item> createItemList(List<String> itemNames) {
        return itemNames.stream().map(name -> new BasicItem(name)).collect(Collectors.toList());
    }

    public static NPC createMayorTadi() {
        List<Item> loved = createItemList(Arrays.asList("Legend"));
        List<Item> liked = createItemList(Arrays.asList("Angler", "Crimsonfish", "Glacierfish"));
        // hated = semua selain loved dan liked, tapi di NPC class sudah di-handle khusus,
        // jadi kita kasih list kosong di sini
        List<Item> hated = List.of();
        return new NPC("Mayor Tadi", loved, liked, hated);
    }

    public static NPC createCaroline() {
        List<Item> loved = createItemList(Arrays.asList("Firewood", "Coal"));
        List<Item> liked = createItemList(Arrays.asList("Potato", "Wheat"));
        List<Item> hated = createItemList(Arrays.asList("Hot Pepper"));
        return new NPC("Caroline", loved, liked, hated);
    }

    public static NPC createPerry() {
        List<Item> loved = createItemList(Arrays.asList("Cranberry", "Blueberry"));
        List<Item> liked = createItemList(Arrays.asList("Wine"));
        // hated = seluruh item Fish, diasumsikan kamu punya list atau cara mendeteksi item fish
        // sementara, kita bisa masukkan nama item ikan secara manual (atau kosong kalau belum tahu)
        List<Item> hated = createItemList(Arrays.asList("Fish")); // ganti sesuai data fish yang lengkap
        return new NPC("Perry", loved, liked, hated);
    }

    public static NPC createDasco() {
        List<Item> loved = createItemList(Arrays.asList("The Legends of Spakbor", "Cooked Pig's Head", "Wine", "Fugu", "Spakbor Salad"));
        List<Item> liked = createItemList(Arrays.asList("Fish Sandwich", "Fish Stew", "Baguette", "Fish n’ Chips"));
        List<Item> hated = createItemList(Arrays.asList("Legend", "Grape", "Cauliflower", "Wheat", "Pufferfish", "Salmon"));
        return new NPC("Dasco", loved, liked, hated);
    }

    public static NPC createEmily() {
        // loved = seluruh item seeds, misal hanya "Seeds" atau kamu punya list lengkap?
        List<Item> loved = createItemList(Arrays.asList("Seeds"));
        List<Item> liked = createItemList(Arrays.asList("Catfish", "Salmon", "Sardine"));
        List<Item> hated = createItemList(Arrays.asList("Coal", "Wood"));
        return new NPC("Emily", loved, liked, hated);
    }

    public static NPC createAbigail() {
        List<Item> loved = createItemList(Arrays.asList("Blueberry", "Melon", "Pumpkin", "Grape", "Cranberry"));
        List<Item> liked = createItemList(Arrays.asList("Baguette", "Pumpkin Pie", "Wine"));
        List<Item> hated = createItemList(Arrays.asList("Hot Pepper", "Cauliflower", "Parsnip", "Wheat"));
        return new NPC("Abigail", loved, liked, hated);
    }
}
