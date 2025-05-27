package test;

public class FishTester {
    public static void main(String[] args) {
        Season[] seasons = {Season.SUMMER, Season.WINTER};
        Weather[] weathers = {Weather.ANY};
        Location[] locations = {Location.OCEAN};
        Time time = new Time(6, 0, 11, 0); // 06:00 - 11:00
        Rank rank = Rank.REGULAR;
        Gold price = new Gold(40);

        Fish halibut = new Fish(seasons, time, weathers, locations, rank, price);

        System.out.println("== Fish Test ==");
        System.out.println("Rank: " + halibut.GetFishRank("Halibut"));
        System.out.println("Price: " + halibut.GetFishPrice("Halibut"));
        System.out.println("Energy Restored: " + halibut.getEnergyRestored());
    }
}
