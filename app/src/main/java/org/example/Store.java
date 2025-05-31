package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Singleton Store
public class Store {
    private static Store instance;
    private final List<Item> items;

    private Store(List<Item> initialItems) {
        this.items = (initialItems != null) ? new ArrayList<>(initialItems) : new ArrayList<>();
    }

    public static synchronized void initializeInstance(List<Item> initialItems) {
        if (instance != null) {
            System.err.println("Peringatan: Store.initializeInstance() dipanggil lebih dari sekali.");
            return;
        }
        if (initialItems == null) {
            throw new IllegalArgumentException("Initial items tidak boleh null untuk inisialisasi Store.");
        }
        instance = new Store(initialItems);
    }

    public static Store getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Store belum diinisialisasi. Panggil Store.initializeInstance() terlebih dahulu.");
        }
        return instance;
    }

    public List<Item> getAllItems() {
        return new ArrayList<>(items);
    }

    public List<Item> getItemsForSale() {
        List<Item> forSale = new ArrayList<>();
        for (Item item : this.items) {
            if (item.getBuyPrice() != -1) {
                forSale.add(item);
            }
        }
        return forSale;
    }

    public List<Item> getItemsByCategory(String category) {
        List<Item> filteredItems = new ArrayList<>();
        for (Item item : this.items) {
            if (item.getCategory().equalsIgnoreCase(category) && item.getBuyPrice() != -1) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }

    public Item findItemByName(String name) {
        for (Item item : this.items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

     public void displayItemsForSale(Player player) {
        System.out.println("\n--- Selamat Datang di Toko! ---");
        System.out.println("Item yang tersedia untuk dibeli:");
        System.out.println("-----------------------------------------------------------------");
        // No. | Kategori         | Nama Item                 | Harga Beli     | Keterangan
        System.out.printf("%-4s %-15s %-25s %-15s %n", "No.", "Kategori", "Nama", "Harga Beli");
        System.out.println("-----------------------------------------------------------------");
        List<Item> itemsForSale = getItemsForSale(); // Ambil item yang bisa dijual saja
        for (int i = 0; i < itemsForSale.size(); i++) {
            Item item = itemsForSale.get(i);
            System.out.printf("%-4d %-15s %-25s %-15s %n",
                    (i + 1), // Menampilkan nomor urut 1-based index
                    item.getCategory(),
                    item.getName(),
                    (item.getBuyPrice() == -1 ? "-" : item.getBuyPrice() + "g")
                    );
        }
        System.out.println("-----------------------------------------------------------------");
    }

    // buyItem dengan itemName, tetapi input menggunakan listIndex
    public boolean buyItem(Player player, String itemName, int quantity) {
        if (quantity <= 0) {
            System.out.println("Jumlah pembelian harus lebih dari 0.");
            return false;
        }

        Item itemToBuy = findItemByName(itemName);

        if (itemToBuy == null) {
            System.out.println("Item dengan nama '" + itemName + "' tidak ditemukan di toko.");
            return false;
        }

        if (itemToBuy.getBuyPrice() == -1) {
            System.out.println("'" + itemToBuy.getName() + "' tidak dapat dibeli.");
            return false;
        }

        // Proposal Ring hanya bisa dibeli sekali
        if (itemToBuy.getName().equalsIgnoreCase("Proposal Ring")) {
            if (player.getInventory().hasItem(itemToBuy)) {
                System.out.println("'" + itemToBuy.getName() + "' sudah ada di inventory Anda dan hanya bisa dimiliki satu.");
                return false;
            }
            if (quantity > 1) {
                System.out.println("'" + itemToBuy.getName() + "' hanya dapat dibeli 1 buah dalam satu waktu.");
                quantity = 1;
            }
        }

        // Furniture hanya bisa dibeli sekali
        if (itemToBuy.getName().equalsIgnoreCase("Queen Bed") || itemToBuy.getName().equalsIgnoreCase("King Bed") || itemToBuy.getName().equalsIgnoreCase("Stove")) {
            if (player.getInventory().hasItem(itemToBuy)) {
                System.out.println("'" + itemToBuy.getName() + "' sudah ada di inventory Anda dan hanya bisa dimiliki satu.");
                return false;
            }
            if (quantity > 1) {
                System.out.println("'" + itemToBuy.getName() + "' hanya dapat dibeli 1 buah dalam satu waktu.");
                return false;
            }
        }

        // Resep item hanya bisa dibeli sekali
        if (itemToBuy instanceof RecipeItem) {
            RecipeItem recipeItem = (RecipeItem) itemToBuy;
            if (player.getInventory().hasItem(itemToBuy)) {
                System.out.println("Anda sudah memiliki resep '" + recipeItem.getUnlocksRecipeName() + "'.");
                return false;
            }
            if (quantity > 1) {
                System.out.println("Resep '" + recipeItem.getUnlocksRecipeName() + "' hanya dapat dibeli 1 buah dalam satu waktu.");
                return false;
            }
        }

        int totalCost = itemToBuy.getBuyPrice() * quantity;

        if (player.getGold() < totalCost) {
            System.out.println("Gold tidak cukup! Anda memiliki " + player.getGold() + "g, butuh " + totalCost + "g.");
            return false;
        }

        player.setGold(player.getGold() - totalCost);
        player.getInventory().addItem(itemToBuy, quantity);

        System.out.println("Berhasil membeli " + quantity + "x " + itemToBuy.getName() + " seharga " + totalCost + "g.");
        System.out.println("Sisa gold: " + player.getGold() + "g.");
        return true;
    }

    public static void main(String[] args) {
        // Setup game utama (simulasi)
        DefaultItemLoader loader = new DefaultItemLoader();
        List<Item> initialItems = loader.loadInitialItems();
        Store.initializeInstance(initialItems);

        Store toko = Store.getInstance();
        Player pemain = new Player("PemainNoID", "Male", "KebunTanpaID");
        pemain.setGold(15000);

        Scanner scanner = new Scanner(System.in);
        boolean shopping = true;

        while(shopping) {
            System.out.println("\n" + pemain);
            pemain.getInventory().displayInventory();
            toko.displayItemsForSale(pemain); // Menampilkan item dengan nomor urut

            System.out.print("Masukkan nomor item yang ingin dibeli (atau 0 untuk keluar): ");
            String inputNo = scanner.nextLine();
            int itemNumber;
            try {
                itemNumber = Integer.parseInt(inputNo);
            } catch (NumberFormatException e) {
                System.out.println("Input nomor tidak valid.");
                continue;
            }

            if (itemNumber == 0) {
                shopping = false;
                continue;
            }

            List<Item> itemsForSale = toko.getItemsForSale();
            if (itemNumber < 1 || itemNumber > itemsForSale.size()) {
                System.out.println("Nomor item di luar jangkauan.");
                continue;
            }
            
            Item selectedItem = itemsForSale.get(itemNumber - 1); // Dapatkan item berdasarkan nomor

            int quantity = 1; // Default quantity
            System.out.print("Masukkan jumlah untuk '" + selectedItem.getName() + "': ");
            String quantityInput = scanner.nextLine();
            try {
                quantity = Integer.parseInt(quantityInput);
            } catch (NumberFormatException e) {
                System.out.println("Input jumlah tidak valid. Harus berupa angka dan minimal 1!");
                continue;
            }
            
            toko.buyItem(pemain, selectedItem.getName(), quantity);
        }

        System.out.println("\n--- Sesi Belanja Selesai ---");
        pemain.getInventory().displayInventory();
        System.out.println("Gold terakhir: " + pemain.getGold() + "g");
        scanner.close();
    }
}
