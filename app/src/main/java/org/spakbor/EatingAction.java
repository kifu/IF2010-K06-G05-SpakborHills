// EatingAction.java

public class EatingAction extends Action {
    private static final int TIME_COST_MINUTES = 5;
    private Item itemToEat;
    private int energyGained;
    private String failureReason;

    public EatingAction(Player player, WorldState world, Item itemToEat) {
        super(player, world);
        this.itemToEat = itemToEat;
    }

    @Override
    protected boolean validate() {
        // Cek apakah item yang diberikan null
        if (itemToEat == null) {
            this.failureReason = "Tidak ada item yang dipilih untuk dimakan.";
            return false;
        }
        
        // Cek apakah item bisa dimakan
        if (!(itemToEat instanceof Edible)) {
            this.failureReason = itemToEat.getName() + " tidak bisa dimakan!";
            return false;
        }

        // Cek apakah pemain memiliki item tersebut
        if (player.getInventory().getItemQuantity(this.itemToEat) <= 0) {
            this.failureReason = "Kamu tidak punya " + this.itemToEat.getName() + " untuk dimakan.";
            return false;
        }

        // Cek apakah energi sudah penuh
        if (player.getEnergy() >= Player.MAX_ENERGY) {
            this.failureReason = "Energimu sudah penuh, tidak perlu makan.";
            return false;
        }

        // Jika semua validasi lolos, simpan jumlah energi untuk digunakan nanti
        this.energyGained = ((Edible) this.itemToEat).getEnergyRes();
        return true;
    }

    @Override
    protected void performAction() {
        // Menampilkan pesan proses
        System.out.println(player.getName() + " memakan " + itemToEat.getName() + "...");
    }

    @Override
    protected void applyEffects() {
        // 1. Majukan waktu game sebanyak 5 menit
        int dayBefore = world.getCurrentTime().getDay();
        world.getCurrentTime().advanceMinutes(TIME_COST_MINUTES);

        // 2. Tambahkan energi ke pemain
        player.setEnergy(player.getEnergy() + this.energyGained);

        // 3. Hapus 1 item dari inventory
        player.getInventory().removeItem(this.itemToEat, 1);

        // 4. Cek apakah terjadi pergantian hari
        if (world.getCurrentTime().getDay() > dayBefore) {
            world.handleNewDay();
        }
    }

    @Override
    protected String getSuccessMessage() {
        if (this.energyGained >= 0) {
            return "Nyam! Energimu pulih sebanyak " + this.energyGained + " poin.";
        } else {
            return "Uh-oh... Kamu merasa tidak enak! Energimu berkurang " + (-this.energyGained) + " poin.";
        }
    }

    @Override
    protected String getFailureMessage() {
        return this.failureReason;
    }
}