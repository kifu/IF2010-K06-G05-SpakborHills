import NPC.RelationshipStatus;

public class Proposing {
    private Player player;
    private NPC target;
    private Time time;

    public Proposing(Player player, NPC target, Time time) {
        this.player = player;
        this.target = target;
        this.time = time;
    }

    public void execute() {
        // Cek Proposal Ring di inventory
        if (!player.getInventory().hasItem("Proposal Ring")) {
            System.out.println("Kamu tidak memiliki Proposal Ring!");
            return;
        }

        // Cek heartPoints NPC
        if (target.getHeartPoint() < 150) {
            System.out.println(target.getName() + " belum cukup dekat untuk dilamar.");
            return;
        }

        // Cek status relationship
        if (target.getRelationshipStatus() == RelationshipStatus.SINGLE) {
            target.setRelationshipStatus(RelationshipStatus.FIANCE);
            target.setFianceDate(time.getHours() * 60 + time.getMinutes());
            player.setEnergy(player.getEnergy() - 10);
            time.advanceMinutes(getTimeCost());
            System.out.println(target.getName() + " menerima lamaranmu >_<!");
        } else {
            player.setEnergy(player.getEnergy() - 20);
            time.advanceMinutes(getTimeCost());
            System.out.println(target.getName() + " menolak lamaranmu T_T.");
        }
    }

    public int getTimeCost() {
        return 60; // 1 jam
    }
}
