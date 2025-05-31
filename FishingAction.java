import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

public class FishingAction extends Action {
    private static final int ENERGY_COST = 5;
    private static final int TIME_COST_MINUTES = 15;
    private static final List<String> FISHING_LOCATIONS = List.of("Pond", "Mountain Lake", "Forest River", "Ocean");

    private List<Item> allPossibleFish; 
    private Fish fishToCatch;           
    private boolean minigameWon = false;      
    private String failureReason;

    public FishingAction(Player player, WorldState world, List<Item> allGameItems) {
        super(player, world);
        this.allPossibleFish = new ArrayList<>();
        // Filter semua item untuk mendapatkan ikan saja
        for (Item item : allGameItems) {
            if (item instanceof Fish) {
                this.allPossibleFish.add(item);
            }
        }
    }

    @Override
    protected boolean validate() {
        if (player.getEnergy() < ENERGY_COST) {
            this.failureReason = "Energi tidak cukup untuk memancing.";
            return false;
        }
        if (!player.hasItemInInventory("Fishing Rod")) {
            this.failureReason = "Kamu tidak punya pancingan!";
            return false;
        }
        if (!FISHING_LOCATIONS.contains(player.getLocation())) {
            this.failureReason = "Tidak bisa memancing di lokasi ini.";
            return false;
        }
        return true;
    }

    @Override
    protected void performAction() {
        // 1. Majukan waktu
        System.out.println("Kamu melempar pancingan... Waktu berlalu selama " + TIME_COST_MINUTES + " menit.");
        int dayBefore = world.getCurrentTime().getDay();
        world.getCurrentTime().advanceMinutes(TIME_COST_MINUTES);
        if (world.getCurrentTime().getDay() > dayBefore) {
            world.handleNewDay();
        }

        // 2. Tentukan ikan yang bisa ditangkap
        List<Fish> catchableFish = getCatchableFish();
        if (catchableFish.isEmpty()) {
            System.out.println("Sepertinya tidak ada ikan yang tertarik dengan umpanmu saat ini.");
            this.fishToCatch = null;
            return;
        }

        // 3. Pilih satu ikan secara acak
        this.fishToCatch = catchableFish.get(new Random().nextInt(catchableFish.size()));
        System.out.println("Ada tarikan! Sepertinya kamu akan menangkap ikan " + fishToCatch.getFishType().getDisplayName() + "...");

        // 4. Mulai mini-game
        this.minigameWon = playFishingMinigame();
    }

    @Override
    protected void applyEffects() {
        player.setEnergy(player.getEnergy() - ENERGY_COST);
        if (this.minigameWon && this.fishToCatch != null) {
            player.getInventory().addItem(this.fishToCatch, 1);
        }
    }

    @Override
    protected String getSuccessMessage() {
        if (fishToCatch == null) {
            return "Tidak ada ikan yang didapat kali ini.";
        }
        if (minigameWon) {
            return "Berhasil! Kamu mendapatkan seekor " + fishToCatch.getName() + "!";
        } else {
            return "Yah, ikannya berhasil kabur...";
        }
    }

    @Override
    protected String getFailureMessage() {
        return this.failureReason;
    }

    // --- LOGIKA MINI-GAME ---

    private boolean playFishingMinigame() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        FishType type = fishToCatch.getFishType();
        int maxNumber, maxAttempts;

        switch (type) {
            case COMMON:
                maxNumber = 10;
                maxAttempts = 10;
                break;
            case REGULAR:
                maxNumber = 100;
                maxAttempts = 10;
                break;
            case LEGENDARY:
                maxNumber = 500;
                maxAttempts = 7;
                break;
            default:
                return false; // Tipe tidak dikenal
        }

        int targetNumber = random.nextInt(maxNumber) + 1;
        System.out.println("Tebak angka rahasia antara 1 dan " + maxNumber + ". Kamu punya " + maxAttempts + " kesempatan.");

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            System.out.print("Percobaan ke-" + attempt + ": Masukkan tebakanmu > ");
            try {
                int guess = scanner.nextInt();
                if (guess == targetNumber) {
                    System.out.println("Tebakanmu benar!");
                    return true;
                } else if (guess < targetNumber) {
                    System.out.println("Terlalu kecil!");
                } else {
                    System.out.println("Terlalu besar!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Masukkan angka.");
                scanner.next(); // Bersihkan buffer scanner
            }
        }

        System.out.println("Sayang sekali, kesempatanmu habis. Angka yang benar adalah " + targetNumber + ".");
        return false;
    }

    // --- LOGIKA PENENTUAN IKAN ---

    private List<Fish> getCatchableFish() {
        List<Fish> available = new ArrayList<>();
        String playerLocation = player.getLocation();
        Season currentSeason = world.getCurrentSeason();
        Weather currentWeather = world.getCurrentWeather();
        Time currentTime = world.getCurrentTime();

        for (Item item : allPossibleFish) {
            Fish fish = (Fish) item;
            if (isLocationValid(fish, playerLocation) &&
                isSeasonValid(fish, currentSeason) &&
                isWeatherValid(fish, currentWeather) &&
                isTimeValid(fish, currentTime)) {
                available.add(fish);
            }
        }
        return available;
    }

    private boolean isLocationValid(Fish fish, String playerLocation) {
        return fish.getLocationsStr().contains(playerLocation);
    }

    private boolean isSeasonValid(Fish fish, Season currentSeason) {
        String seasonsStr = fish.getSeasonsStr();
        if ("Any".equalsIgnoreCase(seasonsStr)) return true;
        return seasonsStr.contains(currentSeason.getDisplayName());
    }

    private boolean isWeatherValid(Fish fish, Weather currentWeather) {
        String weatherStr = fish.getWeatherStr();
        if ("Any".equalsIgnoreCase(weatherStr)) return true;
        return weatherStr.contains(currentWeather.getDisplayName());
    }

    private boolean isTimeValid(Fish fish, Time currentTime) {
        String timeStr = fish.getTimeStr();
        if ("Any".equalsIgnoreCase(timeStr)) return true;

        int currentHour = currentTime.getHours();
        String[] ranges = timeStr.split(",\\s*");
        for (String range : ranges) {
            String[] parts = range.split("-");
            int startHour = Integer.parseInt(parts[0].split("\\.")[0]);
            int endHour = Integer.parseInt(parts[1].split("\\.")[0]);

            if (endHour >= startHour) { // Waktu normal dalam satu hari
                if (currentHour >= startHour && currentHour < endHour) {
                    return true;
                }
            } else { // Melewati tengah malam (misal: 20.00 - 02.00)
                if (currentHour >= startHour || currentHour < endHour) {
                    return true;
                }
            }
        }
        return false;
    }
}