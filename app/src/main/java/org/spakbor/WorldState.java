package org.spakbor;
import java.util.Random;

public class WorldState {
    public static final int GAME_MINUTES_PER_REAL_SECOND = 5;
    public static final int DAYS_PER_SEASON = 10;
    public static final int MIN_RAINY_DAYS_PER_SEASON = 2;
    
    private Time currentTime;
    private Season currentSeason;
    private Weather currentWeather;

    private int dayInSeasonCounter; // Counter untuk menghitung hari dalam satu musim (1 sampai DAYS_PER_SEASON)
    private int rainyDaysThisSeasonCounter; // Counter untuk menghitung hari hujan dalam satu musim
    private Random random;

    public WorldState() {
        this.currentTime = new Time(1, 6, 0); // Mulai dari hari 1, jam 6:00
        this.currentSeason = Season.SPRING; // Musim awal adalah musim semi
        this.dayInSeasonCounter = 1;
        this.rainyDaysThisSeasonCounter = 0;
        this.random = new Random();
        determineInitialWeather();
    }

    public Time getCurrentTime() {
        return currentTime;
    }

    public Season getCurrentSeason() {
        return currentSeason;
    }   

    public Weather getCurrentWeather() {
        return currentWeather;
    }
    
    // Update waktu game berdasarkan waktu nyata
    public void update(long realTimeDeltaMillis) {
        int gameMinutesPassed = (int) (realTimeDeltaMillis / 1000 * GAME_MINUTES_PER_REAL_SECOND);
        if (gameMinutesPassed > 0) {
            int previousDay = currentTime.getDay();
            currentTime.advanceMinutes(gameMinutesPassed);

            if (currentTime.getDay() != previousDay) {
                handleNewDay();
            }
        }
    }
    
    // Menangani perubahan hari
    public void handleNewDay() { 
        System.out.println("------------------------------------");
        System.out.println("Hari baru dimulai! : Day " + currentTime.getDay());
        dayInSeasonCounter++;

        if (dayInSeasonCounter > DAYS_PER_SEASON) {
            changeSeason();
        }

        this.currentWeather = determineWeatherForNewDay();
        if (this.currentWeather == Weather.RAINY) {
            rainyDaysThisSeasonCounter++;
        }

        System.out.println("Cuaca untuk Day " + this.currentTime.getDay() + ": " + this.currentWeather.getDisplayName());
        applyWeatherEffects();
    }

    // Mengubah musim
    private void changeSeason() {
        currentSeason = currentSeason.next();
        dayInSeasonCounter = 1; // Reset counter untuk hari dalam musim
        rainyDaysThisSeasonCounter = 0; // Reset counter untuk hari hujan dalam musim
        System.out.println("Musim berganti menjadi: " + currentSeason.getDisplayName());
        System.out.println("PERINGATAN: Musim baru dimulai, beberapa tanaman mungkin mati.");
    }

    private void determineInitialWeather() {
        this.currentWeather = determineWeatherForNewDay();
        if (this.currentWeather == Weather.RAINY) {
            rainyDaysThisSeasonCounter++;
        }
        // System.out.println("Weather For Day " + this.currentTime.getDay() + ": " + this.currentWeather.getDisplayName());
        // applyWeatherEffects();
    }

    private Weather determineWeatherForNewDay() {
        // Random weather 1:1
        Weather weather = random.nextDouble() < 0.5 ? Weather.SUNNY : Weather.RAINY;

        // Counter untuk menghitung hari dalam satu musim dan hari hujan yang dibutuhkan dalam satu musim
        int daysRemainingInSeason = DAYS_PER_SEASON - (dayInSeasonCounter - 1); // Hitung sisa hari dalam musim (- 1 karena hari ini sudah dihitung)
        int rainyDaysNeeded = MIN_RAINY_DAYS_PER_SEASON - rainyDaysThisSeasonCounter; // Hitung sisa hari hujan yang dibutuhkan (Minimal 2 hari hujan per musim)

        // Kondisi pemaksa untuk mengembalikan hujan
        if (rainyDaysNeeded > 0 && rainyDaysNeeded >= daysRemainingInSeason) {
            // Harus mengembalikan hujan, random weather selama 8 hari selalu sunny
            // Misal: daysRemainingInSeason = 2, rainyDaysNeeded = 2. 
            // Maka harus mengembalikan hujan 2 hari berturut-turut
            return Weather.RAINY;
        }

        return weather;
    }

    public void applyWeatherEffects() {
        if (currentWeather == Weather.RAINY) {
            System.out.println("Hujan nih. Semua tanah yang bisa ditanamin, yang udah dibajak, sama yang udah ditanami sekarang basah semua. Jadi, nggak usah nyiram hari ini.");
        } else if (currentWeather == Weather.SUNNY) {
            System.out.println("Cerah nih. Ingat buat nyiram tanaman kalau perlu.");
        }
    }

    public String getCurrentStatus() {
        return String.format("%s | Phase : %s | Season: %s | Weather: %s", 
                            currentTime.toString(), 
                            currentTime.getPhase(), 
                            currentSeason.getDisplayName(), 
                            currentWeather.getDisplayName());
    }

    // --- CHEATS ---
    public void manuallySetSeason(Season season) {
        System.out.println("[CHEATS] Set Manual Season ke " + season.getDisplayName());
        this.currentSeason = season;
        this.dayInSeasonCounter = 1; // Reset counter untuk hari dalam musim
        this.rainyDaysThisSeasonCounter = 0; // Reset counter untuk hari hujan dalam musim
        System.out.println("WARNING: Beberapa crops mungkin terpengaruh karna perubahan musim secara tiba-tiba!");
    }

    public void manuallySetWeather(Weather weather) {
        System.out.println("[CHEATS] Set Manual Weather untuk hari ini ke " + weather.getDisplayName());
        if (this.currentWeather == Weather.SUNNY && weather == Weather.RAINY) {
            rainyDaysThisSeasonCounter++;
        } else if (this.currentWeather == Weather.RAINY && weather == Weather.SUNNY) {
            rainyDaysThisSeasonCounter--;
            System.out.println("NOTE: Minimum weather rainy dalam satu sesason dapat terpengaruhi karna perubahan manual ini.");
        }
        this.currentWeather = weather;
        applyWeatherEffects();
    }

}
