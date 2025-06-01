import java.util.LinkedHashMap;
import java.util.Map;

public class Inventory {
    private Map<Item, Integer> inventory;

    public Inventory() {
        inventory = new LinkedHashMap<>();  
        
        // Menambahkan beberapa item awal ke inventory
        // inventory.put(new Seeds("Parsnips Seeds", Season.SPRING, 1, 20), 15);
        // inventory.put(new Equipment("Hoe"), 1);
        // inventory.put(new Equipment("Watering Can"), 1);
        // inventory.put(new Equipment("Pickaxe"), 1);
        // inventory.put(new Equipment("Fishing Rod"), 1);
    }

    public Map<Item, Integer> getInventory() {
        return inventory;
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

    // Untuk item yang buyOnce saja
    public boolean hasItem(Item item) {
        for (Item invItem : inventory.keySet()) {
            if (invItem.equals(item)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasItem(String itemName) {
        if (itemName == null || itemName.trim().isEmpty()) {
            return false;
        }
        for (Item invItem : inventory.keySet()) {
            if (invItem.getName().equalsIgnoreCase(itemName.trim())) {
                return true;
            }
        }
        return false;
    }

    public Item getItemByName(String name) {
    if (name == null || name.trim().isEmpty()) return null;
    for (Item item : inventory.keySet()) {
        if (item.getName().equalsIgnoreCase(name.trim())) {
            return item;
        }
    }
    return null;
}
}
