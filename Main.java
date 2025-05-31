import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    private static Player player;
    private static WorldState worldState;
    private static FarmMap farmMap;
    private static House house;
    private static WorldMap worldMap;
    private static ShippingBin<Item> shippingBin;
    private static Store store;
    private static List<NPC> npcs;
    private static List<Item> allItems;
    private static List<Recipe> allRecipes;
    private static Statistics statistics;
    private static boolean gameRunning = true;
    private static final Scanner scanner = new Scanner(System.in);

    private static Timer gameClock;

    public static void main(String[] args) {
        MenuGame.printSpakborHillsArt();
        showMainMenu();
    }

    private static void showMainMenu() {
        while (gameRunning) {
            if (player == null) {
                MenuGame.showMenuUnstarted();
            } else {
                MenuGame.showMenuStarted();
            }

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                if (player == null) {
                    handleMenuUnstarted(choice);
                } else {
                    handleMenuStarted(choice);
                }
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Harap masukkan angka.");
                scanner.nextLine();
            }
        }
        System.out.println("Terima kasih telah bermain Spakbor Hills! Sampai jumpa lagi!");
        
        if (gameClock != null) {
            gameClock.cancel();
        }
        scanner.close();
    }

    private static void handleMenuUnstarted(int choice) {
        switch (choice) {
            case 1: newGame(); break;
            case 2: showHelp(); break;
            case 3: showCredits(); break;
            case 4: 
                gameRunning = false;
                break;
            default: System.out.println("Pilihan tidak valid. Silakan coba lagi.");
        }
    }

    private static void handleMenuStarted(int choice) {
        switch (choice) {
            case 1:
                System.out.println("Memulai permainan baru akan menghapus progres saat ini. Lanjutkan? (y/n)");
                if (scanner.nextLine().trim().equalsIgnoreCase("y")) newGame();
                break;
            case 2: showHelp(); break;
            case 3: viewPlayerInfo(); break;
            case 4: viewStatistics(); break;
            case 5: showActionsMenu(); break;
            case 6: showCredits(); break;
            case 7: 
                gameRunning = false;
                break;
            default: System.out.println("Pilihan tidak valid.");
        }
    }

    private static void newGame() {
        if (gameClock != null) {
            gameClock.cancel();
        }

        System.out.println("\n=====================================================");
        System.out.println("|               Mulai Petualangan Baru              |");
        System.out.println("=====================================================");
        worldState = new WorldState();
        System.out.print("Masukkan Nama Karakter: ");
        String playerName = scanner.nextLine();
        System.out.print("Pilih Gender (Pria/Wanita): ");
        String playerGender = scanner.nextLine();
        while (!playerGender.equals("Pria") && !playerGender.equals("Wanita")) {
            System.out.print("Pilihan tidak valid. Coba lagi (Pria/Wanita): ");
            playerGender = scanner.nextLine();
        }
        System.out.print("Masukkan Nama Ladangmu: ");
        String farmName = scanner.nextLine() + " Farm";
        player = new Player(playerName, playerGender, farmName);
        DefaultItemLoader itemLoader = new DefaultItemLoader();
        allItems = itemLoader.loadInitialItems();
        DefaultRecipeLoader recipeLoader = new DefaultRecipeLoader();
        allRecipes = recipeLoader.loadAllRecipes();
        player.getInventory().addItem(Item.findItemByName(allItems, "Parsnip Seeds"), 15);
        player.getInventory().addItem(Item.findItemByName(allItems, "Hoe"), 1);
        player.getInventory().addItem(Item.findItemByName(allItems, "Watering Can"), 1);
        player.getInventory().addItem(Item.findItemByName(allItems, "Pickaxe"), 1);
        player.getInventory().addItem(Item.findItemByName(allItems, "Fishing Rod"), 1);
        farmMap = new FarmMap();
        worldMap = new WorldMap();
        house = new House(worldState, worldState.getCurrentTime(), HouseLayoutType.DEFAULT);
        Store.initializeInstance(allItems);
        store = Store.getInstance();
        shippingBin = new ShippingBin<>();
        npcs = new ArrayList<>();
        npcs.add(NPCFactory.createMayorTadi(allItems));
        npcs.add(NPCFactory.createCaroline(allItems));
        npcs.add(NPCFactory.createPerry(allItems));
        npcs.add(NPCFactory.createDasco(allItems));
        npcs.add(NPCFactory.createEmily(allItems));
        npcs.add(NPCFactory.createAbigail(allItems));
        statistics = new Statistics();
        statistics.setTotalDaysPlayed(1);
        player.setLocation(player.getFarmName());
        System.out.println("\nSelamat datang di Spakbor Hills, " + player.getName() + "!");
        System.out.println("Petualanganmu di " + player.getFarmName() + " dimulai!");
        
        startGameClock();
    }

    private static void startGameClock() {
        gameClock = new Timer(true); 
        TimerTask clockTask = new TimerTask() {
            @Override
            public void run() {
                if (worldState != null && gameRunning) {
                    synchronized (worldState) { 
                        worldState.update(1000); 
                    }
                } else {
                    gameClock.cancel(); 
                }
            }
        };
        gameClock.scheduleAtFixedRate(clockTask, 1000, 1000);
    }

    private static void showHelp() {
        System.out.println("\n=====================================================");
        System.out.println("|                 Bantuan Spakbor Hills             |");
        System.out.println("=====================================================");
        System.out.println("Spakbor Hills adalah game simulasi pertanian tempat kamu bertani,");
        System.out.println("memancing, memasak, dan menjalin hubungan dengan warga desa.");
        System.out.println("\nTujuan utama (milestone):");
        System.out.println("1. Memiliki gold sebesar 17,209g.");
        System.out.println("2. Menikah dengan salah satu NPC.");
        System.out.println("\nGunakan menu 'Actions' untuk berinteraksi dengan dunia game.");
        System.out.println("Perhatikan energimu! Jika habis, kamu bisa pingsan.");
        System.out.println("Selamat bermain!");
    }

    private static void showCredits() {
        System.out.println("\n=====================================================");
        System.out.println("|                     Credits                     |");
        System.out.println("=====================================================");
        System.out.println("Game 'Spakbor Hills' ini dibuat oleh:");
        System.out.println("IF2010 - Kelompok G05");
        System.out.println("18221339 - Andi Syaichul Mubaraq");
        System.out.println("18223117 - Khairunnisa Azizah");
        System.out.println("18223132 - Muhammad Rafly Fauzan");
        System.out.println("18223130 - M Rabbani K A");
        System.out.println("18223136 - Geraldo Linggom Samuel T.");
        System.out.println("Terima kasih telah bermain!");
    }

    private static void viewPlayerInfo() {
        System.out.println("\n=====================================================");
        System.out.println("|                   Informasi Pemain                |");
        System.out.println("=====================================================");
        System.out.println("Nama: " + player.getName() + " (" + player.getGender() + ")");
        System.out.println("Ladang: " + player.getFarmName());
        System.out.println("Gold: " + player.getGold() + "g");
        System.out.println("Energi: " + player.getEnergy() + "/" + Player.MAX_ENERGY);
        String partnerInfo = (player.getPartner() == null) ? "Single" : player.getPartner().getName() + " (" + player.getPartner().getRelationshipStatus() + ")";
        System.out.println("Partner: " + partnerInfo);
        System.out.println("Lokasi Saat Ini: " + player.getLocation());
        System.out.println("\n--- Inventory ---");
        player.getInventory().displayInventory();
        System.out.println("-----------------");
    }
    
    private static void viewStatistics() {
        System.out.println("\n=====================================================");
        System.out.println("|              Statistik Petualanganmu              |");
        System.out.println("=====================================================");
        System.out.println("Total Hari Bermain: " + worldState.getCurrentTime().getDay() + " hari");
        System.out.println("\n--- Keuangan ---");
        System.out.println("Total Pemasukan: " + statistics.getTotalIncome() + "g");
        System.out.println("Total Pengeluaran: " + statistics.getTotalExpenditure() + "g");
        System.out.println("\n--- Prestasi Ladang ---");
        System.out.println("Total Tanaman Dipanen: " + statistics.getCropsHarvested());
        Statistics.FishCaughtDetails fishStats = statistics.getFishCaught();
        System.out.println("Total Ikan Ditangkap: " + fishStats.getTotalFishCaught());
        System.out.println("  - Common: " + fishStats.getCommon() + ", Regular: " + fishStats.getRegular() + ", Legendary: " + fishStats.getLegendary());
        System.out.println("\n--- Sosial (Status NPC) ---");
        for (NPC npc : npcs) {
            System.out.println("- " + npc.getName() + ": " + npc.getHeartPoint() + "/150 HP (" + npc.getRelationshipStatus() + ")");
        }
    }

    private static void showActionsMenu() {
        boolean inActionMenu = true;
        while (inActionMenu) {
            System.out.println("\n==================================================================");
            // DIPERBARUI: Menggunakan synchronized untuk keamanan thread
            synchronized (worldState) {
                System.out.println("| " + player.getLocation() + " | " + worldState.getCurrentStatus());
            }
            System.out.println("==================================================================");
            
            String adjacentObjectOnFarm = null;

            if (player.getLocation().equals(player.getFarmName())) {
                farmMap.displayMap();
                adjacentObjectOnFarm = farmMap.getAdjacentObjectName();
                if (farmMap.canVisit()) {
                    System.out.print("Kamu berada di jalan keluar. Pergi ke Peta Dunia? (y/n): ");
                    if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                        new VisitingAction(player, worldState, "World Map").execute();
                        continue;
                    }
                }
            } else if (player.getLocation().equals("House")) {
                house.displayMap();
                if (house.isPlayerAtExit()) {
                    System.out.print("Kamu berada di pintu keluar. Keluar dari rumah? (y/n): ");
                    if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                        new VisitingAction(player, worldState, player.getFarmName()).execute();
                        continue;
                    }
                }
            } else if (player.getLocation().equals("World Map")) {
                handleWorldMapInteraction();
                continue;
            } else {
                 System.out.println("Kamu sedang berada di " + player.getLocation() + ".");
            }

            System.out.println("\n--- Aksi Tersedia ---");
            System.out.println("1.  Gerak (W/A/S/D) (Hanya di Ladang/Rumah)");
            System.out.println("2.  Interaksi / Lakukan Aksi Sesuai Lokasi");
            System.out.println("3.  Makan Item dari Inventory");
            System.out.println("4.  Tidur (hanya di rumah)");
            System.out.println("5.  Kembali ke Menu Utama");

            if (isSubLocation(player.getLocation())) {
                System.out.println("6. Keluar ke Peta Dunia");
            }
            if (adjacentObjectOnFarm != null) {
                System.out.println("7. Interaksi dengan " + adjacentObjectOnFarm);
            }
            System.out.print("Pilih aksi: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Masukkan arah (W/A/S/D): ");
                        char direction = scanner.nextLine().trim().toLowerCase().charAt(0);
                        if (player.getLocation().equals(player.getFarmName())) {
                            farmMap.movePlayer(direction);
                        } else if (player.getLocation().equals("House")) {
                            house.movePlayer(direction);
                        } else {
                            System.out.println("Kamu tidak bisa bergerak secara spesifik di lokasi ini.");
                        }
                        break;
                    case 2:
                        handleInteraction();
                        break;
                    case 3:
                        handleEating();
                        break;
                    case 4:
                        new SleepingAction(player, worldState, false).execute();
                        break;
                    case 5:
                        inActionMenu = false;
                        break;
                    case 6:
                        if (isSubLocation(player.getLocation())) {
                            new VisitingAction(player, worldState, "World Map").execute();
                        } else {
                            System.out.println("Pilihan tidak valid.");
                        }
                        break;
                    case 7:
                        if (adjacentObjectOnFarm != null) {
                            handleFarmObjectInteraction(adjacentObjectOnFarm);
                        } else {
                            System.out.println("Pilihan tidak valid.");
                        }
                        break;
                    default:
                        System.out.println("Pilihan tidak valid.");
                }
                checkEndGame();
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Masukkan angka.");
                scanner.nextLine();
            }
        }
    }
    
    private static void handleFarmObjectInteraction(String objectName) {
        System.out.println("Berinteraksi dengan " + objectName + "...");
        switch (objectName) {
            case "House":
                new VisitingAction(player, worldState, "House").execute();
                break;
            case "Pond":
                String originalLocation = player.getLocation();
                player.setLocation("Pond"); 
                new FishingAction(player, worldState, allItems).execute();
                player.setLocation(originalLocation);
                break;
            case "Shipping Bin":
                handleSelling();
                break;
            default:
                System.out.println("Tidak ada interaksi yang terdefinisi untuk " + objectName);
                break;
        }
    }

    // ... (method-method lain dari isSubLocation hingga checkEndGame tidak berubah) ...
    private static boolean isSubLocation(String location) {
        if (location == null || location.isEmpty()) return false;
        if (location.equals(player.getFarmName()) || location.equals("House") || location.equals("World Map")) {
            return false;
        }
        return true;
    }
    
    private static void handleWorldMapInteraction() {
        worldMap.displayMap();
        System.out.println("\n--- Aksi Peta Dunia ---");
        System.out.println("1. Gerak (W/A/S/D)");
        System.out.println("2. Masuk Lokasi Terdekat");
        System.out.println("0. Batal (Tetap di Peta Dunia)");
        System.out.print("Pilihan: ");
    
        int choice;
        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid. Masukkan angka.");
            scanner.nextLine();
            return;
        }
    
        switch (choice) {
            case 1:
                System.out.print("Masukkan arah (W/A/S/D): ");
                char direction = scanner.nextLine().trim().toLowerCase().charAt(0);
                if (worldMap.movePlayer(direction)) {
                    player.setEnergy(player.getEnergy() - 1);
                    worldState.getCurrentTime().advanceMinutes(5);
                    System.out.println("Bergerak di peta... (-1 Energi, +5 Menit)");
                } else {
                    System.out.println("Tidak bisa bergerak ke arah sana.");
                }
                break;
            case 2:
                String adjacentArea = worldMap.getAdjacentSpecialArea();
                if (adjacentArea != null) {
                    System.out.println("Kamu berada di dekat " + adjacentArea + ". Masuk? (y/n)");
                    if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                        String destination = "";
                        switch(adjacentArea) {
                            case "FarmMap":
                                destination = player.getFarmName();
                                worldMap.movePlayerToFarmMap();
                                break;
                            case "Emily's Store":
                                destination = "Store";
                                break;
                            case "Forest River":
                            case "Mountain Lake":
                            case "Ocean":
                                destination = adjacentArea;
                                break;
                            default:
                                if(adjacentArea.startsWith("Rumah ")) {
                                    destination = adjacentArea.replace("Rumah ", "") + " House";
                                }
                                break;
                        }
                        
                        if (!destination.isEmpty()) {
                            new VisitingAction(player, worldState, destination).execute();
                        } else {
                            System.out.println("Area '" + adjacentArea + "' belum bisa dikunjungi.");
                        }
                    }
                } else {
                    System.out.println("Tidak ada lokasi khusus di dekatmu.");
                }
                break;
            case 0: break;
            default: System.out.println("Pilihan tidak valid."); break;
        }
    }

    private static void handleInteraction() {
        String currentLocation = player.getLocation();
        if (currentLocation.equals(player.getFarmName())) {
            Tile currentTile = new Tile();
            System.out.println("Aksi di Ladang (pada tile " + farmMap.getPlayerX() + "," + farmMap.getPlayerY() + "):");
            System.out.println("1. Cangkul Tanah | 2. Siram | 3. Tanam | 4. Panen");
            System.out.print("Pilihan: ");
            int farmAction = scanner.nextInt();
            scanner.nextLine();
            switch (farmAction) {
                case 1: new TillingAction(player, worldState, farmMap, currentTile).execute(); break;
                case 2: new WateringAction(player, worldState, currentTile).execute(); break;
                case 3: new PlantingAction(player, worldState, farmMap, currentTile).execute(); break;
                case 4: new HarvestingAction(player, worldState, farmMap, currentTile, allItems).execute(); break;
                default: System.out.println("Aksi tidak valid.");
            }
        } else if (currentLocation.equals("House")) {
            house.use(player);
        } else if (currentLocation.equals("Store")) {
            handleStoreInteraction();
        } else if (currentLocation.endsWith("House")) {
            String npcName = currentLocation.replace(" House", "");
            NPC targetNpc = findNpcByName(npcName);
            if (targetNpc != null) handleNpcInteraction(targetNpc);
        } else if (isFishingLocation(currentLocation)) {
            new FishingAction(player, worldState, allItems).execute();
        } else {
            System.out.println("Tidak ada interaksi khusus yang bisa dilakukan di sini.");
        }
    }
    
    private static void handleEating() {
        System.out.println("\n--- Pilih Item untuk Dimakan ---");
        player.getInventory().displayInventory();
        System.out.print("Masukkan nama item (atau 'batal'): ");
        String itemName = scanner.nextLine().trim();
        if(itemName.equalsIgnoreCase("batal")) return;
        Item itemToEat = player.getInventory().getItemByName(itemName);
        new EatingAction(player, worldState, itemToEat).execute();
    }
    
    private static void handleSelling() {
        new SellingAction(player, worldState, shippingBin).execute();
    }

    private static void handleStoreInteraction() {
        System.out.println("\n--- Toko & Rumah Emily ---");
        System.out.println("1. Beli Item");
        System.out.println("2. Ngobrol dengan Emily");
        System.out.println("3. Beri Hadiah ke Emily");
        System.out.print("Pilihan: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        NPC emily = findNpcByName("Emily");
        switch (choice) {
            case 1:
                store.displayItemsForSale(player);
                System.out.print("Masukkan nama item yang ingin dibeli: ");
                String itemName = scanner.nextLine().trim();
                System.out.print("Masukkan jumlah: ");
                int qty = scanner.nextInt();
                scanner.nextLine();
                store.buyItem(player, itemName, qty);
                statistics.setTotalExpenditure(statistics.getTotalExpenditure() + (store.findItemByName(itemName).getBuyPrice() * qty));
                break;
            case 2: new ChattingAction(player, worldState, emily).execute(); break;
            case 3:
                System.out.println("Pilih item untuk diberikan dari inventory:");
                player.getInventory().displayInventory();
                System.out.print("Masukkan nama item: ");
                String giftName = scanner.nextLine().trim();
                Item gift = player.getInventory().getItemByName(giftName);
                new GiftingAction(player, worldState, emily, gift).execute();
                break;
        }
    }

    private static void handleNpcInteraction(NPC npc) {
        System.out.println("\n--- Interaksi dengan " + npc.getName() + " ---");
        System.out.println("1. Ngobrol");
        System.out.println("2. Beri Hadiah");
        System.out.println("3. Lamar (butuh Proposal Ring & 150 HP)");
        System.out.println("4. Menikah (harus sudah tunangan)");
        System.out.print("Pilihan: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch(choice) {
            case 1: new ChattingAction(player, worldState, npc).execute(); break;
            case 2:
                System.out.println("Pilih item untuk diberikan:");
                player.getInventory().displayInventory();
                System.out.print("Nama item: ");
                String giftName = scanner.nextLine().trim();
                Item gift = player.getInventory().getItemByName(giftName);
                new GiftingAction(player, worldState, npc, gift).execute();
                break;
            case 3:
                Item ring = Item.findItemByName(allItems, "Proposal Ring");
                new ProposingAction(player, worldState, npc, ring).execute();
                break;
            case 4:
                Item ringForMarriage = Item.findItemByName(allItems, "Proposal Ring");
                new MarryingAction(player, worldState, npc, ringForMarriage).execute();
                break;
            default: System.out.println("Pilihan tidak valid.");
        }
    }

    private static NPC findNpcByName(String name) {
        for (NPC npc : npcs) {
            if (npc.getName().equalsIgnoreCase(name)) return npc;
        }
        return null;
    }

    private static boolean isFishingLocation(String location) {
        return location.equals("Pond") || location.equals("Mountain Lake") || location.equals("Forest River") || location.equals("Ocean");
    }

    private static void checkEndGame() {
        if (player != null && (player.getGold() >= 17209 || (player.getPartner() != null && player.getPartner().getRelationshipStatus() == RelationshipStatus.SPOUSE))) {
            System.out.println("\n\n*****************************************************");
            System.out.println("!!!   SELAMAT, KAMU MENCAPAI MILESTONE!   !!!");
            System.out.println("*****************************************************");
            viewStatistics();
            System.out.println("\nPermainan akan terus berlanjut. Kamu bisa terus bermain!");
            System.out.println("(Tekan Enter untuk melanjutkan...)");
            scanner.nextLine();
        }
    }
}