import java.util.Scanner;

public class Selling implements Action {
    private ShippingBin shippingBin;
    private WorldState worldState;

    public Selling(ShippingBin shippingBin, WorldState worldState) {
        this.shippingBin = shippingBin;
        this.worldState = worldState;
    }

    /**
     * Proses menaruh item ke shipping bin untuk dijual.
     * - Player memilih item dari inventory.
     * - Item langsung dihapus dari inventory dan masuk ke shipping bin.
     * - Waktu berhenti selama memilih, waktu berjalan 15 menit setelah selesai selling.
     */
    @Override
    public void execute(Player player) {
        Inventory inventory = player.getInventory();
        Scanner scanner = new Scanner(System.in);

        inventory.displayInventory();

        System.out.println("Masukkan nama item yang ingin dijual (atau ketik 'batal'):");
        String itemName = scanner.nextLine().trim();

        if (itemName.equalsIgnoreCase("batal")) {
            System.out.println("Penjualan dibatalkan.");
        }

        // Cari item di inventory berdasarkan nama
        Item selected = inventory.getItemByName(itemName);

        if (selected == null) {
            System.out.println("Item tidak ditemukan di inventory.");
        }
        int quantity = 0;
        while (true) {
            System.out.print("Jumlah yang ingin dijual (tersedia: " + inventory.getItemQuantity(selected) + "): ");
            String qtyInput = scanner.nextLine().trim();
            try {
                quantity = Integer.parseInt(qtyInput);
                if (quantity <= 0) {
                    System.out.println("Jumlah harus lebih dari 0.");
                } else if (quantity > inventory.getItemQuantity(selected)) {
                    System.out.println("Jumlah melebihi stok.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Masukkan angka.");
            }
        }

        // Tambahkan ke shipping bin (waktu tidak berjalan di sini)
        boolean success = shippingBin.addItem(selected, quantity, inventory);

        if (success) {
            // Setelah selesai selling, waktu berjalan 15 menit melalui WorldState
            worldState.getCurrentTime().advanceMinutes(getTimeCost());
            System.out.println("Penjualan selesai. Waktu berlalu 15 menit.");
        } else {
            System.out.println("Gagal melakukan selling.");
        }

        scanner.close();
    }

    @Override
    public int getEnergyCost() {
        return 0;
    }

    @Override
    public int getTimeCost() {
        return 15;
    }

    @Override
    public boolean canExecute(Player player) {
        return !shippingBin.hasSoldToday();
    }
}
