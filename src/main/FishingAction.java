import java.util.Random;
import java.util.Scanner;

public class FishingAction implements Action {
    private Location location;

    public FishingAction(Location location) {
        this.location = location;
    }

    @Override
    public void execute(Player player) {
        if (!canExecute(player)) return;

        // Hentikan world time dan tambahkan 15 menit
        World.getInstance().pauseTime();
        World.getInstance().addMinutes(15);

        // Kurangi energi player
        player.decreaseEnergy(getEnergyCost());

        // Dapatkan ikan berdasarkan waktu, lokasi, cuaca, musim
        Fish fish = getFishingResult(player);
        if (fish == null) {
            System.out.println("Tidak ada ikan yang tersedia saat ini.");
            World.getInstance().resumeTime();
            return;
        }

        // Tentukan batas dan range angka berdasarkan jenis ikan
        int maxRange, maxAttempt;
        String fishRank = fish.GetFishRank(fish.GetFish().name()).name(); // Asumsikan name() mengembalikan nama ikan
        switch (fishRank.toLowerCase()) {
            case "common":
                maxRange = 10;
                maxAttempt = 10;
                break;
            case "regular":
                maxRange = 100;
                maxAttempt = 10;
                break;
            case "legendary":
                maxRange = 500;
                maxAttempt = 7;
                break;
            default:
                maxRange = 10;
                maxAttempt = 10;
        }

        Random random = new Random();
        int answer = random.nextInt(maxRange) + 1;

        Scanner scanner = new Scanner(System.in);
        boolean success = false;

        System.out.println("Coba tebak angka dari 1 sampai " + maxRange);
        for (int i = 1; i <= maxAttempt; i++) {
            System.out.print("Tebakan ke-" + i + ": ");
            int guess = scanner.nextInt();
            if (guess == answer) {
                success = true;
                break;
            }
        }

        if (success) {
            System.out.println("Selamat! Kamu mendapatkan ikan " + fish.GetFish().name());
            player.getInventory().add(fish);
        } else {
            System.out.println("Sayang sekali! Kamu tidak berhasil mendapatkan ikan.");
        }

        World.getInstance().resumeTime();
    }

    @Override
    public int getEnergyCost() {
        return 5;
    }

    @Override
    public int getTimeCost() {
        return 15;
    }

    @Override
    public boolean canExecute(Player player) {
        // Pond: harus di Farm, 1 tile dari Pond
        if (location.getName().equalsIgnoreCase("Pond")) {
            return player.getCurrentMap().equals("Farm") && player.isAdjacentTo(location);
        }

        // Ocean, Forest River, Mountain Lake: harus visiting lokasi tersebut
        return player.getVisitedLocations().contains(location);
    }

    @Override
    public Fish getFishingResult(Player player) {
        Season currentSeason = World.getInstance().getSeason();
        Time currentTime = World.getInstance().getTime();
        Weather currentWeather = World.getInstance().getWeather();

        for (Fish fish : FishDatabase.getAllFish()) {
            boolean matchLocation = match(location, fish.FishLocation);
            boolean matchSeason = match(currentSeason, fish.FishSeason);
            boolean matchWeather = match(currentWeather, fish.FishWeather);
            boolean matchTime = fish.FishTime.includes(currentTime); // Asumsikan includes(Time) mengecek interval

            if (matchLocation && matchSeason && matchWeather && matchTime) {
                return fish;
            }
        }

        return null;
    }

    // Helper method: cek apakah nilai cocok dengan array
    private boolean match(Object val, Object[] list) {
        for (Object obj : list) {
            if (val.equals(obj)) return true;
        }
        return false;
    }
}
