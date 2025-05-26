public class Sleeping {
    private Time time;

    public Sleeping(Time time, House house) {
        this.time = time;
    }

    /**
     * Melakukan aksi tidur pada player.
     * Energi akan dipulihkan berdasarkan aturan penalti.
     * Time akan di-skip ke pagi.
     * Jika tidur di tempat tidur (bed) dapat bonus +1 energy per furnitur bed (single/queen/king).
     * 
     * @param player Player yang tidur
     */
    public void execute(Player player) {
        if (time.getHours() >= 2) {
            System.out.println("Sudah lewat jam 02:00! Kamu pingsan dan tidur otomatis...");
        } else {
            System.out.println("Kamu tidur...");
        }

        // Hitung bonus bed (1 per tempat tidur)
        int newEnergy = 0;

        // Aturan penalti:
        if (player.getEnergy() <= 0) {
            newEnergy = 10; // Energi habis total
            System.out.println("Kamu pingsan kelelahan! Energi hanya terisi sedikit.");
        } else if (player.getEnergy() < (Player.MAX_ENERGY / 10)) {
            newEnergy = Player.MAX_ENERGY / 2; // Energi sangat sedikit (<10%)
            System.out.println("Energi menipis, kamu hanya mengisi ulang setengah energi.");
        } else {
            newEnergy = Player.MAX_ENERGY; // Energi normal
            System.out.println("Kamu merasa segar setelah tidur!");
        }

        if (newEnergy > Player.MAX_ENERGY) newEnergy = Player.MAX_ENERGY;

        player.setEnergy(newEnergy);

        // Waktu skip ke pagi
        time.nextDay();
        System.out.println("Hari baru dimulai! Energi: " + player.getEnergy());
    }

    /**
     * Cek auto sleep jika jam >= 2 pagi (02:00)
     */
    public boolean shouldAutoSleep(Time time) {
        return (time.getHours() >= 2);
    }
}
