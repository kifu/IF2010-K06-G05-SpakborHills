public abstract class Furniture {
    private String id;
    private String name;
    private String description;
    private int sizeX;
    private int sizeY;
    private char logo;
    protected int price;

    // Tambahan: Koordinat untuk posisi di map
    private int x;
    private int y;

    public Furniture(String id, String name, String description, int sizeX, int sizeY, char logo) {
        if (sizeX <= 0 || sizeY <= 0) {
            throw new IllegalArgumentException("Furniture size must be greater than zero.");
        }
        this.id = id;
        this.name = name;
        this.description = description;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.logo = logo;
        this.price = 0;
        this.x = -1; // posisi default belum diletakkan
        this.y = -1;
    }

    // Getter lokasi
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    // Setter lokasi
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public int getSizeX() {
        return sizeX;
    }
    public int getSizeY() {
        return sizeY;
    }
    public char getLogo() {
        return logo;
    }
    public int getPrice(){
        return price;
    }

    public abstract void use(Player player);
}