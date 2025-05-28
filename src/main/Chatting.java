public class Chatting implements Action {
    private NPC target;
    private Time time;

    public Chatting(NPC target, Time time) {
        this.target = target;
        this.time = time;
    }

    @Override
    public void execute(Player player) {
        if (!canExecute(player)) {
            return;
        }
        target.setHeartPoint(target.getHeartPoint() + 10);
        player.setEnergy(player.getEnergy() - getEnergyCost());
        time.advanceMinutes(getTimeCost());

        target.chat();
        System.out.println("+10 HeartPoints, -10 energi, waktu +" + getTimeCost() + " menit.");
    }

    @Override
    public int getEnergyCost() {
        return 10;
    }

    @Override
    public int getTimeCost() {
        return 10;
    }

    @Override
     public boolean canExecute(Player player) {
        String location;
        // Emily tinggal di Store
        if (target.getName().equalsIgnoreCase("Emily")) {
            location = "Store";
        } else {
            location = target.getName() + " House";
        }
        if (!player.getLocation().equalsIgnoreCase(location)) {
            System.out.println("Kamu harus berada di " + location + " untuk mengobrol. Berangkat sana ╰（‵□′）╯");
            return false;
        }
        if (player.getEnergy() < getEnergyCost()) {
            System.out.println("Energi kamu tidak cukup untuk mengobrol （︶^︶）.");
            return false;
        }
        return true;
    }
} 