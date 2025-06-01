package test;

public class FishTester {
        public static void main(String[] args) {
        
        // Fish biasa
        Fish salmon = new Fish("Salmon", "Spring,Summer", "06.00-18.00", "Any", "River", FishType.COMMON);
        System.out.println(salmon);
        
        // Fish legendary
        Fish goldenFish = new Fish("Golden Fish", "Any", "Any", "Any", "Ocean", FishType.LEGENDARY);
        System.out.println(goldenFish);
        
        // Fish regular
        Fish tuna = new Fish("Tuna", "Summer", "12.00-15.00", "Sunny", "Ocean", FishType.REGULAR);
        System.out.println(tuna);
    }
}