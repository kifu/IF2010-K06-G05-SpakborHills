package org.example;
public enum FishType {
    COMMON("Common", 10),
    REGULAR("Regular", 5),
    LEGENDARY("Legendary", 25);

    private final String displayName;
    private final int constantC;

    FishType(String displayName, int constantC) {
        this.displayName = displayName;
        this.constantC = constantC;
    }

    public String getDisplayName() {
        return displayName;
    }
    
    public int getConstantC() {
        return constantC;
    }
}
