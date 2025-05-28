package main;
public class Gifting {
    private Player player;
    private NPC target;
    private Item gift;
    private Time time;

    public Gifting(Player player, NPC target, Item gift, Time time) {
        this.player = player;
        this.target = target;
        this.gift = gift;
        this.time = time;
    }

    public void execute() {
        if (!player.getLocation().equalsIgnoreCase(target.getName() + " House")) {
            System.out.println("Kamu harus berada di rumah " + target.getName() + " untuk memberikan hadiah.");
            return;
        }
        if (!player.getInventory().hasItems(gift, 1)) {
            System.out.println("Kamu tidak memiliki item " + gift.getName() + " di inventory.");
            return;
        }

        if (target.getLovedItems().contains(gift)) {
            target.setHeartPoint(target.getHeartPoint() + 25);
            System.out.println(target.getName() + " sangat menyukai " + gift.getName() + "! (+25)");
        } else if (target.getLikedItems().contains(gift)) {
            target.setHeartPoint(target.getHeartPoint() + 20);
            System.out.println(target.getName() + " suka " + gift.getName() + ". (+20)");
        } else if (target.getHatedItems().contains(gift)) {
            target.setHeartPoint(target.getHeartPoint() - 25);
            System.out.println(target.getName() + " benci " + gift.getName() + "!! (-25)");
        } else {
            System.out.println(target.getName() + " menerima " + gift.getName() + " tanpa reaksi.");
        }

        player.getInventory().removeItem(gift, 1);
        target.receiveGift(gift);

        player.setEnergy(player.getEnergy() - 5);
        time.advanceMinutes(getTimeCost());
    }

    public int getTimeCost() {
        return 10;
    }
}

