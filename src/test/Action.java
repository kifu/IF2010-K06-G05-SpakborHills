package test;
public interface Action{
    
    /**
     * execute actiion after checkin canExecute method
     * @param player
     */
   public void execute(Player player);

    /**
    * return energy cost of action
    * @return int
    */
   public int getEnergyCost();

    /**
     * return time cost of action
     * @return Time
     */
   public void getTimeCost();

   /**
     * check if player can execute action
     * @param player
     * @return true if player can execute action
     */
   public boolean canExecute(Player player);
}