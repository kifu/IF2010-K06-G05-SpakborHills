public enum Weather {
    SUNNY("Sunny"),
    RAINY("Rainy");

    private final String displayName;

    Weather(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
