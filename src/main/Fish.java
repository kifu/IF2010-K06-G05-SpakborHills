package main;
public class Fish extends EdibleItem {
    private Season[] fishSeason;
    private Time fishTime;
    private Weather[] fishWeather;
    private Location[] fishLocation;
    private Rank fishRank;
    private Gold fishPrice;

    // Constructor
    public Fish(Season[] season, Time time, Weather[] weather, Location[] location, Rank rank, Gold price) {
        super(1); // Semua ikan mengembalikan +1 energi saat dimakan
        this.fishSeason = season;
        this.fishTime = time;
        this.fishWeather = weather;
        this.fishLocation = location;
        this.fishRank = rank;
        this.fishPrice = price;
    }

    // Method sesuai class diagram
    public Fish GetFish() {
        return this;
    }

    public Season[] GetFishSeason(String name) {
        return this.fishSeason;
    }

    public Time GetFishTime(String name) {
        return this.fishTime;
    }

    public Weather[] GetFishWeather(String name) {
        return this.fishWeather;
    }

    public Location[] GetFishLocation(String name) {
        return this.fishLocation;
    }

    public Rank GetFishRank(String name) {
        return this.fishRank;
    }

    public Gold GetFishPrice(String name) {
        return this.fishPrice;
    }

    public void SetFishSeason(String name, Season[] season) {
        this.fishSeason = season;
    }

    public void SetFishTime(String name, Time time) {
        this.fishTime = time;
    }

    public void SetFishWeather(String name, Weather[] weather) {
        this.fishWeather = weather;
    }

    public void SetFishLocation(String name, Location[] location) {
        this.fishLocation = location;
    }

    public void SetFishRank(String name, Rank rank) {
        this.fishRank = rank;
    }

    public void SetFishPrice(String name, Gold price) {
        this.fishPrice = price;
    }
}
