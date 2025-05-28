public class Television extends Furniture {
    private Time time;
    private WorldState worldState;

    public Television(Time time, WorldState worldState) {
        super("tv", "Television (TV)", "Televisi untuk melihat cuaca pada hari tersebut", 1, 1, 'T');
        this.time = time;
        this.worldState = worldState;
    }

    @Override
    public void use(Player player) {
        // Action Watching: menonton TV
        Watching watching = new Watching(time, worldState);
        if (watching.canExecute(player)) {
            watching.execute(player);
        } else {
            System.out.println("Kamu tidak bisa menonton TV sekarang.");
        }
    }
}