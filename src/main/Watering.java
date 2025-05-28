package main;

public class Watering implements Action{
    private final int timeCost = 5;
    private final int energyCost = 5;
    private Farm farm;

    @Override
    public void execute(Player player){
        if (canExecute(player)){
            System.out.println("Watering");
            
            player.setEnergy(player.getEnergy() - 5); //ngurang energy
            farm.advanceTime(this.timeCost); //ngurang waktu
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
           return player.getEnergy() > -20 ;
    }
   
}