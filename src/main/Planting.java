package main;

public class Planting implements Action{

    private final int timeCost = 5; // menunggu class Time
    private final int energyCost = 5;
    private Farm farm;
    private Tile interacTile;

    /**
     * assign farm to Tilling as Attribute
     * @param farm
     */
    public void assignFarm(Farm farm){
        this.farm = farm;
    }

    public void assignTile(Tile tile){
        this.interacTile = tile; 
    }
    
    @Override
    public void execute(Player player){
          if (canExecute(player)){
            boolean correctItem = false;
            System.out.println("Planting, choose seed from the inventory:");
            player.getInventory().displayInventory();
            System.err.println("input : ");
            String nameInput = "";
            while (!correctItem){
                nameInput = System.console().readLine();
                if (player.getInventory().getItemByName(nameInput) == null) {
                    System.out.println("Item not found in inventory.");
                }
                else if (!(player.getInventory().getItemByName(nameInput) instanceof Seeds)) {
                    System.out.println("Item is not a seed, please choose a seed.");
                } 
                else {
                    correctItem = true; // item found and is a seed
                    System.out.println("You have chosen: " + nameInput);
                }
            }
            player.getInventory().removeItem(player.getInventory().getItemByName(nameInput), 1); // mengurangi jumlah seeds di inventory
            interacTile.setType(Tile.TileType.Planted); // set tile type to Planted
            interacTile.setPlanted((Seeds) player.getInventory().getItemByName(nameInput)); // set planted item to the tile
            farm.farmMap.interact(); // interaksi dengan farmMap untuk menanam

            player.setEnergy(player.getEnergy() - 5); //ngurang energy
            farm.advanceTime(this.timeCost); //ngurang waktu
          }
          else{
            System.out.println("You don't have enough energy to plant.");
          }
            
    }
     
    /**
    * return energy cost of action
    * @return int
    */
    @Override
    public int getEnergyCost(){
      return this.energyCost;
    }

    /**
     * return time cost of action
     * @return int
     */
    @Override
    public int getTimeCost(){
        return this.timeCost;
    }

   /**
     * check if player can execute action
     * @param player
     * @return true if player can execute action
     */
    @Override
    public boolean canExecute(Player player){
          return player.getEnergy() > -20;
    }
}

