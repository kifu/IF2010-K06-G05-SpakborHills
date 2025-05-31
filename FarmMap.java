import java.util.Scanner;
import java.util.Random;

public class FarmMap {
    // Konstanta untuk ukuran peta dan simbol
    private static final int MAP_SIZE = 32;
    public static final char TILLABLE_LAND = '.';
    public static final char TILLED_LAND = 't';
    public static final char PLANTED_LAND = 'l';
    public static final char HOUSE = 'h';
    public static final char POND = 'o';
    public static final char SHIPPING_BIN = 's';
    public static final char PLAYER = 'p';
    
    // Grid peta dan posisi player
    private char[][] map;
    private int playerX, playerY;
    
    // Posisi objek-objek penting
    private int houseX, houseY;
    private int pondX, pondY;
    private int shippingBinX, shippingBinY;
    
    // Constructor untuk menginisialisasi peta
    public FarmMap() {
        map = new char[MAP_SIZE][MAP_SIZE];
        initializeMap();
        placeObjects();
        // PERUBAHAN DIMULAI DI SINI: Panggil method baru untuk menempatkan player
        placePlayer(); 
        // PERUBAHAN SELESAI DI SINI
    }

    // ========== BAGIAN KODE YANG TIDAK BERUBAH ==========
    public int getPlayerX() {
        return this.playerX;
    }

    public int getPlayerY() {
        return this.playerY;
    }

    public char getCharMapTile(int x, int y) {
        if (x >= 0 && x < MAP_SIZE && y >= 0 && y < MAP_SIZE) {
            return map[y][x];
        }
        return ' '; 
    }

    public void setCharMapTile(int x, int y, char tileChar) {
        if (x >= 0 && x < MAP_SIZE && y >= 0 && y < MAP_SIZE) {
            this.map[y][x] = tileChar;
        }
    }
    
    private void initializeMap() {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                map[i][j] = TILLABLE_LAND;
            }
        }
    }
    
    private void placeObjects() {
        Random rand = new Random();
        
        houseX = rand.nextInt(MAP_SIZE - 6);
        houseY = rand.nextInt(MAP_SIZE - 6);
        placeHouse();
        
        placeShippingBin();
        
        do {
            pondX = rand.nextInt(MAP_SIZE - 4);
            pondY = rand.nextInt(MAP_SIZE - 3);
        } while (isAreaOccupied(pondX, pondY, 4, 3));
        placePond();
    }
    
    private void placeHouse() {
        for (int i = houseY; i < houseY + 6; i++) {
            for (int j = houseX; j < houseX + 6; j++) {
                map[i][j] = HOUSE;
            }
        }
    }

    // Menempatkan player di depan pintu rumah.
    private void placePlayer() {
        // Posisi X pemain diatur di tengah-tengah rumah (lebar rumah 6)
        this.playerX = this.houseX + 3; 
        // Posisi Y pemain diatur 1 petak di bawah rumah (tinggi rumah 6)
        this.playerY = this.houseY + 6;

        if (this.playerY >= MAP_SIZE) {
            this.playerY = MAP_SIZE - 1;
        }
        if (this.playerX >= MAP_SIZE) {
            this.playerX = MAP_SIZE - 1;
        }
    }


    private void placeShippingBin() {
        // Cari posisi yang berjarak 1 dari rumah
        if (houseX + 7 < MAP_SIZE - 3) {
            shippingBinX = houseX + 7;
            shippingBinY = houseY;
        } else if (houseX - 3 >= 0) {
            shippingBinX = houseX - 3;
            shippingBinY = houseY;
        } else if (houseY + 6 < MAP_SIZE - 2) {
            shippingBinX = houseX;
            shippingBinY = houseY + 6;
        } else {
            shippingBinX = houseX;
            shippingBinY = houseY - 2;
        }
        
        for (int i = shippingBinY; i < shippingBinY + 2; i++) {
            for (int j = shippingBinX; j < shippingBinX + 3; j++) {
                map[i][j] = SHIPPING_BIN;
            }
        }
    }
    
    private void placePond() {
        for (int i = pondY; i < pondY + 3; i++) {
            for (int j = pondX; j < pondX + 4; j++) {
                map[i][j] = POND;
            }
        }
    }
    
    private boolean isAreaOccupied(int x, int y, int width, int height) {
        for (int i = y; i < y + height && i < MAP_SIZE; i++) {
            for (int j = x; j < x + width && j < MAP_SIZE; j++) {
                if (map[i][j] != TILLABLE_LAND) {
                    return true;
                }
            }
        }
        return false;
    }
    
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
        System.out.println("Kontrol: W(atas) A(kiri) S(bawah) D(kanan) I(interaksi) Q(keluar)");
        
        if (canInteract()) {
            System.out.println("Tekan 'I' untuk berinteraksi dengan objek terdekat!");
        }
        
        if (canVisit()) {
            System.out.println("Anda berada di ujung peta. Tekan 'V' untuk visiting!");
        }
    }
    
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
        if (x < 0 || x >= MAP_SIZE || y < 0 || y >= MAP_SIZE) {
            return false;
        }
        
        char tile = map[y][x];
        return tile != HOUSE && tile != POND && tile != SHIPPING_BIN;
    }
    
    private boolean canInteract() {
        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};
        
        for (int i = 0; i < 4; i++) {
            int adjX = playerX + dx[i];
            int adjY = playerY + dy[i];
            
            if (adjX >= 0 && adjX < MAP_SIZE && adjY >= 0 && adjY < MAP_SIZE) {
                char tile = map[adjY][adjX];
                if (tile == HOUSE || tile == POND || tile == SHIPPING_BIN) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public boolean canVisit() {
        return playerX == MAP_SIZE - 1 && playerY == 0;
    }
    
    public void interact() {
        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};
        
        for (int i = 0; i < 4; i++) {
            int adjX = playerX + dx[i];
            int adjY = playerY + dy[i];
            
            if (adjX >= 0 && adjX < MAP_SIZE && adjY >= 0 && adjY < MAP_SIZE) {
                char tile = map[adjY][adjX];
                
                if (tile == HOUSE) {
                    System.out.println("Anda berinteraksi dengan rumah! Selamat datang di rumah Anda!");
                    return;
                } else if (tile == POND) {
                    System.out.println("Anda memancing di kolam! Anda mendapat ikan!");
                    return;
                } else if (tile == SHIPPING_BIN) {
                    System.out.println("Anda menggunakan shipping bin! Barang-barang Anda dijual!");
                    return;
                }
            }
        }
        
        char currentTile = map[playerY][playerX];
        
        if (currentTile == TILLABLE_LAND) {
            System.out.println("Anda mengolah tanah menjadi siap tanam!");
            map[playerY][playerX] = TILLED_LAND;
            return;
        } else if (currentTile == TILLED_LAND) {
            System.out.println("Anda menanam benih!");
            map[playerY][playerX] = PLANTED_LAND;
            return;
        } else if (currentTile == PLANTED_LAND) {
            System.out.println("Anda memanen tanaman!");
            map[playerY][playerX] = TILLABLE_LAND;
            return;
        }
        
        System.out.println("Tidak ada yang bisa diinteraksi di sini.");
    }
    
    public void visit() {
        if (canVisit()) {
            System.out.println("Anda pergi visiting ke tempat lain!");
        } else {
            System.out.println("Anda harus berada di ujung peta untuk visiting!");
        }
    }
    
    public static void main(String[] args) {
        FarmMap farm = new FarmMap();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== SELAMAT DATANG DI FARM MAP ===");
        System.out.println("Gunakan W/A/S/D untuk bergerak, I untuk interaksi, V untuk visiting, Q untuk keluar");
        
        while (true) {
            farm.displayMap();
            System.out.print("\nMasukkan perintah: ");
            String input = scanner.nextLine().trim();
            
            if (input.length() == 0) continue;
            
            char command = Character.toLowerCase(input.charAt(0));
            
            switch (command) {
                case 'w':
                case 'a':
                case 's':
                case 'd':
                    if (!farm.movePlayer(command)) {
                        System.out.println("Tidak bisa bergerak ke arah tersebut!");
                        try { Thread.sleep(1000); } catch (InterruptedException e) {}
                    }
                    break;
                case 'i':
                    farm.interact();
                    try { Thread.sleep(1500); } catch (InterruptedException e) {}
                    break;
                case 'v':
                    farm.visit();
                    try { Thread.sleep(1500); } catch (InterruptedException e) {}
                    break;
                case 'q':
                    System.out.println("Terima kasih telah bermain!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Perintah tidak dikenal!");
                    try { Thread.sleep(1000); } catch (InterruptedException e) {}
            }
        }
    }
}