package main;

import java.util.*;

public class ShippingBin {
    private static final int MAX_UNIQUE_SLOTS = 16; // maksimal 16 item unik per hari
    private Map<Item, Integer> itemsToSell;
    private boolean hasSoldToday = false;

    public ShippingBin() {
        itemsToSell = new LinkedHashMap<>();
    }

    // Menaruh item ke bin
    public boolean addItem(Item item, int qty, Inventory playerInventory) {
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
        // Pindahkan dari inventory ke bin, item di bin tidak bisa kembali ke inventory
        playerInventory.removeItem(item, qty);
        itemsToSell.put(item, itemsToSell.getOrDefault(item, 0) + qty);
        System.out.println("Berhasil menaruh " + item.getName() + " x" + qty + " ke Shipping Bin.");
        return true;
    }

    // Proses penjualan (dipanggil saat tidur)
    public int sellAll() {
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
        return totalGold;
    }

    // Reset bin harian
    public void newDay() {
        hasSoldToday = false;
        itemsToSell.clear();
    }

    public void displayBin() {
        System.out.println("Shipping Bin:");
        for (Map.Entry<Item, Integer> entry : itemsToSell.entrySet()) {
            System.out.println(entry.getKey().getName() + " x" + entry.getValue());
        }
    }

    public boolean isFull() {
        return itemsToSell.size() >= MAX_UNIQUE_SLOTS;
    }
}
