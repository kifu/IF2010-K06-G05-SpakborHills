package org.spakbor;

import java.util.LinkedHashMap;
import java.util.Map;

// Generics ShippingBin
public class ShippingBin<T extends Item> {
    private static final int MAX_UNIQUE_SLOTS = 16; // Sesuai spesifikasi
    private final Map<T, Integer> itemsToSell;
    private boolean hasSoldToday = false;
    private int pendingGold = 0;

    public ShippingBin() {
        this.itemsToSell = new LinkedHashMap<>();
    }

    public boolean addItem(T itemToAdd, int quantity, Inventory playerInventory) { 
        if (hasSoldToday) {
            System.out.println("Kamu hanya bisa menjual item sekali per hari melalui Shipping Bin.");
            return false;
        }

        if (itemsToSell.size() >= MAX_UNIQUE_SLOTS && !itemsToSell.containsKey(itemToAdd)) {
            System.out.println("Slot unik di Shipping Bin sudah penuh (" + MAX_UNIQUE_SLOTS + "). Tidak bisa menambah jenis item baru.");
            return false;
        }

        int currentQuantityInInventory = playerInventory.getItemQuantity(itemToAdd);
        if (currentQuantityInInventory < quantity) {
            System.out.println("Jumlah '" + itemToAdd.getName() + "' di inventory tidak mencukupi (tersedia: " + currentQuantityInInventory + ", butuh: " + quantity + ").");
            return false;
        }

        playerInventory.removeItem(itemToAdd, quantity);

        // Setelah item dihapus dari inventory, tambahkan ke shipping bin.
        itemsToSell.put(itemToAdd, itemsToSell.getOrDefault(itemToAdd, 0) + quantity);
        System.out.println("Berhasil menaruh " + itemToAdd.getName() + " x" + quantity + " ke Shipping Bin.");
        // Efek waktu +15 menit setelah selesai Selling harus ditangani oleh sistem game utama.
        return true; // Item berhasil ditambahkan
    }

    // Memproses penjualan semua item di shipping bin pada akhir hari.
    public void processEndOfDaySale() {
        if (itemsToSell.isEmpty()) {
            // System.out.println("Shipping Bin kosong, tidak ada item yang dijual hari ini."); // Komentar ini bisa diaktifkan jika perlu
            hasSoldToday = true;
            pendingGold = 0;
            return;
        }

        int totalGoldThisSale = 0;
        System.out.println("\n--- Memproses Penjualan Akhir Hari dari Shipping Bin ---");
        for (Map.Entry<T, Integer> entry : itemsToSell.entrySet()) {
            T item = entry.getKey();
            int qty = entry.getValue();
            int itemSellPrice = item.getSellPrice();

            if (itemSellPrice == -1) { 
                System.out.println(item.getName() + " x" + qty + " tidak dapat dijual (harga jual tidak valid).");
                continue;
            }
            
            int earned = itemSellPrice * qty;
            totalGoldThisSale += earned;
            System.out.println("Menjual: " + item.getName() + " (Kategori: " + item.getCategory() + ") x" + qty + " = " + earned + "g");
        }

        itemsToSell.clear(); 
        hasSoldToday = true; 
        pendingGold = totalGoldThisSale; // Simpan total gold untuk diklaim besok

        if (totalGoldThisSale > 0) {
            System.out.println("Total penjualan hari ini: " + totalGoldThisSale + "g. Akan diterima besok pagi.");
        } else {
            System.out.println("Tidak ada gold yang didapatkan dari penjualan hari ini.");
        }
        System.out.println("------------------------------------------------------\n");
    }

    // Mengambil gold yang tertunda dari penjualan kemarin dan mereset status harian bin.
    public int claimPendingGold() {
        int goldCollected = pendingGold;
        pendingGold = 0;
        hasSoldToday = false; // Reset status, bin siap untuk penjualan hari baru
        if (goldCollected > 0) {
            System.out.println(goldCollected + "g dari penjualan kemarin telah diterima.");
        }
        return goldCollected;
    }

    /**
     * Menampilkan isi Shipping Bin saat ini.
     */
    public void displayBinContents() {
        System.out.println("--- Isi Shipping Bin Saat Ini ---");
        if (itemsToSell.isEmpty()) {
            System.out.println("(Kosong)");
        } else {
            for (Map.Entry<T, Integer> entry : itemsToSell.entrySet()) {
                T item = entry.getKey();
                System.out.println("- " + item.getName() + " (Kategori: " + item.getCategory() + ") x" + entry.getValue());
            }
        }
        System.out.println("Slot item unik terpakai: " + itemsToSell.size() + "/" + MAX_UNIQUE_SLOTS);
        if (hasSoldToday) {
            System.out.println("(Penjualan untuk hari ini sudah diproses/ditutup)");
        }
        System.out.println("---------------------------------");
    }

    public boolean isUniqueSlotsFull() {
        return itemsToSell.size() >= MAX_UNIQUE_SLOTS;
    }

    public boolean containsItemType(T item) {
        return itemsToSell.containsKey(item);
    }

    public boolean hasSoldToday() {
        return hasSoldToday;
    }

    public boolean hasPendingGold() {
        return pendingGold > 0;
    }
}