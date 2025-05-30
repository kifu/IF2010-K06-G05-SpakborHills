package main;

public class Watering implements Action{
    private final int timeCost = 5;
    private final int energyCost = 5;
    private Farm farm;
    private Tile interacTile;


    /**
     * Assign farm to Watering as Attribute
     * @param farm
     */
    public void assignFarm(Farm farm){
        this.farm = farm;
    }

    /**
     * Assign Tile to Watering as Attribute
     * @param interacTile
     */
    public void assignTile(Tile tile){
        this.interacTile = tile;
    }
    @Override
    public void execute(Player player){
        if (canExecute(player)){
            System.out.println("Watering");
            interacTile.setIsWatered(true); // set tile isWatered to true
            player.setEnergy(player.getEnergy() - 5); //ngurang energy
            farm.advanceTime(this.timeCost); //ngurang waktu
        }
        else{
            System.out.println("You don't have enough energy to water.");
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