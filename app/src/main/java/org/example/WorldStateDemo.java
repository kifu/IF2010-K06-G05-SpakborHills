package org.example;

public class WorldStateDemo {
    public static void main(String[] args) {
        // Membuat objek WorldState
        WorldState worldState = new WorldState();

        // Simulasi: Update setiap detik di dunia nyata
        // Total simulasi waktu (misalnya, 1 menit simulasi di dunia game)
        long realTimeDeltaMillis = 1000; // 1000 mililisecond = 1 detik di dunia nyata

        // worldState.update(realTimeDeltaMillis);
        // worldState.update(realTimeDeltaMillis);

        System.out.println(worldState.getCurrentStatus());

        // Update selama 30 detik dunia nyata (karena 1 detik di dunia nyata = 5 menit di dunia game)
        for (int i = 0; i < 30; i++) {
            // Menjalankan update setiap detik dunia nyata
            worldState.update(realTimeDeltaMillis);

            // Menampilkan status dunia saat ini
            System.out.println(worldState.getCurrentStatus());

            // Tunggu 1 detik dunia nyata
            try {
                Thread.sleep(1000); // 1000 ms = 1 detik
            } catch (InterruptedException e) {
                System.out.println("Terjadi kesalahan dalam pengaturan waktu.");
            }
        }
    }
}
