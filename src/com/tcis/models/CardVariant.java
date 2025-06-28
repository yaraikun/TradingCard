package com.tcis.models;

/**
 * Represents the fixed set of variants a card can have, along with their value multipliers.
 * Using an enum is efficient and type-safe for a known, finite set of strategies.
 */
public enum CardVariant {
    NORMAL("Normal", 1.0),
    EXTENDED_ART("Extended-art", 1.5),
    FULL_ART("Full-art", 2.0),
    ALT_ART("Alt-art", 3.0);

    private final String displayName;
    private final double multiplier;

    /**
     * Private constructor for the enum.
     * @param displayName The user-friendly name of the variant.
     * @param multiplier The value multiplier associated with this variant.
     */
    CardVariant(String displayName, double multiplier) {
        this.displayName = displayName;
        this.multiplier = multiplier;
    }

    /**
     * Gets the user-friendly display name of the variant.
     * @return The display name string.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets the value multiplier for this variant.
     * @return The value multiplier.
     */
    public double getMultiplier() {
        return multiplier;
    }

    /**
     * A robust, case-insensitive factory method to get a CardVariant from a string.
     * This is crucial for converting user input into a valid enum constant.
     * @param text The string input from the user.
     * @return The corresponding CardVariant, or null if no match is found.
     */
    public static CardVariant fromString(String text) {
        if (text != null) {
            for (CardVariant v : CardVariant.values()) {
                if (text.trim().equalsIgnoreCase(v.displayName)) {
                    return v;
                }
            }
        }
        return null; // Return null if not found.
    }
}
