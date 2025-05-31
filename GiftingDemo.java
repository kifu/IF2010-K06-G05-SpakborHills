// Main.java (Untuk Demo GiftingAction)

import java.util.List;

public class GiftingDemo {
    public static void main(String[] args) {
        // Inisialisasi Game
        DefaultItemLoader itemLoader = new DefaultItemLoader();
        List<Item> allGameItems = itemLoader.loadInitialItems();
        WorldState world = new WorldState();
        Player asep = new Player("Asep Spakbor", "Male", "Kebon Asep");

        // Buat NPC menggunakan Factory
        NPC perry = NPCFactory.createPerry(allGameItems);
        
        System.out.println("=============================================");
        System.out.println("Asep ingin mencoba memberikan hadiah kepada Perry.");
        System.out.println("=============================================\n");

        // --- SKENARIO PEMBERIAN HADIAH ---
        
        Item bullhead = findItemByName(allGameItems, "Bullhead");
        
        // Cek jika item ada dan berikan ke pemain
        if (bullhead != null) {
            asep.getInventory().addItem(bullhead, 1);
        } else {
            System.out.println("ERROR: Item 'Bullhead' tidak ditemukan.");
            return;
        }

        // Pindahkan Asep ke lokasi yang benar
        asep.setLocation("Perry House");
        
        // Tampilkan kondisi awal
        System.out.println("--- Kondisi Awal ---");
        System.out.println("Waktu: " + world.getCurrentStatus());
        System.out.println("Energi Asep: " + asep.getEnergy());
        System.out.println("Heart Points Perry: " + perry.getHeartPoint());
        asep.showInventory();
        System.out.println("--------------------");

        // Eksekusi aksi Gifting
        Action givePotatoAction = new GiftingAction(asep, world, perry, bullhead);
        givePotatoAction.execute();
        
        // Tampilkan kondisi akhir
        System.out.println("\n--- Kondisi Akhir ---");
        System.out.println("Waktu: " + world.getCurrentStatus());
        System.out.println("Energi Asep: " + asep.getEnergy());
        System.out.println("Heart Points Perry: " + perry.getHeartPoint());
        asep.showInventory();
        System.out.println("----------------------");
    }

    // Helper method dari Main.java sebelumnya
    public static Item findItemByName(List<Item> items, String name) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
}