public class Gifting implements Action {
    private NPC target;
    private Item gift;
    private Time time;

    public Gifting(NPC target, Item gift, Time time) {
        this.target = target;
        this.gift = gift;
        this.time = time;
    }

    @Override
    public void execute(Player player) {
        if (!canExecute(player)) {
            return;
        }
        target.receiveGift(gift);

        player.getInventory().removeItem(gift, 1);
        player.setEnergy(player.getEnergy() - getEnergyCost());
        time.advanceMinutes(getTimeCost());
    }

    @Override
    public int getEnergyCost() {
        return 5;
    }

    @Override
    public int getTimeCost() {
        return 10;
    }

    @Override
    public boolean canExecute(Player player) {
        String location;
        if (target.getName().equalsIgnoreCase("Emily")) {
            location = "Store";
        } else {
            location = target.getName() + " House";
        }
        if (!player.getLocation().equalsIgnoreCase(location)) {
            System.out.println("Kamu harus berada di " + location + " untuk memberikan hadiah ￣へ￣.");
            return false; 
        }

        if (!player.getInventory().hasItems(gift, 1)) {
            System.out.println("Kamu tidak memiliki item " + gift.getName() + " di inventory. Beli dulu sana (* ￣︿￣).");
            return false;
        }
        if (player.getEnergy() < getEnergyCost()) {
            System.out.println("Energi kamu tidak cukup untuk memberikan hadiah.");
            return false;
        }
        return true;
    }
}
