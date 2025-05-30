package main;
public class Harvesting implements Action{
    private final int timeCost = 5; // menunggu class Time
    private final int energyCost = 5;
    private Farm farm;
    private Tile interacTile;


    // Create crop for easing of adding item to player inventory
    Crops Parsnip = new Crops("Parsnip", 50, 35, 1);
    Crops Cauliflower = new Crops("Cauliflower", 200, 150, 1);
    Crops Potato = new Crops("Potato", 0, 80, 1);
    Crops Wheat = new Crops("Wheat", 50, 30, 3);
    Crops Blueberry = new Crops("Blueberry", 150, 40, 3);
    Crops Tomato = new Crops("Tomato", 19, 60, 2);
    Crops HotPepper = new Crops("Hot Pepper", 0, 40, 1);
    Crops Melon = new Crops("Melon", 0, 250, 1);
    Crops Cranberry = new Crops("Cranberry", 0, 25, 10);
    Crops Pumpkin = new Crops("Pumpkin", 300, 250, 1);
    Crops Grape = new Crops("Grape", 100, 10, 20);
    
    //create new inventory for existing crops to ease adding harvested crop
    Inventory availableCrops = new Inventory();
    {
    availableCrops.addItem(Parsnip, 1);
    availableCrops.addItem(Cauliflower, 1);
    availableCrops.addItem(Potato, 1);
    availableCrops.addItem(Wheat, 3);
    availableCrops.addItem(Blueberry, 3);
    availableCrops.addItem(Tomato, 2);
    availableCrops.addItem(HotPepper, 1);
    availableCrops.addItem(Melon, 1);
    availableCrops.addItem(Cranberry, 10);
    availableCrops.addItem(Pumpkin, 1);
    availableCrops.addItem(Grape, 20);
    }

    /**
     * assign farm to Tilling as Attribute
     * @param farm
     */
    public void assignFarm(Farm farm){
        this.farm = farm;
    }
    /**
     * assign Tile to Tilling as Attribute
     * @param interacTile
     */
    public void assignTile(Tile tile){
        this.interacTile = tile;
    }

    @Override
    public void execute(Player player){
        if (canExecute(player)){
            player.setEnergy(player.getEnergy() - 5);
            interacTile.setType(Tile.TileType.Soil); // set tile type to Soil
            // add harvested crop to player inventory
            String tilePlantedSeedName = interacTile.getPlanted().getName();
            if (availableCrops.hasItem(interacTile.getPlanted().getName())) {
                String tilePlantedItemName = tilePlantedSeedName.replace(" Seeds", ""); // so it name is available in availableCrops
                Crops tilePlantedCrop = (Crops) availableCrops.getItemByName(tilePlantedItemName);
                player.getInventory().addItem(tilePlantedCrop,tilePlantedCrop.getCropPerHarvest());
            }
            else {
                System.out.println("Is not an avalable crop");
            }
        }
        else{
            System.out.println("You don't have enough energy to harvest.");
        }
                
        
    }

    @Override
    public int getTimeCost(){
        return this.timeCost;
    }
    @Override
    public int getEnergyCost(){
        return this.energyCost;
    }

    @Override
    public boolean canExecute(Player player){
        return player.getEnergy() > -20;
    }
}