package main;

public class RecoverLand implements Action{
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
     * assign Tile to Tilling as Attribute
     * @param interacTile
     */
    public void assignTile(Tile tile){
        this.interacTile = tile;
    }
    
    @Override
    public void execute(Player player){
        if (canExecute(player)){
            System.out.println("Recovering Land");
            // gak ada setTile atau pilih interaksi di farmMap
            interacTile.setType(Tile.TileType.Soil); // set tile type to Soil
            player.setEnergy(player.getEnergy() - 5); //ngurang energy
            farm.advanceTime(this.timeCost); //ngurang waktu
        }
        else{
            System.out.println("You don't have enough energy to recover land.");
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