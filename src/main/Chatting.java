public class Chatting {
    private Player player;
    private NPC target;
    private Time time;

    public Chatting(Player player, NPC target, Time time) {
        this.player = player;
        this.target = target;
        this.time = time;
    }

    public void execute() {
        if (!player.getLocation().equalsIgnoreCase(target.getName() + " House")) {
            System.out.println("Kamu harus berada di rumah " + target.getName() + " untuk mengobrol.");
            return;
        }

        // Lakukan chatting
        target.setHeartPoint(target.getHeartPoint() + 10);
        player.setEnergy(player.getEnergy() - 10);
        time.advanceMinutes(getTimeCost());

        System.out.println("Kamu mengobrol dengan " + target.getName() + ".");
        System.out.println("+10 HeartPoints, -10 energi, waktu +10 menit.");
    }

    public int getTimeCost() {
        return 10;
    }
}
