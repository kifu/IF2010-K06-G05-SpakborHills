import java.sql.Time;

public class Watering implements Action{
    private int timeCost = 5;
    private int energyCost = 5;

    @Override
    public void execute(Player player){
        if (canExecute(player)){
             System.out.println("Watering");
            Seeds.Watered(); // 
            player.setEnergy(player.getEnergy() - 5); //ngurang energy
            player.getFarm().advanceTime(); //ngurang waktu
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
           if (player.getEnergy() < this.energyCost){
                System.out.println("Not enough energy");
                return false;
            }
            else if(player.getLocation() != 'l'){
                System.out.println("Not in the right location");
                return false;
            }
            else{
                return true;
            }
    }
   
}