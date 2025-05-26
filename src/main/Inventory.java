import java.util.LinkedHashMap;
import java.util.Map;

public class Inventory {
    private Map<Item, Integer> inventory;

    public Inventory() {
        inventory = new LinkedHashMap<>();  
        
        // Menambahkan beberapa item awal ke inventory
        // inventory.put(new Seeds("Parsnips Seeds", 20, 10), 15);
        inventory.put(new Equipment("Hoe"), 1);
        inventory.put(new Equipment("Watering Can"), 1);
        inventory.put(new Equipment("Pickaxe"), 1);
        inventory.put(new Equipment("Fishing Rod"), 1);
    }

    public void addItem(Item item, int quantity) {
        if (inventory.containsKey(item)) {
            inventory.put(item, inventory.get(item) + quantity);
        }
        else {
            inventory.put(item, quantity);
        }
    }

    public void removeItem(Item item, int quantity) {
        if (inventory.containsKey(item)) {
            int currentQuantity = inventory.get(item);
            if (currentQuantity > quantity) {
                inventory.put(item, currentQuantity - quantity);
            }
            else if (currentQuantity == quantity) {
                inventory.remove(item);
            }
            else {
                System.out.println("Jumlah item yang ingin dihapus melebihi jumlah yang ada di inventory.");
            }
        }
        else {
            System.out.println("Item tidak ditemukan di inventory.");
        }
    }

    public int getItemQuantity(Item item) {
        return inventory.getOrDefault(item, 0);
    }

    public void displayInventory() {
        System.out.println("Inventory:");
        for (Map.Entry<Item, Integer> entry : inventory.entrySet()) {
            Item item = entry.getKey();
            System.out.println(item.getName() + " x" + getItemQuantity(item));
        }
    }
     public Item getItemByName(String name) {
        for (Item item : inventory.keySet()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
}
