// Main.java (Untuk Demo FishingAction)

import java.util.List;

public class FishingDemo {
    public static void main(String[] args) {
        // Inisialisasi Game
        DefaultItemLoader itemLoader = new DefaultItemLoader();
        List<Item> allGameItems = itemLoader.loadInitialItems();
        WorldState world = new WorldState();
        Player asep = new Player("Asep Spakbor", "Male", "Kebon Asep");
        
        // Siapkan pemain untuk memancing
        asep.setLocation("Mountain Lake"); // Pindah ke lokasi memancing
        
        // Beri pemain pancingan
        Item fishingRod = findItemByName(allGameItems, "Fishing Rod");
        if (fishingRod != null) {
            asep.getInventory().addItem(fishingRod, 1);
        } else {
            System.out.println("Error: Fishing Rod tidak ditemukan di loader.");
            return;
        }

        System.out.println("=============================================");
        System.out.println("Asep tiba di " + asep.getLocation() + " dan bersiap untuk memancing.");
        System.out.println("Status Dunia: " + world.getCurrentStatus());
        System.out.println("=============================================\n");

        // Tampilkan kondisi awal
        System.out.println("--- KONDISI AWAL ---");
        System.out.println(asep);
        asep.showInventory();
        System.out.println("---------------------\n");
        
        // Eksekusi aksi memancing
        Action fishingAction = new FishingAction(asep, world, allGameItems);
        fishingAction.execute();

        System.out.println("\n--- KONDISI AKHIR ---");
        System.out.println("Status Dunia: " + world.getCurrentStatus());
        System.out.println(asep);
        asep.showInventory();
        System.out.println("---------------------");
    }
    
    // Helper method
    public static Item findItemByName(List<Item> items, String name) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
}