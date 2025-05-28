import NPC.RelationshipStatus;

public class Marrying implements Action {
    private NPC target;
    private Time time;

    public Marrying(NPC target, Time time) {
        this.target = target;
        this.time = time;
    }

    @Override
    public void execute(Player player) {
        if (!player.getInventory().hasItem("Proposal Ring")) {
            System.out.println("Kamu tidak memiliki Proposal Ring! ((beli dulu sana -.-))");
            return;
        }

        if (target.getRelationshipStatus() != RelationshipStatus.FIANCE) {
            System.out.println(target.getName() + " belum menjadi tunanganmu. Buru - buru amat nikahnya :)");
            return;
        }

        if (!target.isReadyToMarry(time)) {
            System.out.println("Belum waktunya menikah dengan " + target.getName() + ".");
            return;
        }

        // Time skip ke 22:00
        time.skipTo(22, 0);

        // Nikah!!!!!!!
        target.marry();
        player.setLocation(player.getName() + " House");
        player.setEnergy(player.getEnergy() - getEnergyCost());
        target.setRelationshipStatus(RelationshipStatus.SPOUSE);

        System.out.println("Selamat! Kamu resmi menikah dengan " + target.getName() + "!  o_o");
    }

    @Override
    public boolean canExecute(Player player) {
        return player.getEnergy() >= getEnergyCost() && player.getInventory().hasItem("Proposal Ring") && target.getRelationshipStatus() == RelationshipStatus.FIANCE &&
               target.isReadyToMarry(time);
    }

    @Override
    public int getEnergyCost() {
        return 80;
    }

    @Override
    public int getTimeCost() {
        // Hitung sisa waktu sampai 22:00
        int currentMinutes = time.getHours() * 60 + time.getMinutes();
        int targetMinutes = 22 * 60;
        return currentMinutes >= targetMinutes ? 0 : targetMinutes - currentMinutes;
    }
}
