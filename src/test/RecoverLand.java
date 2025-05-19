package test;

import java.sql.Time;

public class RecoverLand implements Action{
    private int timeCost = 5;
    private int energyCost = 5;


    @Override
    public void execute(Player player){
        if (canExecute(player)){
            System.out.println("Recovering Land");
            player.setLocation('.');
            player.setEnergy(player.getEnergy() - 5); //ngurang energy
            (player.getFarm()).advanceTime(); //ngurang waktu
        }
    }

    @Override
   public int getEnergyCost(){
    return this.energyCost;
   }

    @Override
   public Time getTimeCost(){
    return this.timeCost;
   }

   @Override
   public boolean canExecute(Player player){
        return player.getEnergy() >= this.energyCost && player.getLocation() == 't';
   }
}