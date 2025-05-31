package org.example;
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
        // Player dimulai di tengah peta
        playerX = 16;
        playerY = 16;
    }

    public int getPlayerX() {
        return this.playerX;
    }

    public int getPlayerY() {
        return this.playerY;
    }

    // Tambahkan getter untuk karakter tile di peta pada koordinat tertentu
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
    
    // Menginisialisasi seluruh peta dengan tillable land
    private void initializeMap() {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                map[i][j] = TILLABLE_LAND;
            }
        }
    }
    
    // Menempatkan objek-objek di peta (house, pond, shipping bin)
    private void placeObjects() {
        Random rand = new Random();
        
        // Tempatkan rumah (6x6) secara random
        houseX = rand.nextInt(MAP_SIZE - 6);
        houseY = rand.nextInt(MAP_SIZE - 6);
        placeHouse();
        
        // Tempatkan shipping bin (3x2) berjarak 1 dari rumah
        placeShippingBin();
        
        // Tempatkan pond (4x3) secara random, pastikan tidak bertabrakan
        do {
            pondX = rand.nextInt(MAP_SIZE - 4);
            pondY = rand.nextInt(MAP_SIZE - 3);
        } while (isAreaOccupied(pondX, pondY, 4, 3));
        placePond();
        
        // Tambahkan beberapa tilled dan planted land untuk contoh
        addSampleCrops();
    }
    
    // Menempatkan rumah di peta
    private void placeHouse() {
        for (int i = houseY; i < houseY + 6; i++) {
            for (int j = houseX; j < houseX + 6; j++) {
                map[i][j] = HOUSE;
            }
        }
    }
    
    // Menempatkan shipping bin berjarak 1 dari rumah
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
    
    // Menempatkan pond di peta
    private void placePond() {
        for (int i = pondY; i < pondY + 3; i++) {
            for (int j = pondX; j < pondX + 4; j++) {
                map[i][j] = POND;
            }
        }
    }
    
    // Cek apakah area sudah ditempati objek lain
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
    
    // Menambahkan beberapa contoh tanaman
    private void addSampleCrops() {
        // Tambahkan beberapa planted land
        for (int i = 9; i < 12; i++) {
            for (int j = 1; j < 4; j++) {
                if (map[i][j] == TILLABLE_LAND) {
                    map[i][j] = PLANTED_LAND;
                }
            }
        }
        
        // Tambahkan beberapa tilled land
        for (int i = 9; i < 12; i++) {
            for (int j = 5; j < 8; j++) {
                if (map[i][j] == TILLABLE_LAND) {
                    map[i][j] = TILLED_LAND;
                }
            }
        }
    }
    
    // Menampilkan peta ke layar
    public void displayMap() {
        // Bersihkan layar
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
        
        // Tampilkan informasi player
        System.out.println("\nPosisi Player: (" + playerX + ", " + playerY + ")");
        System.out.println("Kontrol: W(atas) A(kiri) S(bawah) D(kanan) I(interaksi) Q(keluar)");
        
        // Cek apakah player bisa berinteraksi
        if (canInteract()) {
            System.out.println("Tekan 'I' untuk berinteraksi dengan objek terdekat!");
        }
        
        // Cek apakah player bisa visiting (di ujung peta)
        if (canVisit()) {
            System.out.println("Anda berada di ujung peta. Tekan 'V' untuk visiting!");
        }
    }
    
    // Menggerakkan player berdasarkan input
    public boolean movePlayer(char direction) {
        int newX = playerX;
        int newY = playerY;
        
        switch (Character.toLowerCase(direction)) {
            case 'w': // Gerak ke atas
                newY--;
                break;
            case 's': // Gerak ke bawah
                newY++;
                break;
            case 'a': // Gerak ke kiri
                newX--;
                break;
            case 'd': // Gerak ke kanan
                newX++;
                break;
            default:
                return false; // Input tidak valid
        }
        
        // Cek apakah posisi baru valid
        if (isValidPosition(newX, newY)) {
            playerX = newX;
            playerY = newY;
            return true;
        }
        
        return false; // Gerakan tidak valid
    }
    
    // Cek apakah posisi valid untuk player
    private boolean isValidPosition(int x, int y) {
        // Cek batas peta
        if (x < 0 || x >= MAP_SIZE || y < 0 || y >= MAP_SIZE) {
            return false;
        }
        
        // Cek apakah tidak menabrak objek
        char tile = map[y][x];
        return tile != HOUSE && tile != POND && tile != SHIPPING_BIN;
    }
    
    // Cek apakah player bisa berinteraksi dengan objek terdekat
    private boolean canInteract() {
        // Cek 4 arah sekitar player
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
    
    // Cek apakah player bisa visiting (berada di ujung peta)
    public boolean canVisit() {
        return playerX == MAP_SIZE - 1 && playerY == 0; // ujung kanan atas
    }
    
    // Melakukan interaksi dengan objek atau tile
    public void interact() {
        // PRIORITAS 1: Interaksi dengan objek di sekitar player terlebih dahulu
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
        
        // PRIORITAS 2: Jika tidak ada objek di sekitar, baru interaksi dengan tile di bawah player
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
    
    // Fungsi visiting
    public void visit() {
        if (canVisit()) {
            System.out.println("Anda pergi visiting ke tempat lain!");
            // Di sini bisa ditambahkan logic untuk berpindah ke area lain
        } else {
            System.out.println("Anda harus berada di ujung peta untuk visiting!");
        }
    }
    
    // Main method untuk menjalankan game
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