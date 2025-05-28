package main;
import java.util.*;

public class House {
    // Konstanta untuk ukuran house map dan simbol furnitur
    private static final int MAP_SIZE = 24;
    private static final char EMPTY = '.';
    private static final char PLAYER = 'p';
    private static final char BED_SINGLE = '1';
    private static final char BED_QUEEN = '2';
    private static final char BED_KING = '3';
    private static final char STOVE = 'S';
    private static final char TV = 'T';

    // Map dan posisi player
    private char[][] map;
    private int playerX, playerY;
    

    // Daftar furnitur (untuk extensi/fungsi interaksi)
    private List<Furniture> furnitures;
    // WorldState
    private WorldState worldState;

    // Konstruktor
    public House(WorldState worldState) {
        map = new char[MAP_SIZE][MAP_SIZE];
        furnitures = new ArrayList<>();
        initializeMap();
        placeFurnitures();
        // Player dimulai di dekat pintu (asumsi bawah tengah)
        playerX = MAP_SIZE / 2;
        playerY = MAP_SIZE - 2;
        this.worldState = worldState;
    }

    // Inisialisasi seluruh ruangan kosong
    private void initializeMap() {
        for (int i = 0; i < MAP_SIZE; i++) {
            Arrays.fill(map[i], EMPTY);
        }
    }

    // Tempatkan furnitur utama & dekorasi
    private void placeFurnitures() {
        // Queen Bed (4x6) di pojok kiri atas
        placeFurniture(new Furniture("bed_2", "Queen Bed", 0, 0, 4, 6, BED_QUEEN));
        // Stove (1x1) di dekat kiri tengah
        placeFurniture(new Furniture("stove", "Stove", 2, 8, 1, 1, STOVE));
        // TV (1x1) di tengah atas
        placeFurniture(new Furniture("tv", "Television", MAP_SIZE/2-1, 0, 1, 1, TV));
        // Cabinet (2x2) kanan atas
    }

    // Fungsi menempatkan furnitur di map dan ke list
    private void placeFurniture(Furniture f) {
        for (int i = f.y; i < f.y + f.height; i++) {
            for (int j = f.x; j < f.x + f.width; j++) {
                map[i][j] = f.symbol;
            }
        }
        furnitures.add(f);
    }

    // Tampilkan peta house
    public void displayMap() {
        System.out.print("\033[2J\033[H");
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                if (i == playerY && j == playerX) {
                    System.out.print(PLAYER + " ");
                } else {
                    System.out.print(map[i][j] + " ");
                }
            }
            System.out.println();
        }

        System.out.println("\nPosisi Player: (" + playerX + ", " + playerY + ")");
        System.out.println("Kontrol: W(atas) A(kiri) S(bawah) D(kanan) I(interaksi) Q(keluar rumah)");
        System.out.println("Legenda: 1=SingleBed 2=QueenBed 3=KingBed S=Stove T=TV");
    }

    // Gerakkan player
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
        return x >= 0 && x < MAP_SIZE && y >= 0 && y < MAP_SIZE && map[y][x] == EMPTY;
    }

    // Cek interaksi dengan furnitur di sekitar
    public boolean canInteract() {
        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};
        for (int i = 0; i < 4; i++) {
            int adjX = playerX + dx[i];
            int adjY = playerY + dy[i];
            if (adjX >= 0 && adjX < MAP_SIZE && adjY >= 0 && adjY < MAP_SIZE) {
                char ch = map[adjY][adjX];
                if (ch != EMPTY) {
                    return true;
                }
            }
        }
        return false;
    }

    // Interaksi dengan furnitur
   public void interact(Player player, Time time) {
    int[] dx = {0, 0, -1, 1};
    int[] dy = {-1, 1, 0, 0};
    for (int i = 0; i < 4; i++) {
        int adjX = playerX + dx[i];
        int adjY = playerY + dy[i];
        if (adjX >= 0 && adjX < MAP_SIZE && adjY >= 0 && adjY < MAP_SIZE) {
            char ch = map[adjY][adjX];
            Furniture f = getFurnitureAt(adjX, adjY);
            if (f != null) {
                System.out.println("Interaksi dengan " + f.name + ": " + f.description);
                if (f.id.equals("tv")) {
                    // Aksi menonton TV
                    Watching watching = new Watching(time, worldState);
                    watching.execute(player);
                } else if (f.id.equals("stove")) {
                    System.out.println("Kamu bisa memasak makanan di sini (butuh bahan dan fuel).");
                    // Bisa panggil aksi memasak jika sudah ada
                } else if (
                    f.id.equals("bed_1") || ch == BED_SINGLE ||
                    f.id.equals("bed_2") || ch == BED_QUEEN ||
                    f.id.equals("bed_3") || ch == BED_KING
                ) {
                    // Aksi tidur pada semua jenis bed
                    Sleeping sleeping = new Sleeping(worldState);
                    sleeping.execute(player);
                } else {
                    // Furnitur lain: bisa dikembangkan sendiri
                }
                return;
            }
        }
    }
    System.out.println("Tidak ada furnitur di sekitar untuk diinteraksi.");
}
// Ambil furnitur berdasarkan posisi
    private Furniture getFurnitureAt(int x, int y) {
        for (Furniture f : furnitures) {
            if (x >= f.x && x < f.x + f.width && y >= f.y && y < f.y + f.height) {
                return f;
            }
        }
        return null;
    }
    // Main untuk testing
    public static void main(String[] args) {
        WorldState worldState = new WorldState();
        House house = new House(worldState);
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== SELAMAT DATANG DI RUMAH ASEP SPAKBOR ===");

        while (true) {
            house.displayMap();
            System.out.print("\nMasukkan perintah: ");
            String input = scanner.nextLine().trim();
            if (input.length() == 0) continue;
            char command = Character.toLowerCase(input.charAt(0));

            switch (command) {
                case 'w':
                case 'a':
                case 's':
                case 'd':
                    if (!house.movePlayer(command)) {
                        System.out.println("Tidak bisa bergerak ke arah tersebut!");
                        try { Thread.sleep(1000); } catch (InterruptedException e) {}
                    }
                    break;
                case 'q':
                    System.out.println("Keluar dari rumah...");
                    return;
                default:
                    System.out.println("Perintah tidak dikenal!");
            }
            scanner.close();
        }
    }

    // Kelas internal Furniture
    public static class Furniture {
        public String id, name, description;
        public int x, y, width, height;
        public char symbol;

        public Furniture(String id, String name, int x, int y, int width, int height, char symbol) {
            this.id = id;
            this.name = name;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.symbol = symbol;
        }
    }
}