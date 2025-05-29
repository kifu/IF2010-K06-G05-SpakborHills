public class Fish extends Item implements Edible {
    private FishType fishType;
    
    // String mentah sebelum di-parse
    private String seasonsStr;
    private String timeStr;
    private String weatherStr;
    private String locationsStr;

    // Atribut untuk menyimpan hasil parse jumlah untuk kalkulasi harga
    private int numSeasons;
    private int numHours;
    private int numWeatherVariations;
    private int numLocations;

    public Fish(String name, String seasonsStr, String timeStr, String weatherStr, String locationsStr, FishType fishType) {
        super("Fish", name, -1, 0); // Tidak dapat dibeli, harus memancing untuk mendapatkannya (-1), sell price dihitung di bawah
        this.fishType = fishType;
        this.seasonsStr = seasonsStr;
        this.timeStr = timeStr;
        this.weatherStr = weatherStr;
        this.locationsStr = locationsStr;

        // Parse kondisi untuk mendapatkan jumlah
        this.numSeasons = parseNumSeasons(seasonsStr);
        this.numHours = calculateTotalHours(timeStr);
        this.numWeatherVariations = parseNumWeather(weatherStr);
        this.numLocations = parseNumLocations(locationsStr);

        // Hitung dan set harga jual
        this.setSellPrice(calculateSellPrice());
    }

    private int parseNumSeasons(String seasonsInput) {
        if ("Any".equalsIgnoreCase(seasonsInput.trim())) {
            return 4; // Spring, Summer, Autumn, Winter
        }
        return seasonsInput.split(",").length;
    }

    private int parseHoursFromRange(String singleRange) { // contoh: "06.00-11.00" atau "19.00-02.00"
        String[] parts = singleRange.split("-");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Format rentang waktu tidak valid: " + singleRange);
        }
        String startStr = parts[0].trim(); // "06.00"
        String endStr = parts[1].trim();   // "11.00"

        try {
            int startHour = Integer.parseInt(startStr.split("\\.")[0]);
            int endHour = Integer.parseInt(endStr.split("\\.")[0]);

            if (endHour >= startHour) {
                return endHour - startHour;
            } else { // Melewati tengah malam
                return (24 - startHour) + endHour;
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Error parsing rentang waktu: " + singleRange + ". " + e.getMessage(), e);
        }
    }

    private int calculateTotalHours(String timeRangesInput) {
        if ("Any".equalsIgnoreCase(timeRangesInput.trim())) {
            return 24;
        }
        int totalHours = 0;
        String[] ranges = timeRangesInput.split(",\\s*"); // Pisahkan dengan koma dan spasi (dengan atau tanpa spasi)
        for (String range : ranges) {
            totalHours += parseHoursFromRange(range);
        }
        // Pastikan numHours tidak nol untuk mencegah pembagian dengan nol, minimal 1 jam jika ditentukan
        return Math.max(1, totalHours);
    }

    private int parseNumWeather(String weatherInput) {
        if ("Any".equalsIgnoreCase(weatherInput.trim())) {
            return 2; // Sunny, Rainy
        }
        return weatherInput.split(",").length; // Seharusnya 1 jika bukan "Any"
    }

    private int parseNumLocations(String locationsInput) {
        return locationsInput.split(",").length; // Hitung jumlah lokasi
    }

    private int calculateSellPrice() {
        // Rumus: (4/banyak season) * (24/jumlah jam) * (2/jumlah variasi weather) * (4/banyak lokasi) * C
        // Jangan sampe ada pembagian dengan nol!
        int factorSeasons = 4 / this.numSeasons; // Default ke 4 jika numSeasons 0
        int factorHours = 24 / this.numHours;     // Default ke 24 jika numHours 0
        int factorWeather = 2 / this.numWeatherVariations;
        int factorLocations = 4 / this.numLocations;
        
        int constantC = this.fishType.getConstantC(); // Ambil nilai C dari FishType

        int calculatedPrice = factorSeasons * factorHours * factorWeather * factorLocations * constantC;
        return Math.max(0, calculatedPrice); // Pastikan harga tidak negatif
    }

    @Override
    public int getEnergyRes() {
        return 1; // Semua ikan memulihkan 1 poin energi
    }

    public Fish getFish() {
        return this; 
    }

    public FishType getFishType() {
        return fishType;
    }

    public String getSeasonsStr() {
        return seasonsStr;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public String getWeatherStr() {
        return weatherStr;
    }

    public String getLocationsStr() {
        return locationsStr;
    }

    @Override
    public String toString() {
        return super.toString() +
               ", Tipe: " + fishType.getDisplayName() + 
               ", Season: " + seasonsStr +
               ", Waktu: " + timeStr +
               ", Weather: " + weatherStr +
               ", Lokasi: " + locationsStr;
    }
}