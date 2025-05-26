public class ShippingBinDriver {
    public static void main(String[] args) {
        // Setup
        Time time = new Time(1, 6, 0);
        Player player = new Player("Adit", "Male", "Spakbor Farm");
        ShippingBin shippingBin = new ShippingBin();
        Inventory inventory = player.getInventory();

        // Tambah item ke inventory
        Item firewood = new Equipment("Firewood"); 
        inventory.addItem(firewood, 10);

        // Player menaruh item ke Shipping Bin
        shippingBin.addItem(firewood, 5, inventory, time); // waktu otomatis maju 15 menit
        shippingBin.displayBin();
        inventory.displayInventory();

        // Player tidur (malam hari)
        System.out.println("Player tidur...");
        shippingBin.sellAll(); // proses penjualan, gold belum masuk ke player

        // Hari berikutnya
        time.nextDay();
        System.out.println("Pagi hari! Player menerima gold hasil penjualan.");
        int gold = shippingBin.collectGoldNextDay();
        player.setGold(player.getGold() + gold);
        System.out.println("Player Gold: " + player.getGold());
        // Sekarang shipping bin kosong lagi, bisa dipakai lagi hari berikutnya
    }
}
