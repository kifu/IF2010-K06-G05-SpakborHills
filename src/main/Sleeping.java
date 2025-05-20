package main;

public class Sleeping {
    public class SleepingAction implements Action {
    private int currentHour; // 24h format

    public SleepingAction(int currentHour) {
        this.currentHour = currentHour;
    }

    @Override
    public void execute(Player player) {
        if (!canExecute(player)) return;
        if (player.getEnergy() == 0) {
            player.setEnergy(10);
            System.out.println("Energi kosong, hanya terisi +10.");
        } else if (player.getEnergy() < Player.MAX_ENERGY * 0.1) {
            player.setEnergy(Player.MAX_ENERGY / 2);
            System.out.println("Energi sangat rendah, hanya terisi setengah.");
        } else {
            player.setEnergy(Player.MAX_ENERGY);
            System.out.println("Energi terisi penuh!");
        }
        // Time skip ke pagi
        System.out.println("Waktu tidur... Time skip ke pagi hari.");
    }

    @Override
    public int getEnergyCost() { return 0; }

    @Override
    public int getTimeCost() { return 0; }

    @Override
    public boolean canExecute(Player player) {
        return player.getLocation().equalsIgnoreCase("House");
    }
    }
}
