package main;

public class RecoverLand implements Action{
    private final int timeCost = 5;
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
            System.out.println("Recovering Land");
            // gak ada setTile atau pilih interaksi di farmMap
            player.setEnergy(player.getEnergy() - 5); //ngurang energy
            farm.advanceTime(this.timeCost); //ngurang waktu
        }
    }

    @Override
   public int getEnergyCost(){
    return this.energyCost;
   }

    @Override
   public int getTimeCost(){
    return this.timeCost;
   }

   @Override
   public boolean canExecute(Player player){
        return player.getEnergy() >= -20;
   }
}