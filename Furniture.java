import java.util.List;
import java.util.Objects;

public abstract class Furniture extends Item {
    private String id;
    private String description;
    private int sizeX;
    private int sizeY;
    private char logo;
    protected int price;

    // Tambahan: Koordinat untuk posisi di map
    private int x;
    private int y;

    public Furniture(String id, String name, String description, int sizeX, int sizeY, char logo) {
        super("Furniture", name, -1, -1); // category = "Furniture", buyPrice dan sellPrice default -1
        if (sizeX <= 0 || sizeY <= 0) {
            throw new IllegalArgumentException("Furniture size must be greater than zero.");
        }
        super.setBuyPrice(price);
        this.id = id;
        this.description = description;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.logo = logo;
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

    // Override jika perlu logika khusus untuk harga jual furniture
    @Override
    public int getSellPrice() {
        return price; // Atau logika lain untuk harga jual
    }
}