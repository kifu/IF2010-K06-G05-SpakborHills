import java.util.List;

public class ProposingDemo {
    public static void main(String[] args) {
        // Inisialisasi Game
        DefaultItemLoader itemLoader = new DefaultItemLoader();
        List<Item> allGameItems = itemLoader.loadInitialItems();
        WorldState world = new WorldState();
        Player asep = new Player("Asep Spakbor", "Male", "Kebon Asep");

        // Ambil item Proposal Ring dari daftar item game
        Item proposalRing = Item.findItemByName(allGameItems, "Proposal Ring");
        if (proposalRing == null) {
            System.out.println("ERROR: Item 'Proposal Ring' tidak ditemukan di DefaultItemLoader.");
            return;
        }

        // Buat NPC (misalnya Emily)
        NPC emily = NPCFactory.createEmily(allGameItems);
        
        System.out.println("=============================================");
        System.out.println("Asep ingin mencoba melamar Emily.");
        System.out.println("=============================================\n");

        // --- SKENARIO 1: GAGAL (Tidak punya cincin) ---
        System.out.println("--- Percobaan 1: Gagal (Tidak punya cincin) ---");
        asep.setLocation("Store"); // Lokasi benar
        emily.setHeartPoint(150);   // Heart point cukup
        asep.setEnergy(100);       // Energi cukup

        System.out.println("* Kondisi Awal: Energi Asep: " + asep.getEnergy() + ", HP Emily: " + emily.getHeartPoint() + ", Status Emily: " + emily.getRelationshipStatus());
        asep.showInventory(); // Asep belum punya cincin
        
        Action proposeWithoutRing = new ProposingAction(asep, world, emily, proposalRing);
        proposeWithoutRing.execute();
        System.out.println("* Kondisi Akhir: Energi Asep: " + asep.getEnergy() + ", HP Emily: " + emily.getHeartPoint() + ", Status Emily: " + emily.getRelationshipStatus());
        System.out.println("-------------------------------------------------\n");


        // Beri Asep cincin
        asep.getInventory().addItem(proposalRing, 1);

        // --- SKENARIO 2: BERHASIL ---
        System.out.println("--- Percobaan 2: Berhasil Melamar ---");
        // Kondisi sudah diatur sebelumnya (lokasi, HP, energi, punya cincin)
        System.out.println("* Kondisi Awal: Energi Asep: " + asep.getEnergy() + ", HP Emily: " + emily.getHeartPoint() + ", Status Emily: " + emily.getRelationshipStatus());
        asep.showInventory();
        
        Action proposeSuccess = new ProposingAction(asep, world, emily, proposalRing);
        proposeSuccess.execute();
        System.out.println("* Kondisi Akhir: Energi Asep: " + asep.getEnergy() + ", HP Emily: " + emily.getHeartPoint() + ", Status Emily: " + emily.getRelationshipStatus());
        System.out.println("Tanggal Tunangan (total menit): " + emily.getFianceDate());
        asep.showInventory(); // Cincin seharusnya hilang
        System.out.println("-------------------------------------------------\n");

        // --- SKENARIO 3: GAGAL (NPC Sudah Tunangan/Menikah) ---
        System.out.println("--- Percobaan 3: Gagal (Emily Sudah Tunangan) ---");
        asep.setEnergy(100); // Reset energi

        System.out.println("* Kondisi Awal: Energi Asep: " + asep.getEnergy() + ", HP Emily: " + emily.getHeartPoint() + ", Status Emily: " + emily.getRelationshipStatus());
        
        Action proposeAgain = new ProposingAction(asep, world, emily, proposalRing);
        proposeAgain.execute();
        System.out.println("* Kondisi Akhir: Energi Asep: " + asep.getEnergy() + ", HP Emily: " + emily.getHeartPoint() + ", Status Emily: " + emily.getRelationshipStatus());
        asep.showInventory(); // Cincin seharusnya masih ada
        System.out.println("-------------------------------------------------");
    }
}