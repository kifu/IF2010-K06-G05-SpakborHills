import java.util.List;

public class MarryingDemo {
    public static void main(String[] args) {
        DefaultItemLoader itemLoader = new DefaultItemLoader();
        List<Item> allGameItems = itemLoader.loadInitialItems();
        WorldState world = new WorldState();
        Player asep = new Player("Asep Spakbor", "Male", "Asep Farm");

        Item proposalRing = findItemByName(allGameItems, "Proposal Ring");
        if (proposalRing == null) {
            System.out.println("ERROR: Item 'Proposal Ring' tidak ditemukan.");
            return;
        }

        NPC emily = NPCFactory.createEmily(allGameItems);
        NPC caroline = NPCFactory.createCaroline(allGameItems); // NPC kedua untuk tes

        System.out.println("--- Demo Alur Hubungan ---");

        // 1. Coba lamar Emily (Harusnya berhasil jika kondisi terpenuhi)
        asep.getInventory().addItem(proposalRing, 1);
        asep.setLocation("Store");
        emily.setHeartPoint(150);
        asep.setEnergy(100);
        System.out.println("\n>> Melamar Emily...");
        System.out.println(asep); // Tampilkan status Asep (partner)
        Action proposeEmily = new ProposingAction(asep, world, emily, proposalRing);
        proposeEmily.execute();
        System.out.println(asep); // Tampilkan status Asep setelah melamar

        // 2. Coba lamar Caroline (Harusnya gagal karena Asep sudah punya tunangan)
        asep.getInventory().addItem(proposalRing, 1); // Beri cincin lagi untuk tes
        asep.setLocation("Caroline House");
        caroline.setHeartPoint(150);
        asep.setEnergy(100);
        System.out.println("\n>> Mencoba Melamar Caroline (Asep sudah punya tunangan)...");
        Action proposeCaroline = new ProposingAction(asep, world, caroline, proposalRing);
        proposeCaroline.execute();
        System.out.println(asep);

        // 3. Menikah dengan Emily (jika lamaran Emily berhasil)
        if (asep.getPartner() == emily && emily.getRelationshipStatus() == RelationshipStatus.FIANCE) {
            System.out.println("\n>> Mempersiapkan Pernikahan dengan Emily...");
            // Majukan waktu agar bisa menikah
            System.out.println("Waktu awal: " + world.getCurrentStatus());
            // world.getCurrentTime().advanceMinutes(1440); // 1 hari
            // world.handleNewDay();
            // System.out.println("Waktu setelah dimajukan: " + world.getCurrentStatus());
            System.out.println("Emily siap menikah? " + emily.isReadyToMarry(world.getCurrentTime()));

            asep.setEnergy(100); // Pastikan energi cukup
            if (asep.getInventory().getItemQuantity(proposalRing) == 0){
                asep.getInventory().addItem(proposalRing, 1);
                System.out.println("Menambahkan kembali Proposal Ring untuk formalitas cek di MarryingAction.");
            }


            Action marryEmily = new MarryingAction(asep, world, emily, proposalRing);
            marryEmily.execute();
            System.out.println(asep); // Status setelah menikah
        } else {
            System.out.println("\n>> Pernikahan dengan Emily tidak bisa dilanjutkan (lamaran mungkin gagal).");
        }
        
        // 4. Coba lamar lagi setelah menikah (Harusnya gagal)
        if (asep.getPartner() != null && asep.getPartner().getRelationshipStatus() == RelationshipStatus.SPOUSE) {
            asep.getInventory().addItem(proposalRing, 1); // Beri cincin lagi untuk tes
            asep.setLocation("Store"); // Misal kembali ke Emily
            emily.setHeartPoint(150); // HP Emily masih tinggi
            asep.setEnergy(100);
            System.out.println("\n>> Mencoba Melamar Lagi setelah Menikah...");
            Action proposeAfterMarriage = new ProposingAction(asep, world, emily, proposalRing);
            proposeAfterMarriage.execute();
            System.out.println(asep);
        }
    }

    public static Item findItemByName(List<Item> items, String name) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
}