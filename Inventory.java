import java.util.ArrayList;

public class Inventory {
    private ArrayList<String> items;

    public Inventory() {
        items = new ArrayList<>();

        items.add("Parnsnips Seeds x15");
        items.add("Hoe");
        items.add("Watering Can");
        items.add("Pickaxe");
        items.add("Fishing Rod");   
    }

    public void addItem(String item) {
        items.add(item);
        System.out.println(item + " ditambah ke inventory!");
    }

    public void showInventory() {
        System.out.println("Your Inventory:");
        for (String item : items) {
            System.out.println("- " + item);
        }
    }

    public static void main(String[] args) {
        Inventory playerInventory = new Inventory();  
        playerInventory.showInventory();  

        playerInventory.addItem("Axe");
        playerInventory.showInventory(); 
    }
}
