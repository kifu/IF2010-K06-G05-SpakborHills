// Main.java (Untuk Demo ChattingAction)

import java.util.List;

public class ChattingDemo {
    public static void main(String[] args) {
        // Inisialisasi Game
        WorldState world = new WorldState();
        DefaultItemLoader itemLoader = new DefaultItemLoader();
        List<Item> allGameItems = itemLoader.loadInitialItems();
        Player asep = new Player("Asep Spakbor", "Male", "Kebon Asep");

        // Buat NPC menggunakan Factory
        NPC emily = NPCFactory.createEmily(allGameItems);
        
        System.out.println("=============================================");
        System.out.println("Asep ingin mencoba mengobrol dengan Emily.");
        System.out.println("=============================================\n");

        // --- SKENARIO 1: BERHASIL ---
        System.out.println("--- Percobaan 1: Skenario Berhasil ---");
        // Pindahkan Asep ke lokasi yang benar
        asep.setLocation("Store");
        System.out.println("Asep sekarang berada di " + asep.getLocation() + ".");

        // Tampilkan kondisi awal
        System.out.println("--- Kondisi Awal ---");
        System.out.println("Waktu: " + world.getCurrentStatus());
        System.out.println("Energi Asep: " + asep.getEnergy());
        System.out.println("Heart Points Emily: " + emily.getHeartPoint());
        System.out.println("--------------------");

        // Eksekusi aksi
        Action chatWithEmily = new ChattingAction(asep, world, emily);
        chatWithEmily.execute();
        
        // Tampilkan kondisi akhir
        System.out.println("\n--- Kondisi Akhir ---");
        System.out.println("Waktu: " + world.getCurrentStatus());
        System.out.println("Energi Asep: " + asep.getEnergy());
        System.out.println("Heart Points Emily: " + emily.getHeartPoint());
        System.out.println("----------------------\n\n");


        // --- SKENARIO 2: GAGAL (LOKASI SALAH) ---
        System.out.println("--- Percobaan 2: Skenario Gagal (Lokasi Salah) ---");
        // Pindahkan Asep ke lokasi yang salah
        asep.setLocation("Farm");
        System.out.println("Asep sekarang berada di " + asep.getLocation() + ".");
        
        // Tampilkan kondisi awal
        System.out.println("--- Kondisi Awal ---");
        System.out.println("Waktu: " + world.getCurrentStatus());
        System.out.println("Energi Asep: " + asep.getEnergy());
        System.out.println("Heart Points Emily: " + emily.getHeartPoint());
        System.out.println("--------------------");

        // Eksekusi aksi yang sama
        chatWithEmily.execute();
        
        // Tampilkan kondisi akhir (seharusnya tidak ada perubahan)
        System.out.println("\n--- Kondisi Akhir ---");
        System.out.println("Waktu: " + world.getCurrentStatus());
        System.out.println("Energi Asep: " + asep.getEnergy());
        System.out.println("Heart Points Emily: " + emily.getHeartPoint());
        System.out.println("----------------------");
    }
}