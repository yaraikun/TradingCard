package com.tcis.models.card;

/**
 * Represents the fixed set of rarities a card can have.
 * Using an enum provides type safety and a clear, finite list of options.
 * This is the single source of truth for all rarity-related data in the
 * system.
 */
public enum Rarity {
    /**
     * Represents a Common card.
     */
    COMMON("Common"),

    /**
     * Represents an Uncommon card.
     */
    UNCOMMON("Uncommon"),

    /**
     * Represents a Rare card. Rare cards can have variants.
     */
    RARE("Rare"),

    /**
     * Represents a Legendary card. Legendary cards can have variants.
     */
    LEGENDARY("Legendary");

    private final String displayName;

    /**
     * Private constructor to initialize each enum constant with its display =
     * name.
     * @param displayName The user-friendly name of the rarity.
     */
    Rarity(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the user-friendly display name of the rarity.
     * @return The display name string (e.g., "Common", "Legendary").
     */
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * A static method that converts a user-provided string (case-insensitive)
     * into the corresponding Rarity enum constant.
     * @param text The string input from the user.
     * @return The matching Rarity constant if found, otherwise null.
     */
    public static Rarity fromString(String text) {
        if (text != null)
            for (Rarity r : Rarity.values())
                if (text.trim().equalsIgnoreCase(r.displayName))
                    return r;

        return null; // Return null if not found, to be handled by the 
                     // validator.
    }
}
