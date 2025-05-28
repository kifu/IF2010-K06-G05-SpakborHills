package main;

public class Tilling implements Action{
    private final int timeCost = 5;
    private final int energyCost = 5;
    private Farm farm;

    @Override
    public void execute(Player player){
        if (canExecute(player)){
            System.out.println("Tilling");
            farm.farmMap.interact();
            player.setEnergy(player.getEnergy() - 5); //ngurang energy
            farm.advanceTime(this.timeCost); // advance time 5 minutes
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
        return player.getEnergy() >= -20; // check if player has enough energy
    }
}