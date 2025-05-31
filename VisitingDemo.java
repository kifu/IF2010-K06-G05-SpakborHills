public class VisitingDemo {

    public static void main(String[] args) {
        // 1. Inisialisasi Komponen Game Minimal
        System.out.println("--- Menginisialisasi Komponen Game Minimal ---");
        
        WorldState world = new WorldState(); // Menggunakan WorldState yang sudah ada
        Player asep = new Player("Asep Si Pengunjung", "Male", "Kebun Percobaan");
        
        System.out.println("Inisialisasi Selesai.");
        System.out.println("Lokasi Awal Pemain: " + asep.getLocation() + " (Seharusnya nama farm: " + asep.getFarmName() + ")");
        // Pastikan lokasi awal pemain adalah nama farm-nya jika itu asumsi game Anda
        asep.setLocation(asep.getFarmName()); 
        System.out.println("Waktu Awal: " + world.getCurrentStatus());
        System.out.println("=============================================\n");


        // === SKENARIO 1: Pemain keluar dari ladangnya ke WorldMap ===
        System.out.println("--- SKENARIO 1: Keluar dari '" + asep.getFarmName() + "' ke 'WorldMap' ---");
        asep.setEnergy(50); // Set energi awal untuk aksi ini

        System.out.println("Kondisi Sebelum Aksi:");
        System.out.println("  Lokasi: " + asep.getLocation());
        System.out.println("  Energi: " + asep.getEnergy());
        System.out.println("  Waktu: " + world.getCurrentTime());

        // Kode pemanggil (Game Controller/FarmMap) akan melakukan validasi posisi di FarmMap.
        // Di sini, kita langsung buat aksinya.
        Action exitToWorldMap = new VisitingAction(asep, world, "WorldMap");
        exitToWorldMap.execute(); // validate() hanya akan cek energi

        System.out.println("\nKondisi Setelah Aksi:");
        System.out.println("  Lokasi: " + asep.getLocation()); // Seharusnya "WorldMap"
        System.out.println("  Energi: " + asep.getEnergy());
        System.out.println("  Waktu: " + world.getCurrentTime());
        System.out.println("---------------------------------------------\n");


        // === SKENARIO 2: Pemain mengunjungi "Store" dari "WorldMap" ===
        System.out.println("--- SKENARIO 2: Mengunjungi 'Store' dari 'WorldMap' ---");
        // Pastikan pemain sudah di "WorldMap" dari skenario sebelumnya
        if (!asep.getLocation().equalsIgnoreCase("WorldMap")) {
            System.out.println("Pemain tidak di WorldMap. Mengatur lokasi ke WorldMap untuk demo.");
            asep.setLocation("WorldMap");
        }
        asep.setEnergy(50); // Reset energi

        System.out.println("Kondisi Sebelum Aksi:");
        System.out.println("  Lokasi: " + asep.getLocation());
        System.out.println("  Energi: " + asep.getEnergy());
        System.out.println("  Waktu: " + world.getCurrentTime());

        // Kode pemanggil (Game Controller/WorldMap) akan validasi posisi di WorldMap.
        // Di sini, kita langsung buat aksinya.
        Action visitStore = new VisitingAction(asep, world, "Store");
        visitStore.execute();

        System.out.println("\nKondisi Setelah Aksi:");
        System.out.println("  Lokasi: " + asep.getLocation()); // Seharusnya "Store"
        System.out.println("  Energi: " + asep.getEnergy());
        System.out.println("  Waktu: " + world.getCurrentTime());
        System.out.println("---------------------------------------------\n");


        // === SKENARIO 3: Pemain kembali ke ladangnya dari "WorldMap" ===
        System.out.println("--- SKENARIO 3: Kembali ke '" + asep.getFarmName() + "' dari 'WorldMap' ---");
        if (!asep.getLocation().equalsIgnoreCase("WorldMap")) {
            System.out.println("Pemain tidak di WorldMap. Mengatur lokasi ke WorldMap untuk demo.");
            asep.setLocation("WorldMap");
        }
        asep.setEnergy(50); // Reset energi

        System.out.println("Kondisi Sebelum Aksi:");
        System.out.println("  Lokasi: " + asep.getLocation());
        System.out.println("  Energi: " + asep.getEnergy());
        System.out.println("  Waktu: " + world.getCurrentTime());

        Action returnToFarm = new VisitingAction(asep, world, asep.getFarmName()); // Target adalah nama farm pemain
        returnToFarm.execute();

        System.out.println("\nKondisi Setelah Aksi:");
        System.out.println("  Lokasi: " + asep.getLocation()); // Seharusnya nama farm pemain
        System.out.println("  Energi: " + asep.getEnergy());
        System.out.println("  Waktu: " + world.getCurrentTime());
        System.out.println("---------------------------------------------\n");
        

        // === SKENARIO 4: Gagal karena energi tidak cukup ===
        System.out.println("--- SKENARIO 4: Gagal Visit karena Energi Tidak Cukup ---");
        asep.setLocation("WorldMap"); // Tidak masalah lokasi mana untuk tes energi
        asep.setEnergy(5); // Energi hanya 5, butuh 10

        System.out.println("Kondisi Sebelum Aksi:");
        System.out.println("  Lokasi: " + asep.getLocation());
        System.out.println("  Energi: " + asep.getEnergy());
        System.out.println("  Waktu: " + world.getCurrentTime());

        Action visitLowEnergy = new VisitingAction(asep, world, "MountainLake");
        visitLowEnergy.execute(); // validate() akan return false

        System.out.println("\nKondisi Setelah Aksi (gagal):");
        System.out.println("  Lokasi: " + asep.getLocation()); // Seharusnya tidak berubah
        System.out.println("  Energi: " + asep.getEnergy());   // Seharusnya tidak berubah
        System.out.println("  Waktu: " + world.getCurrentTime()); // Seharusnya tidak berubah
        System.out.println("---------------------------------------------");
    }
}