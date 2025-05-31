public class Television extends Furniture {
    private WorldState worldState;

    public Television(WorldState worldState) {
        super("tv", "Television (TV)", "Televisi untuk melihat cuaca pada hari tersebut", 1, 1, 'T');
        this.worldState = worldState;
    }

    @Override
    public void use(Player player) {
        // Action Watching: menonton TV
        WatchingAction watching = new WatchingAction(player, worldState);
        watching.execute(); // WatchingAction menangani validasi dan eksekusi
    }
}
