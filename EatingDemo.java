// Main.java (Untuk Demo EatingAction)

import java.util.List;

public class EatingDemo {
    public static void main(String[] args) {
        // Inisialisasi Game
        DefaultItemLoader itemLoader = new DefaultItemLoader();
        List<Item> allGameItems = itemLoader.loadInitialItems();
        WorldState world = new WorldState();
        Player asep = new Player("Asep Spakbor", "Male", "Kebon Asep");

        // Siapkan Skenario
        // Beri pemain ikan untuk dimakan
        Item carp = Item.findItemByName(allGameItems, "Carp");
        if (carp != null) {
            asep.getInventory().addItem(carp, 1);
        } else {
            System.out.println("Error: Item 'Carp' tidak ditemukan.");
            return;
        }
        
        // Kurangi energi pemain secara manual untuk demo
        asep.setEnergy(50); 
        
        System.out.println("Skenario: Energi Asep rendah setelah bekerja keras.");
        System.out.println("====================================================\n");

        // Tampilkan kondisi awal
        System.out.println("--- KONDISI AWAL ---");
        System.out.println("Status Waktu: " + world.getCurrentStatus());
        System.out.println(asep);
        asep.showInventory();
        System.out.println("---------------------\n");

        // Eksekusi aksi makan
        // Pemain memilih untuk memakan ikan Carp dari inventory
        Action eatingAction = new EatingAction(asep, world, carp);
        eatingAction.execute();
        
        // Tampilkan kondisi akhir
        System.out.println("\n--- KONDISI AKHIR ---");
        System.out.println("Status Waktu: " + world.getCurrentStatus());
        System.out.println(asep);
        asep.showInventory();
        System.out.println("---------------------");
    }
}