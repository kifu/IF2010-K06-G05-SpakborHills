<<<<<<< HEAD
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
   public Time getTimeCost();

   /**
     * check if player can execute action
     * @param player
     * @return true if player can execute action
     */
   public boolean canExecute(Player player);
}
=======
public interface Action {
    void execute(Player player);
    int getEnergyCost();
    int getTimeCost();
    boolean canExecute(Player player);
}
>>>>>>> 584929d21a22453a4c166c5284936d6b47f57131
