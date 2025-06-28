package com.tcis.models.card;

/**
  Represents the fixed set of variants a card can have, along with their value multipliers.
 */
public enum Variant {
    NORMAL("Normal", 1.0),
    EXTENDED_ART("Extended-art", 1.5),
    FULL_ART("Full-art", 2.0),
    ALT_ART("Alt-art", 3.0);

    private final String displayName;
    private final double multiplier;

    /*
    Method: Variant Constructor
    Purpose: Creates an instance of variant based on specific constant given
    @param displayName: Name of variant
    @param multiplier: The specific multiplier associated with specific variant
    */
    Variant(String displayName, double multiplier) {
        this.displayName = displayName;
        this.multiplier = multiplier;
    }

    /*
        Method: getDisplayName
        Purpose: Returns the display name of the instance's variant when called
    */
    public String getDisplayName() {
        return displayName;
    }

    /*
        Method: getMultiplier
        Purpose: Returns the multiplier of the instance's variant when called
    */
    public double getMultiplier() {
        return multiplier;
    }
}
