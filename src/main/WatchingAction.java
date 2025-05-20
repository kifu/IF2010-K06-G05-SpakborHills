package main;

public class WatchingAction implements Action {
    @Override
    public void execute(Player player) {
        if (!canExecute(player)) return;
        player.setEnergy(player.getEnergy() - getEnergyCost());
        System.out.println("Menonton TV selama 15 menit. -5 energi.");
    }

    @Override
    public int getEnergyCost() { 
        return 5;
    }
    @Override
    public int getTimeCost() { 
        return 15; 
    }
    @Override
    public boolean canExecute(Player player) {
        return player.getLocation().equalsIgnoreCase("House") && player.getEnergy() >= getEnergyCost();
    }
}
