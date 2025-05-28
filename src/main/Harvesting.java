package main;
public class Harvesting implements Action{
    private final int timeCost = 5; // menunggu class Time
    private final int energyCost = 5;

    @Override
    public void execute(Player player){
        if (canExecute(player)){
            player.setEnergy(player.getEnergy() - 5);
            
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