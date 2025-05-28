import java.util.*;

public class WorldMap {
    // Ukuran world map (misal 10x10)
    private static final int MAP_WIDTH = 10;
    private static final int MAP_HEIGHT = 10;

    // Simbol area
    private static final char EMPTY = '.';
    private static final char VILLAGE = 'V';
    private static final char FOREST_RIVER = 'F';
    private static final char MOUNTAIN_LAKE = 'M';
    private static final char OCEAN = 'O';
    private static final char STORE = 'S';
    private static final char NPC_HOUSE = 'N';
    private static final char PLAYER = 'P';

    private char[][] map;
    private int playerX, playerY;

    // Lokasi spesifik objek
    private Map<String, int[]> locations;

    public WorldMap() {
        map = new char[MAP_HEIGHT][MAP_WIDTH];
        locations = new HashMap<>();
        initializeMap();
        placeObjects();
        // Player mulai di Village
        int[] village = locations.get("Village");
        playerX = village[0];
        playerY = village[1];
    }

    // Setup map dasar (semua kosong)
    private void initializeMap() {
        for (int i = 0; i < MAP_HEIGHT; i++) {
            Arrays.fill(map[i], EMPTY);
        }
    }

    // Tempatkan objek-objek utama world map
    private void placeObjects() {
        // Village (pusat)
        locations.put("Village", new int[]{MAP_WIDTH / 2, MAP_HEIGHT / 2});
        map[MAP_HEIGHT / 2][MAP_WIDTH / 2] = VILLAGE;

        // Forest River (pojok kiri atas)
        locations.put("ForestRiver", new int[]{1, 1});
        map[1][1] = FOREST_RIVER;

        // Mountain Lake (pojok kanan atas)
        locations.put("MountainLake", new int[]{MAP_WIDTH - 2, 1});
        map[1][MAP_WIDTH - 2] = MOUNTAIN_LAKE;

        // Ocean (pojok kiri bawah)
        locations.put("Ocean", new int[]{1, MAP_HEIGHT - 2});
        map[MAP_HEIGHT - 2][1] = OCEAN;

        // Store (dekat village)
        locations.put("Store", new int[]{MAP_WIDTH / 2 + 2, MAP_HEIGHT / 2});
        map[MAP_HEIGHT / 2][MAP_WIDTH / 2 + 2] = STORE;

        // NPC House (dekat village)
        locations.put("NpcHouse", new int[]{MAP_WIDTH / 2 - 2, MAP_HEIGHT / 2});
        map[MAP_HEIGHT / 2][MAP_WIDTH / 2 - 2] = NPC_HOUSE;
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
        System.out.println("\nLegenda: V=Village, F=Forest River, M=Mountain Lake, O=Ocean, S=Store, N=NPC House, .=Kosong, P=Player");
        System.out.println("Posisi Player: (" + playerX + ", " + playerY + ")");
        if (canInteract()) {
            System.out.println("Tekan 'I' untuk interaksi dengan area spesial di sekitar!");
        }
        System.out.println("Kontrol: W(atas) A(kiri) S(bawah) D(kanan) I(interaksi) Q(keluar WorldMap)");
    }

    // Gerakkan player jika posisi valid
    public boolean movePlayer(char direction) {
        int newX = playerX;
        int newY = playerY;

        switch (Character.toLowerCase(direction)) {
            case 'w': newY--; break;
            case 's': newY++; break;
            case 'a': newX--; break;
            case 'd': newX++; break;
            default: return false;
        }
        if (isValidPosition(newX, newY)) {
            playerX = newX;
            playerY = newY;
            return true;
        }
        return false;
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < MAP_WIDTH && y >= 0 && y < MAP_HEIGHT;
    }

    // Cek apakah ada area spesial di sekitar player
    public boolean canInteract() {
        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};
        for (int i = 0; i < 4; i++) {
            int adjX = playerX + dx[i];
            int adjY = playerY + dy[i];
            if (adjX >= 0 && adjX < MAP_WIDTH && adjY >= 0 && adjY < MAP_HEIGHT) {
                char tile = map[adjY][adjX];
                if (tile == FOREST_RIVER || tile == MOUNTAIN_LAKE || tile == OCEAN ||
                    tile == STORE || tile == VILLAGE || tile == NPC_HOUSE) {
                    return true;
                }
            }
        }
        return false;
    }

    // Interaksi dengan area spesial di sekitar player
    public void interact() {
        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};
        for (int i = 0; i < 4; i++) {
            int adjX = playerX + dx[i];
            int adjY = playerY + dy[i];
            if (adjX >= 0 && adjX < MAP_WIDTH && adjY >= 0 && adjY < MAP_HEIGHT) {
                char tile = map[adjY][adjX];
                switch (tile) {
                    case FOREST_RIVER:
                        System.out.println("Kamu berada di Forest River! Bisa memancing di sini.");
                        return;
                    case MOUNTAIN_LAKE:
                        System.out.println("Kamu berada di Mountain Lake! Bisa memancing ikan langka.");
                        return;
                    case OCEAN:
                        System.out.println("Kamu sampai di Ocean! Banyak ikan dan pemandangan indah.");
                        return;
                    case STORE:
                        System.out.println("Ini Store! Kamu bisa belanja kebutuhan di sini.");
                        return;
                    case VILLAGE:
                        System.out.println("Kamu kembali ke Village.");
                        return;
                    case NPC_HOUSE:
                        System.out.println("Ini rumah NPC! Kamu bisa ngobrol dengan penduduk.");
                        return;
                }
            }
        }
        System.out.println("Tidak ada area spesial untuk diinteraksi di sekitar.");
    }

    // Untuk validasi: apakah player berada di dekat area tertentu
    public boolean isNear(String areaName) {
        int[] loc = locations.get(areaName);
        if (loc == null) return false;
        int dx = Math.abs(playerX - loc[0]);
        int dy = Math.abs(playerY - loc[1]);
        // Dekat berarti berjarak 1 tile secara horizontal/vertikal
        return (dx + dy == 1);
    }

    // Untuk testing/akses lokasi area
    public int[] getAreaLocation(String areaName) {
        return locations.get(areaName);
    }   

    // Cek apakah player berada tepat di lokasi area spesifik
    public boolean isAtArea(String areaName) {
        int[] loc = locations.get(areaName);
        if (loc == null) return false;
        return (playerX == loc[0] && playerY == loc[1]);
    }

    // Digunakan untuk player ketika keluar dari Farm Map
    public void movePlayerToVillage() {
        int[] loc = locations.get("Village");
        playerX = loc[0];
        playerY = loc[1];
    }

    // Main method for testing
   
}
