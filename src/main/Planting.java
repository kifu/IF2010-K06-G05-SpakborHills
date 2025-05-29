package main;

public class Planting implements Action{

    private final int timeCost = 5; // menunggu class Time
    private final int energyCost = 5;
    private Farm farm;

    /**
     * assign farm to Tilling as Attribute
     * @param farm
     */
    public void assignFarm(Farm farm){
        this.farm = farm;
    }
    
    @Override
    public void execute(Player player){
          if (canExecute(player)){
              System.out.println("Planting, choose seed from the inventory:");
              player.getInventory().displayInventory();
              System.err.println("input : ");
              String nameInput = System.console().readLine();
              player.getInventory().removeItem(player.getInventory().getItemByName(nameInput), 1); // mengurangi jumlah seeds di inventory
              farm.farmMap.interact(); // interaksi dengan farmMap untuk menanam
              player.setEnergy(player.getEnergy() - 5); //ngurang energy
              farm.advanceTime(this.timeCost); //ngurang waktu
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

