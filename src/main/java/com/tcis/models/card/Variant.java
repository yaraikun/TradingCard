package com.tcis.models.card;

/**
 * Represents the fixed set of variants a card can have, along with their value
 * multipliers.
 *
 * <p>
 * Only Rare and Legendary cards can have variants other than Normal. This
 * enum encapsulates both the display name and the business logic (the
 * multiplier) for each variant type.
 * </p>
 */
public enum Variant {
    /**
     * The standard, default variant with no value increase (1.0x multiplier).
     */
    NORMAL("Normal", 1.0),

    /**
     * A variant with a 50% value increase (1.5x multiplier).
     */
    EXTENDED_ART("Extended-art", 1.5),

    /**
     * A variant with a 100% value increase (2.0x multiplier).
     */
    FULL_ART("Full-art", 2.0),

    /**
     * A variant with a 200% value increase (3.0x multiplier).
     */
    ALT_ART("Alt-art", 3.0);

    /**
     * The user-friendly string representation of the variant.
     */
    private final String displayName;

    /**
     * The value multiplier associated with this variant.
     */
    private final double multiplier;

    /**
     * Private constructor for the enum.
     *
     * @param displayName The user-friendly name of the variant.
     * @param multiplier  The value multiplier associated with this variant.
     */
    Variant(String displayName, double multiplier) {
        this.displayName = displayName;
        this.multiplier = multiplier;
    }

    /**
     * Gets the user-friendly display name of the variant.
     *
     * @return The display name string (e.g., "Full-art").
     */
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * Gets the value multiplier for this variant.
     *
     * @return The value multiplier (e.g., 1.0, 2.0).
     */
    public double getMultiplier() {
        return this.multiplier;
    }

    /**
     * A static factory method that converts a user-provided string
     * (case-insensitive) into the corresponding Variant enum constant.
     *
     * @param text The string input from the user.
     * @return The matching Variant constant if found, otherwise null.
     */
    public static Variant fromString(String text) {
        if (text != null)
            for (Variant v : Variant.values())
                if (text.trim().equalsIgnoreCase(v.displayName))
                    return v;

        return null;
    }
}
