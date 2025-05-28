package main;
public enum Season {
    SPRING("Spring"),
    SUMMER("Summer"),
    AUTUMN("Autumn"),
    WINTER("Winter");

    private final String displayName;

    Season(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }   

    public Season next() {
        Season[] seasons = values();
        int nextSeasonIndex = (this.ordinal() + 1) % seasons.length;
        return seasons[nextSeasonIndex]; 
    }
}
