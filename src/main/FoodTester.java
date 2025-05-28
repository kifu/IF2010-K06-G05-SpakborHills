package main;

public class FoodTester {
    public static void main(String[] args) {
        Food sashimi = new Food(70, new Gold(300), new Gold(275));

        System.out.println("== Food Test ==");
        System.out.println("Energy: " + sashimi.getEnergyRes());
        System.out.println("Buy Price: " + sashimi.getFoodBuyPrice());
        System.out.println("Sell Price: " + sashimi.getFoodSellPrice());

        sashimi.setFoodBuyPrice("Sashimi", new Gold(310));
        System.out.println("Updated Buy Price: " + sashimi.getFoodBuyPrice());
    }
}
