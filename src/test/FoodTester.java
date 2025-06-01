package test;

public class FoodTester {
    public static void main(String[] args) {
        
        // Food biasa
        Food bread = new Food("Bread", 50, 25, 15);
        System.out.println(bread);
        
        // Food mahal
        Food goldenApple = new Food("Golden Apple", 500, 750, 100);
        System.out.println(goldenApple);
        
        // Food gratis
        Food berries = new Food("Wild Berries", 0, 10, 5);
        System.out.println(berries);
    }
}