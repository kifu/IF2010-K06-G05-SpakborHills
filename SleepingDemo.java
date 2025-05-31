// Contoh di Main.java atau dalam loop game utama Anda

public class SleepingDemo {
    public static void main(String[] args) {
        WorldState world = new WorldState();
        // Misal SLEEP_LOCATION di SleepingAction adalah "House"
        Player asep = new Player("Asep Spakbor", "Male", "NamaFarmAsep"); 
        
        // Skenario: Asep berada di "Mountain Lake", waktu menunjukkan 02:00 pagi.
        asep.setLocation("Mountain Lake");
        world.getCurrentTime().setTime(2, 0); // Set waktu ke jam 2 pagi

        System.out.println("--- Skenario Tidur Otomatis di Lokasi Salah ---");
        System.out.println("Kondisi Awal: " + world.getCurrentStatus() +
                           ", Lokasi Asep: " + asep.getLocation() +
                           ", Energi Asep: " + asep.getEnergy());

        // Logika deteksi tidur otomatis oleh sistem game:
        if (world.getCurrentTime().getHours() >= 2 || world.getCurrentTime().getHours() < 6 || asep.getEnergy() <=0) {
            System.out.println("Sistem mendeteksi kondisi tidur otomatis!");
            Action autoSleepAction = new SleepingAction(asep, world, true); // true untuk isAutomaticSleep
            autoSleepAction.execute(); // Ini akan melewati cek lokasi karena isAutomaticSleep = true
        } else {
            // Jika pemain memilih tidur secara manual (misal interaksi dengan tempat tidur)
            // Action playerInitiatedSleep = new SleepingAction(asep, world, false);
            // playerInitiatedSleep.execute(); 
            // (Ini akan gagal jika Asep tidak di "House")
            System.out.println("Kondisi tidur otomatis tidak terpenuhi.");
        }

        System.out.println("\nKondisi Akhir: " + world.getCurrentStatus() +
                           ", Lokasi Asep: " + asep.getLocation() + // Lokasi tidak berubah saat tidur otomatis
                           ", Energi Asep: " + asep.getEnergy());
        System.out.println("-------------------------------------------------");


        // Skenario: Pemain memilih tidur di lokasi yang benar
        asep.setLocation("House"); // Pindah ke lokasi tidur yang benar
        asep.setEnergy(80);
        world.getCurrentTime().setTime(21,0); // Jam 9 malam
        System.out.println("\n--- Skenario Pemain Memilih Tidur di Lokasi Benar ---");
        System.out.println("Kondisi Awal: " + world.getCurrentStatus() +
                           ", Lokasi Asep: " + asep.getLocation() +
                           ", Energi Asep: " + asep.getEnergy());
        
        Action chosenSleepAction = new SleepingAction(asep, world, false); // false untuk tidur yang dipilih
        chosenSleepAction.execute();

        System.out.println("\nKondisi Akhir: " + world.getCurrentStatus() +
                           ", Lokasi Asep: " + asep.getLocation() +
                           ", Energi Asep: " + asep.getEnergy());
        System.out.println("-------------------------------------------------");

        // Skenario: Pemain kecapekan tidur otomatis di lokasi yang salah
        asep.setLocation("Forest River"); // Pindah ke lokasi tidur yang salah
        asep.setEnergy(-20);  
        world.getCurrentTime().setTime(21,0); // Jam 9 malam
        System.out.println("\n--- Skenario Pemain kecapekan tidur otomatis di lokasi yang salah ---");
        System.out.println("Kondisi Awal: " + world.getCurrentStatus() +
                           ", Lokasi Asep: " + asep.getLocation() +
                           ", Energi Asep: " + asep.getEnergy());
        
        Action tiredSleepAction = new SleepingAction(asep, world, true); // false untuk tidur yang dipilih
        tiredSleepAction.execute();

        System.out.println("\nKondisi Akhir: " + world.getCurrentStatus() +
                           ", Lokasi Asep: " + asep.getLocation() +
                           ", Energi Asep: " + asep.getEnergy());
        System.out.println("-------------------------------------------------");


        // Skenario: Pemain memilih tidur di lokasi yang salah
        asep.setLocation("Forest River"); // Pindah ke lokasi tidur yang salah
        asep.setEnergy(80);
        world.getCurrentTime().setTime(22,0); // Jam 10 malam
        System.out.println("\n--- Skenario Pemain Memilih Tidur di Lokasi Salah ---");
        System.out.println("Kondisi Awal: " + world.getCurrentStatus() +
                           ", Lokasi Asep: " + asep.getLocation() +
                           ", Energi Asep: " + asep.getEnergy());
        
        Action chosenSleepWrongLocAction = new SleepingAction(asep, world, false); // false untuk tidur yang dipilih
        chosenSleepWrongLocAction.execute(); // Ini akan GAGAL karena lokasi salah

        System.out.println("\nKondisi Akhir: " + world.getCurrentStatus() + // Waktu tidak akan maju, energi tidak berubah
                           ", Lokasi Asep: " + asep.getLocation() +
                           ", Energi Asep: " + asep.getEnergy());
        System.out.println("-------------------------------------------------");


    }
}