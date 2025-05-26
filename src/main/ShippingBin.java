import java.util.*;

public class ShippingBin {
    private static final int MAX_UNIQUE_SLOTS = 16;
    private Map<Item, Integer> itemsToSell;
    private boolean hasSoldToday = false;
    private int pendingGold = 0; // Gold dari hasil penjualan yang akan diterima pagi harinya

    public ShippingBin() {
        itemsToSell = new LinkedHashMap<>();
    }

    /**
     * Menambahkan item ke Shipping Bin dari inventory.
     * Item langsung dihapus dari inventory, tidak dapat dikembalikan lagi.
     * Mengembalikan true jika berhasil, false jika gagal.
     */
    public boolean addItem(Item item, int qty, Inventory playerInventory, Time time) {
        if (hasSoldToday) {
            System.out.println("Kamu hanya bisa menjual sekali per hari.");
            return false;
        }
        if (itemsToSell.size() >= MAX_UNIQUE_SLOTS && !itemsToSell.containsKey(item)) {
            System.out.println("Slot unik di Shipping Bin sudah penuh (16).");
            return false;
        }
        if (playerInventory.getItemQuantity(item) < qty) {
            System.out.println("Jumlah item di inventory kurang.");
            return false;
        }
        // Hapus dari inventory, masukkan ke shipping bin
        playerInventory.removeItem(item, qty);
        itemsToSell.put(item, itemsToSell.getOrDefault(item, 0) + qty);

        // Advance waktu 15 menit tiap jualan
        if (time != null) {
            time.advanceMinutes(15);
        }
        System.out.println("Berhasil menaruh " + item.getName() + " x" + qty +
            " ke Shipping Bin. (Waktu berlalu 15 menit)");
        return true;
    }

    /**
     * Proses penjualan semua item dalam shipping bin.
     * Dipanggil saat player tidur (malam hari). Gold belum langsung masuk ke player,
     * melainkan masuk ke pendingGold dan akan diberikan ke player keesokan harinya.
     */
    public void sellAll() {
        int totalGold = 0;
        for (Map.Entry<Item, Integer> entry : itemsToSell.entrySet()) {
            Item item = entry.getKey();
            int qty = entry.getValue();
            int earned = item.getSellPrice() * qty;
            totalGold += earned;
            System.out.println("Menjual " + item.getName() + " x" + qty + " : " + earned + "g");
        }
        itemsToSell.clear();
        hasSoldToday = true;
        pendingGold = totalGold;
        if (totalGold > 0) {
            System.out.println("Hasil penjualan akan kamu terima besok pagi: " + totalGold + "g");
        }
    }

    /**
     * Dipanggil pagi harinya, setelah tidur (time.nextDay()),
     * untuk memasukkan gold ke player dan mereset bin.
     */
    public int collectGoldNextDay() {
        int gold = pendingGold;
        pendingGold = 0;
        hasSoldToday = false;
        return gold;
    }

    /**
     * Reset shipping bin di awal hari baru 
     */
    public void newDay() {
        itemsToSell.clear();
        hasSoldToday = false;
        pendingGold = 0;
    }

    public void displayBin() {
        System.out.println("Shipping Bin:");
        if (itemsToSell.isEmpty()) {
            System.out.println("(Kosong)");
        }
        for (Map.Entry<Item, Integer> entry : itemsToSell.entrySet()) {
            System.out.println(entry.getKey().getName() + " x" + entry.getValue());
        }
    }

    public boolean isFull() {
        return itemsToSell.size() >= MAX_UNIQUE_SLOTS;
    }

    public boolean containsItem(Item item) {
        return itemsToSell.containsKey(item);
    }

    public boolean hasSoldToday() {
        return hasSoldToday;
    }

    /**
     * Untuk pengecekan: apakah ada gold yang bisa di-claim pagi harinya
     */
    public boolean hasPendingGold() {
        return pendingGold > 0;
    }
}