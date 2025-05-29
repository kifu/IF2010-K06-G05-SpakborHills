public class Player {
    public static final int MAX_ENERGY = 100;
    public static final int MIN_ENERGY = -20;

    private String name;
    private String gender;
    private int energy;
    private String farmName;
    private String partner;
    private int gold;
    private Inventory inventory;
    private Location location;

    public Player(String name, String gender, String farmName) {
        this.name = name;
        this.gender = gender;
        this.energy = MAX_ENERGY; 
        this.farmName = farmName;
        this.partner = null; 
        this.gold = 500; // Initial gold amount
        this.inventory = new Inventory();
        this.location = new Location("Farm"); 
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        if (energy > MAX_ENERGY) {
            this.energy = MAX_ENERGY;
        }
        else if (energy < MIN_ENERGY) {
            this.energy = MIN_ENERGY;
        }
        else {
            this.energy = energy;
        }
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }
    
    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public int getGold() {
        return gold;
    }   

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void addGold(int amount) {
        if (amount > 0) {
            this.gold += amount;
        }
    }

    public void subtractGold(int amount) {
        if (amount > 0 && this.gold >= amount) {
            this.gold -= amount;
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void showInventory() {
        inventory.displayInventory();
    }

    public String getLocation() {
        return location.getName();
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setLocation(String locationName) {
        this.location = new Location(locationName);
    }

    public void showLocation() {
        System.out.println("Current Location: " + location.getName());
    }

    @Override
    public String toString() {
        return "Player: " + name + ", Gold: " + gold + "g, Energy: " + energy;
    }
}