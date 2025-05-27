package test;

public class Harvesting implements Action{
    private Tile tile;

    public void execute(Player player){
        if (Location(player) == "harvestable plant"){
            player.setEnergy(player.getEnergy - 5);
            
        }
    }
    
}