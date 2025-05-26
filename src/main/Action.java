public interface Action {
    void execute(Player player);
    int getEnergyCost();
    int getTimeCost();
    boolean canExecute(Player player);
}
