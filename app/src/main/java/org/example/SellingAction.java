package org.example;
import java.util.Scanner; 

public class SellingAction extends Action {

    private static final int TIME_COST_MINUTES = 15;
    // Tidak ada energy cost untuk selling

    private ShippingBin<Item> shippingBin;
    private String failureReason;

    private Item itemToSell;
    private int quantityToSell;
    private int calculatedTotalSellValue;
    private boolean userCancelled;
    private String performActionMessage; 

    public SellingAction(Player player, WorldState world, ShippingBin<Item> shippingBin) {
        super(player, world);
        this.shippingBin = shippingBin;
        this.userCancelled = false;
        this.itemToSell = null;
        this.quantityToSell = 0;
        this.calculatedTotalSellValue = 0;
    }

    private int getTotalSellPrice(Item item, int quantity) {
        if (item == null || quantity <= 0) return 0;
        return item.getSellPrice() * quantity;
    }

    @Override
    protected boolean validate() {
        if (shippingBin.hasSoldToday()) {
            this.failureReason = "Kamu hanya bisa menjual item sekali per hari melalui Shipping Bin.";
            return false;
        }
        // Tidak ada biaya energi, jadi ngga perlu cek energi
        return true;
    }

    @Override
    protected void performAction() {
        // Interaksi dengan pengguna untuk memilih item dan kuantitas
        Scanner scanner = new Scanner(System.in); 
        Inventory inventory = player.getInventory();

        System.out.println("\n--- Memulai Proses Penjualan ---");
        inventory.displayInventory();

        System.out.println("Masukkan nama item yang ingin dijual (atau ketik 'batal' untuk membatalkan):");
        String itemNameInput = scanner.nextLine().trim();

        if (itemNameInput.equalsIgnoreCase("batal")) {
            this.userCancelled = true;
            this.performActionMessage = "Penjualan dibatalkan oleh pengguna.";
            // scanner.close(); // Jangan tutup System.in jika scanner ini dibuat dari System.in
            return;
        }

        this.itemToSell = inventory.getItemByName(itemNameInput);

        if (this.itemToSell == null) {
            this.userCancelled = true; // Anggap batal jika item tidak ada
            this.performActionMessage = "Item '" + itemNameInput + "' tidak ditemukan di inventory.";
            // scanner.close();
            return;
        }
        
        if (this.itemToSell.getSellPrice() < 0) { // Item yang tidak bisa dijual (harga -1)
            this.userCancelled = true; // Anggap batal
            this.performActionMessage = itemToSell.getName() + " tidak dapat dijual (harga jual tidak valid).";
            // scanner.close();
            return;
        }

        int availableQuantity = inventory.getItemQuantity(this.itemToSell);
        while (true) {
            System.out.print("Jumlah '" + this.itemToSell.getName() + "' yang ingin dijual (tersedia: " + availableQuantity + ", ketik 0 untuk batal): ");
            String qtyInput = scanner.nextLine().trim();
            try {
                this.quantityToSell = Integer.parseInt(qtyInput);
                if (this.quantityToSell == 0) {
                    this.userCancelled = true;
                    this.performActionMessage = "Penjualan item '" + this.itemToSell.getName() + "' dibatalkan.";
                    // scanner.close();
                    return;
                }
                if (this.quantityToSell < 0) {
                    System.out.println("Jumlah harus positif.");
                } else if (this.quantityToSell > availableQuantity) {
                    System.out.println("Jumlah melebihi stok yang tersedia (" + availableQuantity + ").");
                } else {
                    break; // Jumlah valid
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Masukkan angka untuk jumlah.");
            }
        }

        this.calculatedTotalSellValue = getTotalSellPrice(this.itemToSell, this.quantityToSell);
        System.out.println("Item: " + this.itemToSell.getName() + " x" + this.quantityToSell);
        System.out.println("Potensi pendapatan: " + this.calculatedTotalSellValue + "g (akan diterima besok pagi).");
        // scanner.close(); // Sebaiknya jangan tutup Scanner jika dibuat dari System.in
    }

    @Override
    protected void applyEffects() {
        if (this.userCancelled || this.itemToSell == null || this.quantityToSell <= 0) {
            // Tidak ada efek jika dibatalkan atau item/kuantitas tidak valid dari performAction
            return;
        }

        boolean successPuttingToBin = shippingBin.addItem(this.itemToSell, this.quantityToSell, player.getInventory());

        if (successPuttingToBin) {
            // Majukan waktu game
            int dayBefore = world.getCurrentTime().getDay();
            world.getCurrentTime().advanceMinutes(TIME_COST_MINUTES);
            if (world.getCurrentTime().getDay() > dayBefore) {
                world.handleNewDay();
            }
        } else {
            this.performActionMessage = "Gagal memasukkan item ke Shipping Bin (mungkin slot unik penuh).";
            this.userCancelled = true; 
        }
    }

    @Override
    protected String getSuccessMessage() {
        if (this.userCancelled) {
            return this.performActionMessage; 
        }
        if (this.itemToSell != null && this.quantityToSell > 0) {
            return "Berhasil menjual " + this.itemToSell.getName() + " x" + this.quantityToSell +
                   " senilai " + this.calculatedTotalSellValue + "g ke Shipping Bin. Waktu berlalu " +
                   TIME_COST_MINUTES + " menit.";
        }
        return "Proses penjualan selesai."; 
    }

    @Override
    protected String getFailureMessage() {
        return this.failureReason;
    }
}