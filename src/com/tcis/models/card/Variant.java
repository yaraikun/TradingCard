package com.tcis.models.card;

/**
 * Represents the fixed set of variants a card can have, along with their value multipliers.
 */
public enum Variant {
    NORMAL("Normal", 1.0),
    EXTENDED_ART("Extended-art", 1.5),
    FULL_ART("Full-art", 2.0),
    ALT_ART("Alt-art", 3.0);

    private final String displayName;
    private final double multiplier;

    Variant(String displayName, double multiplier) {
        this.displayName = displayName;
        this.multiplier = multiplier;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public static Variant fromString(String text) {
        if (text != null) {
            for (Variant v : Variant.values()) {
                if (text.trim().equalsIgnoreCase(v.displayName)) {
                    return v;
                }
            }
        }
        return null;
    }
}
