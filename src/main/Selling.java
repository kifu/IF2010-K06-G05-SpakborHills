package main;

import java.util.Scanner;

public class Selling implements Action {
    @Override
    public void execute(Player player) {
        Scanner scanner = new Scanner(System.in);
        Inventory inventory = player.getInventory();
        ShippingBin shippingBin = player.getShippingBin();

        // Tampilkan inventory player
        inventory.displayInventory();

        System.out.print("Masukkan nama item yang ingin dijual: ");
        String itemName = scanner.nextLine().trim();

        // Cari item di inventory berdasarkan nama (case-insensitive)
        Item itemToSell = null;
        for (Item item : inventory.getAllItems()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                itemToSell = item;
                break;
            }
        }
        if (itemToSell == null) {
            System.out.println("Item tidak ditemukan di inventory.");
            return;
        }

        System.out.print("Masukkan jumlah yang ingin dijual: ");
        int qty;
        try {
            qty = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Jumlah tidak valid.");
            return;
        }

        if (qty <= 0) {
            System.out.println("Jumlah harus lebih dari 0.");
            return;
        }

        // Cek cukup item di inventory
        if (inventory.getItemQuantity(itemToSell) < qty) {
            System.out.println("Jumlah item di inventory tidak cukup.");
            return;
        }

        // Cek apakah shipping bin full slot unik
        if (shippingBin.isFull() && !shippingBin.containsItem(itemToSell)) {
            System.out.println("Shipping Bin sudah penuh slot unik (16 item unik per hari).");
            return;
        }

        // Masukkan ke shipping bin
        boolean success = shippingBin.addItem(itemToSell, qty, inventory);
        if (success) {
            // Simulasi waktu berjalan 15 menit (implementasi time system sesuai game-mu)
            System.out.println("Menjual " + qty + "x " + itemToSell.getName() + " ke Shipping Bin. Waktu berlalu 15 menit.");
            // (Opsional) tambahkan integrasi sistem waktu di sini
        }
    }

    @Override
    public int getEnergyCost() {
        return 0;
    }

    @Override
    public int getTimeCost() {
        return 15; // dalam menit
    }

    @Override
    public boolean canExecute(Player player) {
        return player.getLocation().equalsIgnoreCase("Farm")
            || player.getLocation().equalsIgnoreCase("ShippingBin");
    }
}
