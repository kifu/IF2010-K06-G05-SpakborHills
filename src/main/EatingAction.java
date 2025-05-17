public class EatingAction implements Action {
    private EdibleItem edibleItem;

    public EatingAction(EdibleItem edibleItem) {
        this.edibleItem = edibleItem;
    }

    @Override
    public void execute(Player player) {
        if (canExecute(player)) {
            player.increaseEnergy(edibleItem.getEnergy());
        }
    }

    @Override
    public int getEnergyCost() {
        return -edibleItem.getEnergy(); // Recharge energi → dianggap pengurangan cost
    }

    @Override
    public int getTimeCost() {
        return 5; // Sesuai deskripsi: makan butuh 5 menit dalam game
    }

    @Override
    public boolean canExecute(Player player) {
        // Misalnya: hanya bisa makan kalau edible item tidak null dan energy-nya > 0
        return edibleItem != null && edibleItem.getEnergy() > 0;
    }

    // Optional getter
    public EdibleItem getEdibleItem() {
        return edibleItem;
    }
}
