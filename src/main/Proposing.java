import NPC.RelationshipStatus;

public class Proposing implements Action {
    private static final int ACCEPTED_COST = 10;
    private static final int REJECTED_COST = 20;
    private NPC target;
    private Time time;

    public Proposing(NPC target, Time time) {
        this.target = target;
        this.time = time;
    }

    @Override
    public void execute(Player player) {
        if (target.getRelationshipStatus() == RelationshipStatus.SINGLE) {
            target.setRelationshipStatus(RelationshipStatus.FIANCE);
            target.setFianceDate(time.getTotalMinutes());
            player.setEnergy(player.getEnergy() - ACCEPTED_COST);
            System.out.println(target.getName() + " menerima lamaranmu >_<!");
        } else {
            player.setEnergy(player.getEnergy() - REJECTED_COST);
            System.out.println(target.getName() + " sudah tidak single! Lamaranmu ditolak, carilah yang masih single O_T.");
        }
        time.advanceMinutes(getTimeCost());
    }

    @Override
    public boolean canExecute(Player player) {
        if (!player.getInventory().hasItem("Proposal Ring")) {
            System.out.println("Kamu tidak memiliki Proposal Ring! ((beli dulu sana -.-))");
            return false;
        }

        if (target.getHeartPoint() < 150) {
            System.out.println(target.getName() + " belum cukup dekat untuk dilamar. PDKT yg bener kidss");
            return false;
        }

        if (player.getEnergy() < getEnergyCost()) {
            System.out.println("Energi tidak cukup untuk melamar. Cari energi dulu gih");
            return false;
        }

        return true;
    }

    @Override
    public int getEnergyCost() {
        return REJECTED_COST;
    }

    @Override
    public int getTimeCost() {
        return 60;
    }
}

