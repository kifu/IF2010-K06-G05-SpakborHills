package main;
public class Farm {
    private String name;
    private Time time;
    private int day;
    private Season season;
    private Weather weather;
    private Player player;
    private FarmMap farmMap;

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
        this.farmMap = farmMap;
    }

    public Season getSeason(){
        return this.season;
    }

    public Time getTime(){
        return this.Time;
    }

    public Player getPlayer(){
        return this.player;
    }

    public String getName(){
        return this.name;
    }

    public void setSeasons(Season newSeason){
        this.season = newSeason;
    }
    public Weather getWeather(){
        return this.weather;
    }

    public void setWeather(Weather newWeather){
        this.weather = newWeather;
    }

    public int getDay(){
        return this.day;
    }
    public void setDay(int newDayCount){
        this.day = newDayCount;
    }

    public void advanceTime(){

    }

    public void displayStatus(){
        System.out.println("Farm Name: " + this.name);
        System.out.println("Time: " + this.time);
        System.out.println("Day: " + this.day);
        System.out.println("Season: " + this.season);
        System.out.println("Weather: " + this.weather);
        System.out.println("Player: " + this.player.getName());
    }
    
    public void nextDay(){
        this.day++;
    }

    
}
