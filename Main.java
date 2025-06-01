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
    private static Tile[][] tiles;
    private static List<Recipe> allRecipes;
    private static Statistics statistics;
    private static boolean gameRunning = true;
    private static final Scanner scanner = new Scanner(System.in);
    private static boolean milestoneReached = false;

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
            case 8: 
                showCheatMenu();
                break;
            default: System.out.println("Pilihan tidak valid.");
        }
    }

    private static void newGame() {
        if (gameClock != null) {
            gameClock.cancel();
        }
        milestoneReached = false;

        System.out.println("\n=====================================================");
        System.out.println("|               Mulai Petualangan Baru              |");
        System.out.println("=====================================================");
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
        shippingBin = new ShippingBin<>();
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
        int mapSize = 32; // Sesuaikan dengan MAP_SIZE di FarmMap.java
        tiles = new Tile[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                tiles[i][j] = new Tile();
            }
        }
        worldState = new WorldState(player, shippingBin, tiles);
        worldMap = new WorldMap();
        house = new House(worldState, worldState.getCurrentTime(), HouseLayoutType.DEFAULT);
        unlockDefaultRecipes();
        Store.initializeInstance(allItems, allRecipes);
        store = Store.getInstance();
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
        System.out.println("Resep yang Diketahui: " + player.getUnlockedRecipes().size() + " resep");
        if (player.getUnlockedRecipes().isEmpty()) {
            System.out.println("  - Belum ada resep yang diketahui.");
        } else {
            for (Recipe recipe : player.getUnlockedRecipes()) {
                System.out.println("  - " + recipe.getRecipeName());
            }
        }
        System.out.println("Lokasi Saat Ini: " + player.getLocation());
        System.out.println("\n--- Inventory ---");
        player.getInventory().displayInventory();
        System.out.println("-----------------");
    }
    
    private static void viewStatistics() {
        int totalDaysPlayed = worldState.getCurrentTime().getDay();
        int totalIncome = statistics.getTotalIncome();
        int totalExpenditure = statistics.getTotalExpenditure();

        int seasonsPassed = ((totalDaysPlayed - 1) / WorldState.DAYS_PER_SEASON) + 1;

        double averageIncome = 0;
        double averageExpenditure = 0;
        if (seasonsPassed > 0) {
            averageIncome = (double) totalIncome / seasonsPassed;
            averageExpenditure = (double) totalExpenditure / seasonsPassed;
        }

        statistics.setAverageSeasonIncome(averageIncome);
        statistics.setAverageSeasonExpenditure(averageExpenditure);

        System.out.println("\n=====================================================");
        System.out.println("|              Statistik Petualanganmu              |");
        System.out.println("=====================================================");
        System.out.println("Total Hari Bermain: " + worldState.getCurrentTime().getDay() + " hari");
        System.out.println("\n--- Keuangan ---");
        System.out.println("Total Pemasukan: " + statistics.getTotalIncome() + "g");
        System.out.println("Total Pengeluaran: " + statistics.getTotalExpenditure() + "g");
        System.out.println("Rata-rata Pendapatan per Musim: " + String.format("%.2f", statistics.getAverageSeasonIncome()) + "g");
        System.out.println("Rata-rata Pengeluaran per Musim: " + String.format("%.2f", statistics.getAverageSeasonExpenditure()) + "g");
        System.out.println("\n--- Prestasi Ladang ---");
        System.out.println("Total Tanaman Dipanen: " + statistics.getCropsHarvested());
        Statistics.FishCaughtDetails fishStats = statistics.getFishCaught();
        System.out.println("Total Ikan Ditangkap: " + fishStats.getTotalFishCaught());
        System.out.println("  - Common: " + fishStats.getCommon() + ", Regular: " + fishStats.getRegular() + ", Legendary: " + fishStats.getLegendary());
        System.out.println("\n--- Sosial (Status NPC) ---");
        for (NPC npc : npcs) {
            System.out.println("-----------------------------------------------------");
            System.out.println("- Nama: " + npc.getName());
            System.out.println("  Status Hubungan: " + npc.getHeartPoint() + "/150 HP (" + npc.getRelationshipStatus() + ")");
            System.out.println("  Frekuensi Interaksi:");
            System.out.println("    - Chatting: " + npc.getChattingCount() + " kali");
            System.out.println("    - Gifting: " + npc.getGiftingCount() + " kali");
            System.out.println("    - Visiting: " + npc.getVisitingCount() + " kali");
        }
        System.out.println("-----------------------------------------------------");
    }

    private static void showActionsMenu() {
        boolean inActionMenu = true;
        while (inActionMenu) {
            if (player != null && worldState != null) { 
                boolean forcedSleep = false;
                String reason = "";

                if (worldState.getCurrentTime().getHours() >= 2 && worldState.getCurrentTime().getHours() < 6) { 
                    forcedSleep = true;
                    reason = "sudah sangat larut malam";
                } else if (player.getEnergy() <= Player.MIN_ENERGY) { 
                    forcedSleep = true;
                    reason = "kehabisan energi";
                }

                if (forcedSleep) {
                    System.out.println("\n!! Kamu pingsan karena " + reason + " !!");
                    new SleepingAction(player, worldState, true).execute(); 
                }
            }
            System.out.println("\n================================================================================");
            synchronized (worldState) {
                System.out.println(" " + worldState.getCurrentStatus() + " | Energy: " + player.getEnergy() + "  ");
                System.out.println(" Gold: " + player.getGold() + "g                                             ");
            }
            System.out.println("================================================================================");
            
            String adjacentObjectOnFarm = null;

            if (player.getLocation().equals(player.getFarmName())) {
                farmMap.displayMap();
                adjacentObjectOnFarm = farmMap.getAdjacentObjectName();
                if (farmMap.canVisit()) {
                    System.out.print("Kamu berada di jalan keluar. Pergi ke Peta Dunia? (y/n): ");
                    if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                        new VisitingAction(player, worldState, "WorldMap", npcs).execute();
                        continue;
                    }
                }
            } else if (player.getLocation().equals("House")) {
                house.displayMap();
                if (house.isPlayerAtExit()) {
                    System.out.print("Kamu berada di pintu keluar. Keluar dari rumah? (y/n): ");
                    if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                        new VisitingAction(player, worldState, player.getFarmName(), npcs).execute();
                        continue;
                    }
                }
            } else if (player.getLocation().equals("WorldMap")) {
                boolean keepInActionMenu = handleWorldMapInteraction();
                if (!keepInActionMenu) {
                    inActionMenu = false; 
                }
                continue; 
            } else {
                 System.out.println("Kamu sedang berada di " + player.getLocation() + ".");
            }

            System.out.println("\n--- Kontrol ---");
            System.out.println("-> Gerak: Langsung ketik W/A/S/D lalu Enter");
            System.out.println("\n--- Aksi Tersedia ---");
            System.out.println("1. Interaksi / Lakukan Aksi Sesuai Lokasi");
            System.out.println("2. Tampilkan Lokasi Saat Ini");
            System.out.println("3. Tampilkan Inventory");
            System.out.println("4. Makan Item dari Inventory");
            System.out.println("5. Kembali ke Menu Utama");

            if (isSubLocation(player.getLocation())) {
                System.out.println("6. Keluar ke Peta Dunia");
            }
            if (adjacentObjectOnFarm != null) {
                System.out.println("7. Interaksi dengan " + adjacentObjectOnFarm);
            }
            System.out.print("Pilih Aksi (angka) atau Gerak (W/A/S/D): ");

            try {
                String input = scanner.nextLine().trim().toLowerCase();
                if (input.isEmpty()) {
                    continue; // Jika input kosong, ulangi loop
                }

                char command = input.charAt(0);

                // Cek apakah perintah adalah untuk bergerak
                if (input.length() == 1 && (command == 'w' || command == 'a' || command == 's' || command == 'd')) {
                    if (player.getLocation().equals(player.getFarmName())) {
                        farmMap.movePlayer(command);
                    } else if (player.getLocation().equals("House")) {
                        house.movePlayer(command);
                    } else {
                        System.out.println("Kamu tidak bisa bergerak bebas di lokasi ini. Gunakan menu.");
                    }
                } else {
                    // Jika bukan perintah gerak, coba proses sebagai angka aksi
                    int choice = Integer.parseInt(input);
                    switch (choice) {
                        case 1:
                            handleInteraction();
                            checkRecipeUnlocks();
                            break;
                        case 2:
                            System.out.println("\nLokasi Saat Ini: " + player.getLocation());
                            break;
                        case 3:
                            System.out.println("\n--- Inventory ---");
                            player.getInventory().displayInventory();
                            System.out.println("-----------------");
                            break;
                        case 4:
                            handleEating();
                            break;
                        case 5:
                            inActionMenu = false;
                            break;
                        case 6:
                            if (isSubLocation(player.getLocation())) {
                                new VisitingAction(player, worldState, "WorldMap", npcs).execute();
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
                }
                checkEndGame();
            } catch (NumberFormatException e) {
                System.out.println("Perintah tidak dikenal. Gunakan W/A/S/D untuk bergerak atau masukkan nomor aksi.");
            }
        }
    }
    
    private static void handleFarmObjectInteraction(String objectName) {
        System.out.println("Berinteraksi dengan " + objectName + "...");
        switch (objectName) {
            case "House":
                new VisitingAction(player, worldState, "House", npcs).execute();
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

    private static void updateHouseLayout() {
        if (player == null || house == null) return;

        boolean hasStove = player.hasOwnedFurniture("Stove");
        boolean hasQueenBed = player.hasOwnedFurniture("Queen Bed");
        boolean hasKingBed = player.hasOwnedFurniture("King Bed");

        HouseLayoutType newLayout = HouseLayoutType.DEFAULT; 

        if (hasStove && hasQueenBed && hasKingBed) newLayout = HouseLayoutType.ALL;
        else if (hasStove && hasQueenBed) newLayout = HouseLayoutType.STOVE_QUEEN;
        else if (hasStove && hasKingBed) newLayout = HouseLayoutType.STOVE_KING;
        else if (hasQueenBed && hasKingBed) newLayout = HouseLayoutType.QUEEN_KING;
        else if (hasStove) newLayout = HouseLayoutType.WITH_STOVE;
        else if (hasQueenBed) newLayout = HouseLayoutType.WITH_QUEEN;
        else if (hasKingBed) newLayout = HouseLayoutType.WITH_KING;
        
        house.setLayout(newLayout);
    }

    private static boolean isSubLocation(String location) {
        if (location == null || location.isEmpty()) return false;
        if (location.equals(player.getFarmName()) || location.equals("House") || location.equals("WorldMap")) {
            return false;
        }
        return true;
    }
    
    private static boolean handleWorldMapInteraction() {
        worldMap.displayMap();
        System.out.println("\n--- Kontrol ---");
        System.out.println("-> Gerak: Langsung ketik W/A/S/D lalu Enter");
        System.out.println("\n--- Aksi Tersedia ---");
        System.out.println("1. Masuk Lokasi Terdekat");
        System.out.println("2. Tampilkan Lokasi Saat Ini");
        System.out.println("3. Kembali ke Menu Utama"); 
        System.out.println("0. Batal (gambar ulang peta)");
        System.out.print("Pilih Aksi (angka) atau Gerak (W/A/S/D): ");

        try {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.isEmpty()) {
                return true; // 
            }

            if (input.length() == 1 && "wasd".contains(input)) {
                char direction = input.charAt(0);
                if (worldMap.movePlayer(direction)) {
                    if (worldState != null && worldState.getCurrentTime() != null) {
                        worldState.getCurrentTime().advanceMinutes(5);
                    }
                    System.out.println("Bergerak di peta...");
                } else {
                    System.out.println("Tidak bisa bergerak ke arah sana.");
                }
            } else {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
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
                                    new VisitingAction(player, worldState, destination, npcs).execute();
                                } else {
                                    System.out.println("Area '" + adjacentArea + "' belum bisa dikunjungi.");
                                }
                            }
                        } else {
                            System.out.println("Tidak ada lokasi khusus di dekatmu.");
                        }
                        break;
                    case 0:
                        return true; 
                    case 2:
                        System.out.println("\nLokasi Saat Ini: " + player.getLocation());
                        break;
                    case 3:
                        return false; 
                    default:
                        System.out.println("Pilihan tidak valid.");
                        break;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Perintah tidak dikenal. Gunakan W/A/S/D atau nomor aksi yang valid.");
        }
        return true; // Secara default, tetap di menu aksi
    }

    private static void unlockDefaultRecipes() {
        for (Recipe recipe : allRecipes) {
            if (recipe.getRecipeName().equals("Baguette") || 
                recipe.getRecipeName().equals("Wine") || 
                recipe.getRecipeName().equals("Pumpkin Pie") ||
                recipe.getRecipeName().equals("Spakbor Salad")) {
                player.unlockRecipe(recipe);
            }
        }
    }

    private static void checkRecipeUnlocks() {
        if (player == null || allRecipes == null) return;

        for (Recipe recipe : allRecipes) {
            // Jika resep sudah dimiliki, lewati
            if (player.hasRecipe(recipe.getRecipeName())) {
                continue;
            }

            // Kondisi tertentu untuk membuka resep
            boolean unlocked = false;
            switch (recipe.getRecipeName()) {
                case "Sashimi":
                    if (statistics.getFishCaught().getTotalFishCaught() >= 10) unlocked = true;
                    break;
                case "Fugu":
                    if (player.getInventory().hasItem("Pufferfish")) unlocked = true;
                    break;
                case "Veggie Soup":
                    if (statistics.getCropsHarvested() > 0) unlocked = true;
                    break;
                case "Fish Stew":
                    if (player.getInventory().hasItem("Hot Pepper")) unlocked = true;
                    break;
                case "The Legends of Spakbor":
                    if (player.getInventory().hasItem("Legend")) unlocked = true;
                    break;
            }

            if (unlocked) {
                player.unlockRecipe(recipe);
            }
        }
    }

    private static void handleCooking() {
        System.out.println("\n--- Resep yang Kamu Ketahui ---");
        if (player.getUnlockedRecipes().isEmpty()) {
            System.out.println("Kamu belum mengetahui resep apapun.");
            return;
        }

        // Tampilkan semua resep yang tersedia
        List<Recipe> unlocked = player.getUnlockedRecipes();
        for (int i = 0; i < unlocked.size(); i++) {
            System.out.println((i + 1) + ". " + unlocked.get(i).getRecipeName());
        }
        System.out.println("0. Batal");

        System.out.print("Pilih resep yang ingin dimasak: ");
        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            if (choice == 0) {
                System.out.println("Batal memasak.");
                return;
            }

            if (choice > 0 && choice <= unlocked.size()) {
                Recipe selectedRecipe = unlocked.get(choice - 1);
                new CookingAction(player, worldState, selectedRecipe).execute();
            } else {
                System.out.println("Pilihan resep tidak valid.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid. Harap masukkan angka.");
            scanner.nextLine(); 
        }
    }

    private static void handleInteraction() {
        String currentLocation = player.getLocation();
        if (currentLocation.equals(player.getFarmName())) {
            int playerX = farmMap.getPlayerX();
            int playerY = farmMap.getPlayerY();
            Tile currentTile = tiles[playerY][playerX]; 
            System.out.println("Aksi di Ladang (pada tile " + farmMap.getPlayerX() + "," + farmMap.getPlayerY() + "):");
            System.out.println("1. Cangkul Tanah | 2. Siram | 3. Tanam | 4. Panen | 5. Recover Tanah");
            System.out.print("Pilihan: ");
            int farmAction = scanner.nextInt();
            scanner.nextLine();
            switch (farmAction) {
                case 1: new TillingAction(player, worldState, farmMap, currentTile).execute(); break;
                case 2: new WateringAction(player, worldState, currentTile).execute(); break;
                case 3: new PlantingAction(player, worldState, farmMap, currentTile).execute(); break;
                case 4: new HarvestingAction(player, worldState, farmMap, currentTile, allItems).execute(); break;
                case 5: new RecoverLandAction(player, worldState, farmMap, currentTile).execute(); break;
                default: System.out.println("Aksi tidak valid.");
            }
        } else if (currentLocation.equals("House")) {
            Furniture adjacent = house.getAdjacentFurniture();
            if (adjacent instanceof Stove) {
                handleCooking();
            } else {
                house.use(player);
            }
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
        System.out.println("4. Lamar (butuh Proposal Ring & 150 HP)");
        System.out.println("5. Menikah (harus sudah tunangan)");
        System.out.print("Pilihan: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        NPC emily = findNpcByName("Emily");
        switch (choice) {
            case 1:
                store.displayItemsForSale(player);
                List<Item> itemsForSale = store.getItemsForSale();

                try {
                    System.out.print("Masukkan nomor item yang ingin dibeli (atau 0 untuk batal): ");
                    int itemNumber = scanner.nextInt();
                    scanner.nextLine(); 

                    if (itemNumber == 0) {
                        System.out.println("Pembelian dibatalkan.");
                        break;
                    }

                    if (itemNumber < 1 || itemNumber > itemsForSale.size()) {
                        System.out.println("Nomor item tidak valid.");
                        break;
                    }

                    Item selectedItem = itemsForSale.get(itemNumber - 1);

                    System.out.print("Masukkan jumlah untuk '" + selectedItem.getName() + "': ");
                    int qty = scanner.nextInt();
                    scanner.nextLine(); 

                    int totalCost = store.buyItem(player, selectedItem.getName(), qty);

                    if (totalCost > 0) {
                        statistics.setTotalExpenditure(statistics.getTotalExpenditure() + totalCost);

                        if (selectedItem instanceof Furniture) {
                            player.addOwnedFurniture(selectedItem.getName());
                            System.out.println("--> " + selectedItem.getName() + " telah berhasil dipasang di rumahmu!");
                            updateHouseLayout();
                        }
                    }
                    checkRecipeUnlocks(); 
                } catch (InputMismatchException e) {
                    System.out.println("Input tidak valid. Harap masukkan nomor.");
                    scanner.nextLine(); 
                }
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
            case 4:
                Item ring = Item.findItemByName(allItems, "Proposal Ring");
                new ProposingAction(player, worldState, emily, ring).execute();
                break;
            case 5:
                Item ringForMarriage = Item.findItemByName(allItems, "Proposal Ring");
                new MarryingAction(player, worldState, emily, ringForMarriage).execute();
                break;
            default: System.out.println("Pilihan tidak valid.");
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

    // Tambahkan metode ini di dalam kelas Main.java
private static void showCheatMenu() {
    if (player == null || worldState == null) {
        System.out.println("Mulai 'New Game' terlebih dahulu untuk menggunakan cheat.");
        return;
    }

    boolean inCheatMenu = true;
        while (inCheatMenu) {
            System.out.println("\n===== MENU CHEATS (DEV) =====");
            System.out.println("Current: " + worldState.getCurrentStatus());
            System.out.println("Player Gold: " + player.getGold() + "g | Energy: " + player.getEnergy());
            System.out.println("-----------------------------");
            System.out.println("1. Set Weather");
            System.out.println("2. Set Season");
            System.out.println("3. Tambah Gold");
            System.out.println("4. Set Energi");
            System.out.println("5. Majukan Waktu (per jam)");
            System.out.println("6. Lanjut ke Hari Berikutnya");
            System.out.println("7. Buka Semua Resep");
            System.out.println("0. Kembali ke Menu Utama");
            System.out.print("Pilih cheat: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        System.out.print("Set Weather ke (1: Sunny, 2: Rainy): ");
                        int weatherChoice = scanner.nextInt();
                        scanner.nextLine();
                        if (weatherChoice == 1) worldState.manuallySetWeather(Weather.SUNNY);
                        else if (weatherChoice == 2) worldState.manuallySetWeather(Weather.RAINY);
                        else System.out.println("Pilihan weather tidak valid.");
                        break;
                    case 2:
                        System.out.print("Set Season ke (1: Spring, 2: Summer, 3: Fall, 4: Winter): ");
                        int seasonChoice = scanner.nextInt();
                        scanner.nextLine();
                        Season newSeason = null;
                        if (seasonChoice == 1) newSeason = Season.SPRING;
                        else if (seasonChoice == 2) newSeason = Season.SUMMER;
                        else if (seasonChoice == 3) newSeason = Season.FALL;
                        else if (seasonChoice == 4) newSeason = Season.WINTER;
                        
                        if (newSeason != null) worldState.manuallySetSeason(newSeason);
                        else System.out.println("Pilihan season tidak valid.");
                        break;
                    case 3:
                        System.out.print("Jumlah Gold yang ingin ditambahkan: ");
                        int goldToAdd = scanner.nextInt();
                        scanner.nextLine();
                        player.addGold(goldToAdd);
                        System.out.println("Gold berhasil ditambahkan. Gold sekarang: " + player.getGold() + "g");
                        break;
                    case 4:
                        System.out.print("Set Energi ke (0-" + Player.MAX_ENERGY + "): ");
                        int energyToSet = scanner.nextInt();
                        scanner.nextLine();
                        player.setEnergy(energyToSet);
                        System.out.println("Energi berhasil diubah. Energi sekarang: " + player.getEnergy());
                        break;
                    case 5:
                        System.out.print("Majukan waktu sebanyak (jam): ");
                        int hoursToAdvance = scanner.nextInt();
                        scanner.nextLine();
                        if (hoursToAdvance > 0) {
                            int dayBefore = worldState.getCurrentTime().getDay();
                            worldState.getCurrentTime().advanceMinutes(hoursToAdvance * 60);
                            System.out.println("Waktu dimajukan " + hoursToAdvance + " jam.");
                            if (worldState.getCurrentTime().getDay() > dayBefore) {
                                System.out.println("Memproses hari baru karena waktu melewati tengah malam...");
                                worldState.handleNewDay();
                            }
                        } else {
                            System.out.println("Jumlah jam harus positif.");
                        }
                        break;
                    case 6:
                        System.out.println("Melanjutkan ke hari berikutnya...");
                        worldState.getCurrentTime().nextDay(); 
                        worldState.handleNewDay(); 
                        System.out.println("Selamat datang di hari yang baru!");
                        break;
                    case 7:
                        if (allRecipes != null && !allRecipes.isEmpty()) {
                            for (Recipe recipe : allRecipes) {
                                player.unlockRecipe(recipe);
                            }
                            System.out.println("Semua resep berhasil dibuka!");
                        } else {
                            System.out.println("Tidak ada resep untuk dibuka atau daftar resep kosong.");
                        }
                        break;
                    case 0:
                        inCheatMenu = false;
                        break;
                    default:
                        System.out.println("Pilihan cheat tidak valid.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Harap masukkan angka.");
                scanner.nextLine(); 
            }
        }
    }

    private static void checkEndGame() {
        if (!milestoneReached && player != null && (player.getGold() >= 17209 && (player.getPartner() != null && player.getPartner().getRelationshipStatus() == RelationshipStatus.SPOUSE))) {
            System.out.println("\n\n*****************************************************");
            System.out.println("!!!      SELAMAT, KAMU MENCAPAI MILESTONE!        !!!");
            System.out.println("*****************************************************");
            viewStatistics();
            System.out.println("\nPermainan akan terus berlanjut. Kamu bisa terus bermain!");
            System.out.println("(Tekan Enter untuk melanjutkan...)");
            scanner.nextLine();

            milestoneReached = true;
        }
    }
}