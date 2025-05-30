package main;

public class Tilling implements Action{
    private final int timeCost = 5;
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

    /**
     * assign Tile to Tilling as Attribute, and set pos equal to player pos
     * This tile will be interacted with when execute is called
     * @param interacTile
     */
    public void assignTile(Tile tile){
        this.interacTile = tile;
    }

    @Override
    public void execute(Player player){
        if (canExecute(player)){
            System.out.println("Tilling");
            farm.farmMap.interact();
            interacTile.setType(Tile.TileType.Tilled); // set tile type to Tilled
            player.setEnergy(player.getEnergy() - 5); //ngurang energy
            farm.advanceTime(this.timeCost); // advance time 5 minutes
        }
        else{
            System.out.println("You don't have enough energy to till.");
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