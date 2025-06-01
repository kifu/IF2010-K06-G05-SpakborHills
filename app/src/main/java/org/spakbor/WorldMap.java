package org.spakbor;

import java.util.*;

public class WorldMap {
    private static final int MAP_WIDTH = 15;
    private static final int MAP_HEIGHT = 15;

    private static final char EMPTY = '.';
    private static final char FARMMAP = 'F';
    private static final char FOREST_RIVER = 'R';
    private static final char MOUNTAIN_LAKE = 'M';
    private static final char OCEAN = 'O';
    private static final char STORE = 'S'; // Emily's store is 'S'
    private static final char[] NPC_HOUSE_SYMBOLS = {'T', 'C', 'P', 'D', 'A'};
    private static final char PLAYER = 'P';

    private char[][] map;
    private int playerX, playerY;

    // Lokasi area dan rumah NPC
    Map<String, int[]> locations;
    Map<String, Character> npcToSymbol;
    Map<Character, String> symbolToNPC;

    private static final String[] NPC_NAMES = {
        "Mayor Tadi", "Caroline", "Perry", "Dasco", "Abigail"
    };

    public WorldMap() {
        map = new char[MAP_HEIGHT][MAP_WIDTH];
        locations = new HashMap<>();
        npcToSymbol = new HashMap<>();
        symbolToNPC = new HashMap<>();
        initializeMap();
        placeObjects();
        int[] farmMap = locations.get("FarmMap");
        playerX = farmMap[0];
        playerY = farmMap[1];
    }

    private void initializeMap() {
        for (int i = 0; i < MAP_HEIGHT; i++) {
            Arrays.fill(map[i], EMPTY);
        }
    }

    private void placeObjects() {
        // FarmMap (tengah map)
        locations.put("FarmMap", new int[]{MAP_WIDTH / 2, MAP_HEIGHT / 2});
        map[MAP_HEIGHT / 2][MAP_WIDTH / 2] = FARMMAP;

        // Fishing areas
        locations.put("ForestRiver", new int[]{2, 2});
        map[2][2] = FOREST_RIVER;
        locations.put("MountainLake", new int[]{MAP_WIDTH - 3, 2});
        map[2][MAP_WIDTH - 3] = MOUNTAIN_LAKE;
        locations.put("Ocean", new int[]{2, MAP_HEIGHT - 3});
        map[MAP_HEIGHT - 3][2] = OCEAN;

        // Emily's store
        locations.put("Store", new int[]{MAP_WIDTH / 2 + 4, MAP_HEIGHT / 2});
        map[MAP_HEIGHT / 2][MAP_WIDTH / 2 + 4] = STORE;

        // NPC Houses
        int[][] npcHouseLocations = {
            {4, 4}, // Mayor Tadi (T)
            {MAP_WIDTH - 5, 4}, // Caroline (C)
            {4, MAP_HEIGHT - 5}, // Perry (P)
            {MAP_WIDTH - 5, MAP_HEIGHT - 5}, // Dasco (D)
            {2, 7} // Abigail (A)
        };
        for (int i = 0; i < NPC_NAMES.length; i++) {
            String npc = NPC_NAMES[i];
            int[] loc = npcHouseLocations[i];
            char symbol = NPC_HOUSE_SYMBOLS[i];
            locations.put("House_" + npc, loc);
            npcToSymbol.put(npc, symbol);
            symbolToNPC.put(symbol, npc);
            map[loc[1]][loc[0]] = symbol;
        }
    }

    // --- Tambahan: Cek player di sekitar area
    public boolean isPlayerAdjacentToArea(String areaName) {
        // Normalisasi nama area untuk rumah NPC
        if (areaName.startsWith("Rumah ")) areaName = areaName.substring(6);

        // Cari lokasi area dari locations Map
        int[] areaLoc = null;
        if (locations.containsKey(areaName)) {
            areaLoc = locations.get(areaName);
        } else if (locations.containsKey("House_" + areaName)) {
            areaLoc = locations.get("House_" + areaName);
        }
        if (areaLoc == null) return false;

        // Cek 8 arah (termasuk diagonal)
        int[] dx = {0, 0, -1, 1, -1, -1, 1, 1};
        int[] dy = {-1, 1, 0, 0, -1, 1, -1, 1};
        for (int i = 0; i < 8; i++) {
            int adjX = areaLoc[0] + dx[i];
            int adjY = areaLoc[1] + dy[i];
            if (playerX == adjX && playerY == adjY) {
                return true;
            }
        }
        return false;
    }
    public boolean enterWorldMap(Scanner scanner) {
        boolean running = true;
        while (running) {
            displayMap();
            String areaToEnter = getAdjacentSpecialArea();
            if (areaToEnter != null) {
                System.out.println("Kamu berada di dekat " + areaToEnter + ". Masuk ke sini? (y/n)");
                String answer = scanner.nextLine().trim();
                if (answer.equalsIgnoreCase("y")) {
                    // Jika masuk ke "FarmMap", tanya apakah ingin kembali ke FarmMap
                    if (areaToEnter.equals("FarmMap")) {
                        System.out.println("Kamu kembali ke FarmMap. Ingin kembali ke FarmMap? (y/n)");
                        String v = scanner.nextLine().trim();
                        if (v.equalsIgnoreCase("y")) {
                            movePlayerToFarmMap();
                            return false; // Sinyal ke main loop untuk masuk kembali ke FarmMap
                        } else {
                            enterArea(areaToEnter, scanner); // tetap di WorldMap
                        }
                    } else {
                        enterArea(areaToEnter, scanner);
                    }
                    continue;
                }
            }
            System.out.print("\nGerak: [W/A/S/D], Q: Keluar Game\nPilih: ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.isEmpty()) continue;
            char cmd = input.charAt(0);

            switch (cmd) {
                case 'w': case 'a': case 's': case 'd':
                    if (!movePlayer(cmd)) {
                        System.out.println("Tidak bisa bergerak ke arah tersebut!");
                    }
                    break;
                case 'q':
                    System.out.println("Keluar dari Game...");
                    System.exit(0);
                default:
                    System.out.println("Perintah tidak dikenal!");
            }
        }
        return true;
    }

    // Cek 8 arah (termasuk diagonal)
    public String getAdjacentSpecialArea() {
        int[] dx = {0, 0, -1, 1, -1, -1, 1, 1};
        int[] dy = {-1, 1, 0, 0, -1, 1, -1, 1};
        for (int i = 0; i < 8; i++) {
            int adjX = playerX + dx[i];
            int adjY = playerY + dy[i];
            if (adjX < 0 || adjX >= MAP_WIDTH || adjY < 0 || adjY >= MAP_HEIGHT) continue;
            char tile = map[adjY][adjX];
            if (tile == FOREST_RIVER) return "Forest River";
            if (tile == MOUNTAIN_LAKE) return "Mountain Lake";
            if (tile == OCEAN) return "Ocean";
            if (tile == STORE) return "Emily's Store";
            if (tile == FARMMAP) return "FarmMap";
            if (symbolToNPC.containsKey(tile)) return "Rumah " + symbolToNPC.get(tile);
        }
        return null;
    }

    private void enterArea(String area, Scanner scanner) {
        System.out.println("Masuk ke " + area + "...");
        if (area.equals("Emily's Store")) {
            System.out.println("Ini Store milik Emily! Kamu bisa belanja, ngobrol, atau memberi hadiah ke Emily.");
        } else if (area.equals("Forest River")) {
            System.out.println("Kamu berada di Forest River! Bisa memancing di sini.");
        } else if (area.equals("Mountain Lake")) {
            System.out.println("Kamu berada di Mountain Lake! Bisa memancing ikan langka.");
        } else if (area.equals("Ocean")) {
            System.out.println("Kamu sampai di Ocean! Banyak ikan dan pemandangan indah.");
        } else if (area.equals("FarmMap")) {
            System.out.println("Kamu kembali ke FarmMap.");
        } else if (area.startsWith("Rumah ")) {
            String npc = area.substring(6);
            System.out.println("Ini rumah " + npc + "! Kamu bisa ngobrol, memberi hadiah, atau bahkan menikahi " + npc + ".");
        } else {
            System.out.println("Belum ada aktivitas khusus di area ini.");
        }
        System.out.println("(Tekan Enter untuk kembali ke World Map)");
        scanner.nextLine();
    }

    public void displayMap() {
        System.out.print("\033[2J\033[H");
        for (int i = 0; i < MAP_HEIGHT; i++) {
            for (int j = 0; j < MAP_WIDTH; j++) {
                if (i == playerY && j == playerX) {
                    System.out.print(PLAYER + " ");
                } else {
                    System.out.print(map[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println("\nLegenda: F = FarmMap, R = Forest River, M = Mountain Lake, O = Ocean, S = Store Emily");
        System.out.println("T = Rumah Mayor Tadi, C = Caroline, P = Perry, D = Dasco, A = Abigail, . = Kosong, P = Player");
        System.out.println("Posisi Player: (" + playerX + ", " + playerY + ")");
    }

    public boolean movePlayer(char direction) {
        int newX = playerX, newY = playerY;
        switch (direction) {
            case 'w': newY--; break;
            case 's': newY++; break;
            case 'a': newX--; break;
            case 'd': newX++; break;
            default: return false;
        }
        if (newX >= 0 && newX < MAP_WIDTH && newY >= 0 && newY < MAP_HEIGHT) {
            playerX = newX;
            playerY = newY;
            return true;
        }
        return false;
    }

    // Method ini dipanggil saat ingin reset posisi ke FarmMap (misal setelah dari FarmMap)
    public void movePlayerToFarmMap() {
        int[] loc = locations.get("FarmMap");
        playerX = loc[0];
        playerY = loc[1];
    }
    //test main
    public static void main(String[] args) {
        WorldMap worldMap = new WorldMap();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== TEST INTERAKTIF WORLD MAP ===");
        System.out.println("W/A/S/D: Gerak, Q: Keluar");
        while (true) {
            worldMap.displayMap();
            String area = worldMap.getAdjacentSpecialArea();
            if (area != null) {
                System.out.println("Kamu berada di dekat " + area + ". Masuk ke sini? (y/n)");
                String ans = scanner.nextLine().trim();
                if (ans.equalsIgnoreCase("y")) {
                    worldMap.enterArea(area, scanner);
                    continue;
                }
            }
            System.out.print("Gerak (W/A/S/D) atau Q untuk keluar: ");
            String in = scanner.nextLine().trim().toLowerCase();
            if (in.isEmpty()) continue;
            char c = in.charAt(0);
            if (c == 'q') break;
            if ("wasd".contains("" + c)) {
                boolean ok = worldMap.movePlayer(c);
                if (!ok) System.out.println("Tidak bisa bergerak ke arah tersebut!");
            }
        }
        System.out.println("Selesai.");
    }
}