package test;

import java.sql.Time;

public class Planting implements Action{

    private Time timeCost = 5*60; // menunggu class Time
    private int energyCost = 5;
     public void execute(Player player){
            if (canExecute(player)){
                 System.out.println("Planting, choose seed");
                 (player.getInventory).getItem();
                player.setLocation('l');
                player.setEnergy(player.getEnergy() - 5); //ngurang energy
                (player.getFarm()).advanceTime(); //ngurang waktu
            }
     }

    /**
    * return energy cost of action
    * @return int
    */
   public int getEnergyCost(){
    return this.energyCost;
   }

    /**
     * return time cost of action
     * @return Time
     */
   public Time getTimeCost(){
    return this.timeCost;
   }

   /**
     * check if player can execute action
     * @param player
     * @return true if player can execute action
     */
   public boolean canExecute(Player player){
        return player.getEnergy() >= this.energyCost && player.getLocation() == 't';
   }
}

