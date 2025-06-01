package org.spakbor;

public class HouseMapDisplay {
    private static final int MAP_SIZE = 24;
    private static final char EMPTY = '.';
    private static final char PLAYER = 'P';

    // Helper: print map
    private static void printMap(char[][] map) {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("\nLegenda: S=SingleBed Q=QueenBed K=KingBed V=Stove T=TV P=Player .(kosong)");
    }

    // Helper: draw furniture (x, y = pojok kiri atas)
    private static void drawFurniture(char[][] map, int x, int y, int sizeX, int sizeY, char logo) {
        for (int i = y; i < y + sizeY; i++) {
            for (int j = x; j < x + sizeX; j++) {
                if (i >= 0 && i < MAP_SIZE && j >= 0 && j < MAP_SIZE)
                    map[i][j] = logo;
            }
        }
    }

    // 1. Default: Single Bed + TV
    public static void mapDefault() {
        char[][] map = new char[MAP_SIZE][MAP_SIZE];
        for (char[] row : map) java.util.Arrays.fill(row, EMPTY);

        drawFurniture(map, 2, 2, 2, 4, 'S');      // SingleBed
        drawFurniture(map, MAP_SIZE/2 - 1, 1, 1, 1, 'T'); // TV

        map[MAP_SIZE-2][MAP_SIZE/2] = PLAYER; // Player start bawah tengah

        System.out.println("== Map DEFAULT (SingleBed + TV) ==");
        printMap(map);
    }

    // 2. Single Bed + TV + Stove
    public static void mapWithStove() {
        char[][] map = new char[MAP_SIZE][MAP_SIZE];
        for (char[] row : map) java.util.Arrays.fill(row, EMPTY);

        drawFurniture(map, 2, 2, 2, 4, 'S');
        drawFurniture(map, MAP_SIZE/2 - 1, 1, 1, 1, 'T');
        drawFurniture(map, 4, 12, 1, 1, 'V'); // Stove

        map[MAP_SIZE-2][MAP_SIZE/2] = PLAYER;
        System.out.println("== Map: SingleBed + TV + Stove ==");
        printMap(map);
    }

    // 3. Single Bed + TV + Queen Bed
    public static void mapWithQueen() {
        char[][] map = new char[MAP_SIZE][MAP_SIZE];
        for (char[] row : map) java.util.Arrays.fill(row, EMPTY);

        drawFurniture(map, 2, 2, 2, 4, 'S');
        drawFurniture(map, MAP_SIZE/2 - 1, 1, 1, 1, 'T');
        drawFurniture(map, 8, 2, 4, 6, 'Q'); // QueenBed

        map[MAP_SIZE-2][MAP_SIZE/2] = PLAYER;
        System.out.println("== Map: SingleBed + TV + QueenBed ==");
        printMap(map);
    }

    // 4. Single Bed + TV + King Bed
    public static void mapWithKing() {
        char[][] map = new char[MAP_SIZE][MAP_SIZE];
        for (char[] row : map) java.util.Arrays.fill(row, EMPTY);

        drawFurniture(map, 2, 2, 2, 4, 'S');
        drawFurniture(map, MAP_SIZE/2 - 1, 1, 1, 1, 'T');
        drawFurniture(map, MAP_SIZE-8, 2, 6, 6, 'K'); // KingBed

        map[MAP_SIZE-2][MAP_SIZE/2] = PLAYER;
        System.out.println("== Map: SingleBed + TV + KingBed ==");
        printMap(map);
    }

    // 5. Single Bed + TV + Stove + QueenBed
    public static void mapStoveQueen() {
        char[][] map = new char[MAP_SIZE][MAP_SIZE];
        for (char[] row : map) java.util.Arrays.fill(row, EMPTY);

        drawFurniture(map, 2, 2, 2, 4, 'S');
        drawFurniture(map, MAP_SIZE/2 - 1, 1, 1, 1, 'T');
        drawFurniture(map, 4, 12, 1, 1, 'V');
        drawFurniture(map, 8, 2, 4, 6, 'Q');

        map[MAP_SIZE-2][MAP_SIZE/2] = PLAYER;
        System.out.println("== Map: SingleBed + TV + Stove + QueenBed ==");
        printMap(map);
    }

    // 6. Single Bed + TV + Stove + KingBed
    public static void mapStoveKing() {
        char[][] map = new char[MAP_SIZE][MAP_SIZE];
        for (char[] row : map) java.util.Arrays.fill(row, EMPTY);

        drawFurniture(map, 2, 2, 2, 4, 'S');
        drawFurniture(map, MAP_SIZE/2 - 1, 1, 1, 1, 'T');
        drawFurniture(map, 4, 12, 1, 1, 'V');
        drawFurniture(map, MAP_SIZE-8, 2, 6, 6, 'K');

        map[MAP_SIZE-2][MAP_SIZE/2] = PLAYER;
        System.out.println("== Map: SingleBed + TV + Stove + KingBed ==");
        printMap(map);
    }

    // 7. Single Bed + TV + QueenBed + KingBed
    public static void mapQueenKing() {
        char[][] map = new char[MAP_SIZE][MAP_SIZE];
        for (char[] row : map) java.util.Arrays.fill(row, EMPTY);

        drawFurniture(map, 2, 2, 2, 4, 'S');
        drawFurniture(map, MAP_SIZE/2 - 1, 1, 1, 1, 'T');
        drawFurniture(map, 8, 2, 4, 6, 'Q');
        drawFurniture(map, MAP_SIZE-8, 2, 6, 6, 'K');

        map[MAP_SIZE-2][MAP_SIZE/2] = PLAYER;
        System.out.println("== Map: SingleBed + TV + QueenBed + KingBed ==");
        printMap(map);
    }

    // 8. Semua lengkap (SingleBed + TV + Stove + QueenBed + KingBed)
    public static void mapAll() {
        char[][] map = new char[MAP_SIZE][MAP_SIZE];
        for (char[] row : map) java.util.Arrays.fill(row, EMPTY);

        drawFurniture(map, 2, 2, 2, 4, 'S');
        drawFurniture(map, MAP_SIZE/2 - 1, 1, 1, 1, 'T');
        drawFurniture(map, 4, 12, 1, 1, 'V');
        drawFurniture(map, 8, 2, 4, 6, 'Q');
        drawFurniture(map, MAP_SIZE-8, 2, 6, 6, 'K');

        map[MAP_SIZE-2][MAP_SIZE/2] = PLAYER;
        System.out.println("== Map: SEMUA FURNITURE ==");
        printMap(map);
    }

    // Untuk demo: panggil semua
    public static void main(String[] args) {
        mapDefault();
        mapWithStove();
        mapWithQueen();
        mapWithKing();
        mapStoveQueen();
        mapStoveKing();
        mapQueenKing();
        mapAll();
    }
}
