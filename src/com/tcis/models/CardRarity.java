package com.tcis.models;

/**
 * Represents the fixed set of rarities a card can have.
 * Using an enum provides type safety and a clear, finite list of options.
 */
public enum CardRarity {
    COMMON("Common"),
    UNCOMMON("Uncommon"),
    RARE("Rare"),
    LEGENDARY("Legendary");

    private final String displayName;

    /**
     * Private constructor for the enum.
     * @param displayName The user-friendly name of the rarity.
     */
    CardRarity(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the user-friendly display name of the rarity.
     * @return The display name string.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * A robust, case-insensitive factory method to get a CardRarity from a string.
     * This is crucial for converting user input into a valid enum constant.
     * @param text The string input from the user.
     * @return The corresponding CardRarity, or null if no match is found.
     */
    public static CardRarity fromString(String text) {
        if (text != null) {
            for (CardRarity r : CardRarity.values()) {
                if (text.trim().equalsIgnoreCase(r.displayName)) {
                    return r;
                }
            }
        }
        return null; // Return null if not found, to be handled by the validator.
    }
}
