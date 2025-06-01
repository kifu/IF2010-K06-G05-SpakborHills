package org.spakbor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Singleton Store
public class Store {
    private static Store instance;
    private final List<Item> itemsForSale;
    private List<Recipe> allGameRecipes;

    private Store(List<Item> initialItems, List<Recipe> gameRecipes) {
        this.itemsForSale = (initialItems != null) ? new ArrayList<>(initialItems) : new ArrayList<>();
        this.allGameRecipes = (gameRecipes != null) ? new ArrayList<>(gameRecipes) : new ArrayList<>();
    }

    public static synchronized void initializeInstance(List<Item> initialItems, List<Recipe> gameRecipes) {
        if (instance != null) {
            System.err.println("Peringatan: Store.initializeInstance() dipanggil lebih dari sekali.");
            return;
        }
        if (initialItems == null) {
            throw new IllegalArgumentException("Initial items tidak boleh null untuk inisialisasi Store.");
        }
        if (gameRecipes == null) {
            throw new IllegalArgumentException("Daftar resep game tidak boleh null untuk inisialisasi Store.");
        }
        instance = new Store(initialItems, gameRecipes);
    }

    public static Store getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Store belum diinisialisasi. Panggil Store.initializeInstance() terlebih dahulu.");
        }
        return instance;
    }

    public List<Item> getAllItems() {
        return new ArrayList<>(itemsForSale);
    }

    public List<Item> getItemsForSale() {
        List<Item> forSale = new ArrayList<>();
        for (Item item : this.itemsForSale) {
            if (item.getBuyPrice() != -1) {
                forSale.add(item);
            }
        }
        return forSale;
    }

    public List<Item> getItemsByCategory(String category) {
        List<Item> filteredItems = new ArrayList<>();
        for (Item item : this.itemsForSale) {
            if (item.getCategory().equalsIgnoreCase(category) && item.getBuyPrice() != -1) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }

    public Item findItemByName(String name) {
        for (Item item : this.itemsForSale) {
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

    public int buyItem(Player player, String itemName, int quantity) {
        if (quantity <= 0) {
            System.out.println("Jumlah pembelian harus lebih dari 0.");
            return 0; 
        }

        Item itemToBuy = findItemByName(itemName);

        if (itemToBuy == null) {
            System.out.println("Item dengan nama '" + itemName + "' tidak ditemukan di toko.");
            return 0;
        }

        if (itemToBuy instanceof RecipeItem) {
            RecipeItem recipeItemBeingBought = (RecipeItem) itemToBuy;
            String recipeNameToUnlock = recipeItemBeingBought.getUnlocksRecipeName();

            if (player.hasRecipe(recipeNameToUnlock)) {
                System.out.println("Anda sudah memiliki resep '" + recipeNameToUnlock + "'.");
                return 0;
            }

            Recipe actualRecipeToUnlock = null;
            if (this.allGameRecipes != null) {
                for (Recipe r : this.allGameRecipes) {
                    if (r.getRecipeName().equalsIgnoreCase(recipeNameToUnlock)) {
                        actualRecipeToUnlock = r;
                        break;
                    }
                }
            }

            if (actualRecipeToUnlock == null) {
                System.out.println("Error internal: Definisi resep untuk '" + recipeNameToUnlock + "' tidak ditemukan.");
                return 0;
            }

            int cost = recipeItemBeingBought.getBuyPrice();
            if (player.getGold() < cost) {
                System.out.println("Gold tidak cukup untuk membeli resep! Anda memiliki " + player.getGold() + "g, butuh " + cost + "g.");
                return 0;
            }

            player.setGold(player.getGold() - cost);
            player.unlockRecipe(actualRecipeToUnlock); 
            System.out.println("Berhasil membeli dan membuka resep: " + recipeNameToUnlock + " seharga " + cost + "g.");
            System.out.println("Sisa gold: " + player.getGold() + "g.");
            return cost; 
        }

        if (itemToBuy.getBuyPrice() == -1) {
            System.out.println("'" + itemToBuy.getName() + "' tidak dapat dibeli.");
            return 0;
        }

        if (itemToBuy.getName().equalsIgnoreCase("Proposal Ring")) {
            if (player.getInventory().hasItem(itemToBuy.getName())) {
                System.out.println("'" + itemToBuy.getName() + "' sudah ada di inventory Anda dan hanya bisa dimiliki satu.");
                return 0;
            }
            if (quantity > 1) {
                System.out.println("'" + itemToBuy.getName() + "' hanya dapat dibeli 1 buah dalam satu waktu.");
                quantity = 1;
            }
        }

        if (itemToBuy instanceof Furniture) {
            if (player.hasOwnedFurniture(itemToBuy.getName())) {
                System.out.println("'" + itemToBuy.getName() + "' sudah Anda miliki dan hanya bisa dibeli satu.");
                return 0;
            }
            if (quantity > 1) {
                System.out.println("'" + itemToBuy.getName() + "' hanya dapat dibeli 1 buah dalam satu waktu.");
                quantity = 1;
            }
        }

        int totalCost = itemToBuy.getBuyPrice() * quantity;

        if (player.getGold() < totalCost) {
            System.out.println("Gold tidak cukup! Anda memiliki " + player.getGold() + "g, butuh " + totalCost + "g.");
            return 0;
        }

        player.setGold(player.getGold() - totalCost);

        if (!(itemToBuy instanceof Furniture) && !(itemToBuy instanceof RecipeItem)) {
            player.getInventory().addItem(itemToBuy, quantity);
        }

        System.out.println("Berhasil membeli " + quantity + "x " + itemToBuy.getName() + " seharga " + totalCost + "g.");
        System.out.println("Sisa gold: " + player.getGold() + "g.");
        return totalCost;
    }

    // public static void main(String[] args) {
    //     // Setup game utama (simulasi)
    //     DefaultItemLoader loader = new DefaultItemLoader();
    //     List<Item> initialItems = loader.loadInitialItems();
    //     Store.initializeInstance(initialItems);

    //     Store toko = Store.getInstance();
    //     Player pemain = new Player("PemainNoID", "Male", "KebunTanpaID");
    //     pemain.setGold(15000);

    //     Scanner scanner = new Scanner(System.in);
    //     boolean shopping = true;

    //     while(shopping) {
    //         System.out.println("\n" + pemain);
    //         pemain.getInventory().displayInventory();
    //         toko.displayItemsForSale(pemain); // Menampilkan item dengan nomor urut

    //         System.out.print("Masukkan nomor item yang ingin dibeli (atau 0 untuk keluar): ");
    //         String inputNo = scanner.nextLine();
    //         int itemNumber;
    //         try {
    //             itemNumber = Integer.parseInt(inputNo);
    //         } catch (NumberFormatException e) {
    //             System.out.println("Input nomor tidak valid.");
    //             continue;
    //         }

    //         if (itemNumber == 0) {
    //             shopping = false;
    //             continue;
    //         }

    //         List<Item> itemsForSale = toko.getItemsForSale();
    //         if (itemNumber < 1 || itemNumber > itemsForSale.size()) {
    //             System.out.println("Nomor item di luar jangkauan.");
    //             continue;
    //         }
            
    //         Item selectedItem = itemsForSale.get(itemNumber - 1); // Dapatkan item berdasarkan nomor

    //         int quantity = 1; // Default quantity
    //         System.out.print("Masukkan jumlah untuk '" + selectedItem.getName() + "': ");
    //         String quantityInput = scanner.nextLine();
    //         try {
    //             quantity = Integer.parseInt(quantityInput);
    //         } catch (NumberFormatException e) {
    //             System.out.println("Input jumlah tidak valid. Harus berupa angka dan minimal 1!");
    //             continue;
    //         }
            
    //         toko.buyItem(pemain, selectedItem.getName(), quantity);
    //     }

    //     System.out.println("\n--- Sesi Belanja Selesai ---");
    //     pemain.getInventory().displayInventory();
    //     System.out.println("Gold terakhir: " + pemain.getGold() + "g");
    //     scanner.close();
    // }
}
