import java.util.List;

public class SellingDemo {
    public static void main(String[] args) {
        // Inisialisasi Game
        DefaultItemLoader itemLoader = new DefaultItemLoader();
        List<Item> allGameItems = itemLoader.loadInitialItems();
        WorldState world = new WorldState();
        Player asep = new Player("Asep Spakbor", "Male", "Asep Farm");
        
        // Buat ShippingBin (Asumsi T bisa berupa Item)
        ShippingBin<Item> shippingBin = new ShippingBin<>();

        // Beri Asep beberapa item untuk dijual
        Item potato = findItemByName(allGameItems, "Potato");
        Item carp = findItemByName(allGameItems, "Carp");

        if (potato != null) asep.getInventory().addItem(potato, 5);
        if (carp != null) asep.getInventory().addItem(carp, 3);

        System.out.println("=============================================");
        System.out.println("Asep ingin menjual beberapa item ke Shipping Bin.");
        System.out.println("=============================================\n");
        
        // Tampilkan kondisi awal
        System.out.println("--- KONDISI AWAL ---");
        System.out.println("Waktu: " + world.getCurrentStatus());
        System.out.println(asep); // Akan menampilkan gold dan energi
        shippingBin.displayBinContents(); // Tampilkan isi bin awal
        System.out.println("---------------------\n");

        // Eksekusi aksi Selling (akan meminta input dari konsol)
        // Anda perlu memasukkan nama item dan kuantitas saat program berjalan.
        // Contoh input saat diminta:
        // Nama item: Potato
        // Jumlah: 2
        Action sellingAction = new SellingAction(asep, world, shippingBin);
        sellingAction.execute();
        
        // Tampilkan kondisi akhir
        System.out.println("\n--- KONDISI AKHIR SETELAH SELLING ---");
        System.out.println("Waktu: " + world.getCurrentStatus());
        System.out.println(asep);
        asep.showInventory(); // Tampilkan inventory setelah menjual
        shippingBin.displayBinContents(); // Tampilkan isi bin setelah menjual
        System.out.println("---------------------");

        // Simulasi akhir hari untuk memproses shipping bin
        System.out.println("\n--- SIMULASI AKHIR HARI ---");
        shippingBin.processEndOfDaySale();
        int goldCollected = shippingBin.claimPendingGold(); // Klaim di "awal hari berikutnya"
        asep.addGold(goldCollected); // Tambahkan gold ke pemain

        System.out.println("\n--- KONDISI SETELAH KLAIM GOLD ---");
        System.out.println(asep); // Tampilkan gold pemain yang sudah bertambah
        System.out.println("----------------------------------");
    }

    // Helper method
    public static Item findItemByName(List<Item> items, String name) {
        if (name == null || name.trim().isEmpty()) return null;
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name.trim())) {
                return item;
            }
        }
        return null;
    }
}