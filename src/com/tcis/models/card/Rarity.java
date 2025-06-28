package com.tcis.models.card;

/**
 * Represents the fixed set of rarities a card can have.
 * Using an enum provides type safety and a clear, finite list of options.
 */
public enum Rarity {
    COMMON("Common"),
    UNCOMMON("Uncommon"),
    RARE("Rare"),
    LEGENDARY("Legendary");

    private final String displayName;

    Rarity(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Rarity fromString(String text) {
        if (text != null) {
            for (Rarity r : Rarity.values()) {
                if (text.trim().equalsIgnoreCase(r.displayName)) {
                    return r;
                }
            }
        }
        return null;
    }
}
