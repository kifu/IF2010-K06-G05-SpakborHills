// SleepingAction.java

public class SleepingAction extends Action {
    private String failureReason;
    private String sleepQualityMessage;
    private boolean isAutomaticSleep;   // Important flag bruh

    private static final String SLEEP_LOCATION = "House"; 

    public SleepingAction(Player player, WorldState world, boolean isAutomaticSleep) {
        super(player, world);
        this.isAutomaticSleep = isAutomaticSleep; 
    }

    @Override
    protected boolean validate() {
        if (!this.isAutomaticSleep) {
            if (!player.getLocation().equalsIgnoreCase(SLEEP_LOCATION)) {
                this.failureReason = "Kamu hanya bisa memilih untuk tidur di " + SLEEP_LOCATION + " (rumahmu).";
                return false;
            }
        }
        return true;
    }

    @Override
    protected void performAction() {
        if (this.isAutomaticSleep) {
            if (player.getEnergy() <= Player.MIN_ENERGY) { 
                System.out.println(player.getName() + " pingsan karena kehabisan energi!");
            } else { // Pingsan karena larut malam
                System.out.println(player.getName() + " pingsan karena sudah sangat larut malam!");
            }
        } else { // Pemain memilih tidur
            System.out.println(player.getName() + " memutuskan untuk tidur...");
        }
    }

    @Override
    protected void applyEffects() {
        int currentEnergy = player.getEnergy();
        int newEnergy;

        if (currentEnergy <= 0) {
            newEnergy = 10; 
            this.sleepQualityMessage = "Kamu pingsan kelelahan! Energi hanya terisi sedikit.";
        } else if (currentEnergy < (Player.MAX_ENERGY / 10)) {
            newEnergy = Player.MAX_ENERGY / 2; 
            this.sleepQualityMessage = "Energi sangat menipis, kamu hanya berhasil memulihkan setengah energi.";
        } else {
            newEnergy = Player.MAX_ENERGY; 
            this.sleepQualityMessage = "Kamu merasa segar setelah tidur!";
        }

        if (newEnergy > Player.MAX_ENERGY) {
            newEnergy = Player.MAX_ENERGY;
        }
        player.setEnergy(newEnergy);
        
        boolean passOutDueToLowEnergy = this.isAutomaticSleep && (currentEnergy <= Player.MIN_ENERGY);
        boolean normalSleep = !this.isAutomaticSleep;

        if (normalSleep || passOutDueToLowEnergy) {
            // Jika pemain memilih tidur ATAU pingsan karena energi habis:
            world.getCurrentTime().nextDay(); 
            world.handleNewDay();         
        } else {
            // Jika ini adalah tidur otomatis karna larut malam
            world.getCurrentTime().setTime(6, 0); // Set jam ke 06:00, menit ke 00, di HARI SAAT INI.
        }       
    }

    @Override
    protected String getSuccessMessage() {
        return this.sleepQualityMessage + " Energi sekarang: " + player.getEnergy() + ". Selamat datang di hari yang baru!";
    }

    @Override
    protected String getFailureMessage() {
        return this.failureReason;
    }
}