package test;
public class Farm {
    private String name;
    private Time time;
    private Season season;
    private Weather weather;
    private Player player;
    private FarmMap farmMap;


    public enum Season{
        Spring,Summer,Fall,Winter
    }
    public enum Weather{
        Sunny, Rainy
    }

    /**
     * Konstrukri kelas farm
     * @param name
     * @param player
     */
    public Farm(String name, Player player){
        this.name = name;
        this.time = LocalTime(0,0);
        this.season = Spring;
        this.weather = Rainy;
        this.player = player;
        this.farmMap = ;
    }


    
}
