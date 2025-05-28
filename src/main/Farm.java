package main;
public class Farm {
    private String name;
    private Time time;
    private int day;
    private Season season;
    private Weather weather;
    private Player player;
    public FarmMap farmMap;

    /**
     * Konstrukri kelas farm
     * @param name
     * @param player
     */
    public Farm(String name, Player player){
        this.name = name;
        this.time = new Time(0,0,0);
        this.season = Season.SPRING;
        this.weather = Weather.RAINY;
        this.player = player;
        this.farmMap = new FarmMap();
    }

    /**
     * mengembalikan season
     * @return
     */
    public Season getSeason(){
        return this.season;
    }
    /**
     * meberitahu waktu waktu dalam bentuk string
     * @return
     */
    public String getTime(){
        return this.time.toString();
    }

    /**
     * mengembalikan player pemilik darm
     * @return
     */
    public Player getPlayer(){
        return this.player;
    }

    /**
     * mengembalikan nama farm
     * @return
     */
    public String getName(){
        return this.name;
    }
    /**
     * mengembalikan farmMap
     * @return
     */
    public FarmMap getFarmMap(){
        return this.farmMap;
    }
    /**
     * mengatur season farm
     * @param newSeason
     */
    public void setSeasons(Season newSeason){
        this.season = newSeason;
    }
    /**
     * mengembalikkan cuaca farm
     * @return
     */
    public Weather getWeather(){
        return this.weather;
    }
    /**
     * mengatur cuaca farm
     * @param newWeather
     */
    public void setWeather(Weather newWeather){
        this.weather = newWeather;
    }
    /**
     * mengembalikan jumlah hari farm
     * @return
     */
    public int getDay(){
        return this.day;
    }
    /**
     * mengatur jumlah hari farm
     * @param newDayCount
     */
    public void setDay(int newDayCount){
        this.day = newDayCount;
    }
    /**
     * advance waktu farm
     * @param minutesToAdd jumlah menit yang akan ditambahkan ke waktu farm
     */
    public void advanceTime(int minutesToAdd){
        this.time.advanceMinutes(minutesToAdd); // advance time by minutese added
        if (this.time.getHours() == 0 && this.time.getMinutes() == 0) {
            nextDay(); // if time is reset to 00:00, advance to the next day
        }
    }
    /**
     * menampilkan status farm
     */
    public void displayStatus(){
        System.out.println("Farm Name: " + this.name);
        System.out.println("Time: " + this.time.toString());
        System.out.println("Days: " + this.day);
        System.out.println("Season: " + this.season);
        System.out.println("Weather: " + this.weather);
        System.out.println("Player: " + this.player.getName());
    }
    /**
     * advance waktu farm ke hari berikutnya
     */
    public void nextDay(){
        this.day++;
    }

    
}
