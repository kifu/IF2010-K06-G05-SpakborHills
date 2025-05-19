package test;

import java.util.FormatFlagsConversionMismatchException;

public class Tilling implements Action{
    private int timeCost = 5;
    private int energyCost = 5;


    
    @Override
    public void execute(Player player){
        if (canExecute(player)){
            player.setLocation('t');
            player.setEnergy(player.getEnergy() - 5); //ngurang energy
            (player.getFarm()).advanceTime(); //ngurang waktu

        }
    }

   
    @Override
   public Time getTimeCost(){
        return this.timeCost;
   }
 

  
    @Override
   public int getEnergyCost(){
        return this.energyCost;
   }

    
    @Override
   public boolean canExecute(Player player){
        return player.getEnergy() >= this.energyCost && player.getLocation() == '.';
   }

}