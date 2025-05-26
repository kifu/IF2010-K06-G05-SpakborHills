import NPC.RelationshipStatus;

public class Marrying {
    private Player player;
    private NPC target;
    private Time time;

    public Marrying(Player player, NPC target, Time time) {
        this.player = player;
        this.target = target;
        this.time = time;
    }

    public void execute() {
        if (!player.getInventory().hasItem("Proposal Ring")) {
            System.out.println("Kamu tidak memiliki Proposal Ring!");
            return;
        }

        if (target.getRelationshipStatus() != RelationshipStatus.FIANCE) {
            System.out.println(target.getName() + " belum menjadi tunanganmu.");
            return;
        }

        if (!target.isReadyToMarry(time)) {
            System.out.println("Belum waktunya menikah dengan " + target.getName() + ".");
            return;
        }

        // Hitung timeCost sampai 22:00
        int timeCost = getTimeCost();
        time.advanceMinutes(timeCost);

        target.marry();
        player.setEnergy(player.getEnergy() - 80);
        time.skipTo(22, 0);
        target.setRelationshipStatus(RelationshipStatus.SPOUSE);
        System.out.println("Selamat! Kamu resmi menikah dengan " + target.getName() + "! ");
    }

    public int getTimeCost() {
        int currentMinuteTotal = time.getHours() * 60 + time.getMinutes();
        int targetMinuteTotal = 22 * 60;
        return currentMinuteTotal >= targetMinuteTotal ? 0 : targetMinuteTotal - currentMinuteTotal;
    }

}
