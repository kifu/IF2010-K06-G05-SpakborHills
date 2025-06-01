package org.spakbor;

import java.util.*;

public class House {
    public static final int MAP_SIZE = 24;
    private static final char EMPTY = '.';
    private static final char PLAYER = 'P';

    private char[][] map;
    private int playerX, playerY;
    private List<Furniture> furnitures;
    private WorldState worldState;
    private Time time;

    public House(WorldState worldState, Time time, HouseLayoutType layoutType) {
        this.worldState = worldState;
        this.time = time;
        this.map = new char[MAP_SIZE][MAP_SIZE];
        this.furnitures = new ArrayList<>();
        initializeMap();
        setLayout(layoutType);
    }

    private void initializeMap() {
        for (int i = 0; i < MAP_SIZE; i++) Arrays.fill(map[i], EMPTY);
    }

    public boolean isPlayerAtExit() {
        return playerY == MAP_SIZE - 1;
    }

    private void clearFurniture() {
        furnitures.clear();
        initializeMap();
    }

    public void setLayout(HouseLayoutType type) {
        clearFurniture();
        switch (type) {
            case DEFAULT:
                // SingleBed (2x4) di (2,2)
                SingleBed sbDefault = new SingleBed(worldState);
                placeFurniture(sbDefault, 2, 2);
                // TV (1x1) di tengah atas
                Television tvDefault = new Television(worldState);
                placeFurniture(tvDefault, MAP_SIZE/2-1, 1);
                break;
            case WITH_STOVE:
                setLayout(HouseLayoutType.DEFAULT);
                // Stove (1x1) di (4,12)
                Stove stove = new Stove();
                placeFurniture(stove, 4, 12);
                break;
            case WITH_QUEEN:
                setLayout(HouseLayoutType.DEFAULT);
                // QueenBed (4x6) di (8,2)
                QueenBed qb = new QueenBed(worldState);
                placeFurniture(qb, 8, 2);
                break;
            case WITH_KING:
                setLayout(HouseLayoutType.DEFAULT);
                // KingBed (6x6) di (MAP_SIZE-8,2)
                KingBed kb = new KingBed(worldState);
                placeFurniture(kb, MAP_SIZE-8, 2);
                break;
            case STOVE_QUEEN:
                setLayout(HouseLayoutType.WITH_STOVE);
                // QueenBed (4x6) di (8,2)
                QueenBed qb2 = new QueenBed(worldState);
                placeFurniture(qb2, 8, 2);
                break;
            case STOVE_KING:
                setLayout(HouseLayoutType.WITH_STOVE);
                // KingBed (6x6) di (MAP_SIZE-8,2)
                KingBed kb2 = new KingBed(worldState);
                placeFurniture(kb2, MAP_SIZE-8, 2);
                break;
            case QUEEN_KING:
                setLayout(HouseLayoutType.WITH_QUEEN);
                // KingBed (6x6) di (MAP_SIZE-8,2)
                KingBed kb3 = new KingBed(worldState);
                placeFurniture(kb3, MAP_SIZE-8, 2);
                break;
            case ALL:
                setLayout(HouseLayoutType.STOVE_QUEEN);
                // KingBed (6x6) di (MAP_SIZE-8,2)
                KingBed kb4 = new KingBed(worldState);
                placeFurniture(kb4, MAP_SIZE-8, 2);
                break;
        }
        // Player start seperti di map statis: bawah tengah
        playerX = MAP_SIZE/2;
        playerY = MAP_SIZE-2;
    }

    private void placeFurniture(Furniture f, int x, int y) {
        f.setLocation(x, y);
        for (int i = y; i < y + f.getSizeY(); i++)
            for (int j = x; j < x + f.getSizeX(); j++)
                map[i][j] = f.getLogo();
        furnitures.add(f);
    }

    public void displayMap() {
        System.out.print("\033[2J\033[H");
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                if (i == playerY && j == playerX) System.out.print(PLAYER + " ");
                else System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        Furniture adjacent = getAdjacentFurniture();
        if (adjacent != null) {
            System.out.println("\n[!] Kamu berada dekat dengan " + adjacent.getName() + ".");
        }
        System.out.println("\nPosisi Player: (" + playerX + ", " + playerY + ")");
        // System.out.println("Kontrol: W(atas) A(kiri) S(bawah) D(kanan) E(use/interaksi) Q(keluar rumah)");
        System.out.println("Legenda: S = SingleBed, Q = QueenBed, K = KingBed, V = Stove, T = TV");
    }

    public boolean movePlayer(char direction) {
        int newX = playerX, newY = playerY;
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
        return x >= 0 && x < MAP_SIZE && y >= 0 && y < MAP_SIZE && map[y][x] == EMPTY;
    }

    public Furniture getAdjacentFurniture() {
        int[] dx = {0, 0, -1, 1, -1, -1, 1, 1};
        int[] dy = {-1, 1, 0, 0, -1, 1, -1, 1};
        for (int i = 0; i < 8; i++) {
            int adjX = playerX + dx[i];
            int adjY = playerY + dy[i];
            if (adjX >= 0 && adjX < MAP_SIZE && adjY >= 0 && adjY < MAP_SIZE) {
                Furniture f = getFurnitureAt(adjX, adjY);
                if (f != null) return f;
            }
        }
        return null;
    }

    private Furniture getFurnitureAt(int x, int y) {
        for (Furniture f : furnitures) {
            int fx = f.getX(), fy = f.getY();
            int sx = f.getSizeX(), sy = f.getSizeY();
            if (x >= fx && x < fx + sx && y >= fy && y < fy + sy) return f;
        }
        return null;
    }

    public void use(Player player) {
        Furniture f = getAdjacentFurniture();
        if (f == null) {
            System.out.println("Tidak ada furniture di sekitar untuk digunakan.");
            return;
        }
        System.out.println("Interaksi dengan " + f.getName() + ": " + f.getDescription());
        f.use(player);
    }

    // private String getActionMessageForFurniture(Furniture f) {
    //     String id = f.getId();
    //     if (id.startsWith("bed_")) {
    //         return "Tekan [E] untuk tidur di kasur ini.";
    //     } else if (id.equals("stove")) {
    //         return "Tekan [E] untuk memasak makanan di sini.";
    //     } else if (id.equals("tv")) {
    //         return "Tekan [E] untuk menonton TV (melihat ramalan cuaca).";
    //     } else {
    //         return "Tekan [E] untuk berinteraksi.";
    //     }
    // }
    // Test Main
    // public static void main(String[] args) {
    //     // --- Pilihan layout ---
    //     Scanner scanner = new Scanner(System.in);
    //     System.out.println("Pilih layout House yang ingin kamu mainkan:");
    //     System.out.println("1. Default (SingleBed + TV)");
    //     System.out.println("2. SingleBed + TV + Stove");
    //     System.out.println("3. SingleBed + TV + Queen Bed");
    //     System.out.println("4. SingleBed + TV + King Bed");
    //     System.out.println("5. SingleBed + TV + Stove + Queen Bed");
    //     System.out.println("6. SingleBed + TV + Stove + King Bed");
    //     System.out.println("7. SingleBed + TV + Queen Bed + King Bed");
    //     System.out.println("8. Semua furniture (ALL)");

    //     int pilihan = 1;
    //     try {
    //         System.out.print("> ");
    //         pilihan = Integer.parseInt(scanner.nextLine().trim());
    //     } catch (Exception ignored) {}

    //     HouseLayoutType type = HouseLayoutType.DEFAULT;
    //     switch (pilihan) {
    //         case 2: type = HouseLayoutType.WITH_STOVE; break;
    //         case 3: type = HouseLayoutType.WITH_QUEEN; break;
    //         case 4: type = HouseLayoutType.WITH_KING; break;
    //         case 5: type = HouseLayoutType.STOVE_QUEEN; break;
    //         case 6: type = HouseLayoutType.STOVE_KING; break;
    //         case 7: type = HouseLayoutType.QUEEN_KING; break;
    //         case 8: type = HouseLayoutType.ALL; break;
    //         default: type = HouseLayoutType.DEFAULT; break;
    //     }

    //     WorldState worldState = new WorldState();
    //     Time time = new Time(3, 3, 3);
    //     House house = new House(worldState, time, type);
    //     Player player = new Player("Asep Spakbor", "Male", "FarmDuls");

    //     System.out.println("=== SELAMAT DATANG DI RUMAH ASEP SPAKBOR ===");
    //     while (true) {
    //         house.displayMap();
    //         System.out.print("\nMasukkan perintah: ");
    //         String input = scanner.nextLine().trim();
    //         if (input.isEmpty()) continue;
    //         char command = Character.toLowerCase(input.charAt(0));
    //         switch (command) {
    //             case 'w': case 'a': case 's': case 'd':
    //                 if (!house.movePlayer(command)) {
    //                     System.out.println("Tidak bisa bergerak ke arah tersebut!");
    //                 }
    //                 break;
    //             case 'e': house.use(player); break;
    //             case 'q':
    //                 System.out.println("Keluar dari rumah...");
    //                 return;
    //             default:
    //                 System.out.println("Perintah tidak dikenal!");
    //         }
    //     }
    // }
}