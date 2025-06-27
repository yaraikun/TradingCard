package com.tcis;

/**
 * Represents a type of trading card.
 * This class is immutable; its state cannot be changed after creation.
 */
public class Card {

    // --- Constants ---

    public static final double NORMAL_MULTIPLIER = 1.0;       // No increase
    public static final double EXTENDED_ART_MULTIPLIER = 1.5; // 50% increase
    public static final double FULL_ART_MULTIPLIER = 2.0;     // 100% increase
    public static final double ALT_ART_MULTIPLIER = 3.0;      // 200% increase

    // --- Properties ---

    private final String name;
    private final String rarity;
    private final String variant;
    private final double baseValue;

    // --- Constructors ---

    /**
     * Constructs a new Card with all its essential properties.
     *
     * @param name      The unique name of the card.
     * @param rarity    The rarity of the card (e.g., "Common", "Rare").
     * @param variant   The variant of the card (e.g., "Normal", "Full-art").
     * @param baseValue The base dollar value of the card.
     */
    public Card(String name, String rarity, String variant, double baseValue) {
        this.name = name;
        this.rarity = rarity;
        this.variant = variant;
        this.baseValue = baseValue;
    }

    // --- Getters ---

    public String getName() {
        return name;
    }

    public String getRarity() {
        return rarity;
    }

    public String getVariant() {
        return variant;
    }

    public double getBaseValue() {
        return baseValue;
    }

    // --- Methods ---

    /**
     * Calculates the final dollar value of the card based on its variant.
     *
     * @return The calculated dollar value.
     */
    public double getCalculatedValue() {
        switch (this.variant.toLowerCase()) {
            case "extended-art":
            return this.baseValue * EXTENDED_ART_MULTIPLIER;
            case "full-art":
            return this.baseValue * FULL_ART_MULTIPLIER;
            case "alt-art":
            return this.baseValue * ALT_ART_MULTIPLIER;
            case "normal":
            default:
            return this.baseValue * NORMAL_MULTIPLIER;
        }
    }

    /**
     * Returns a string containing all details of the card for display.
     *
     * @return A formatted string with the card's details.
     */
    public String getDisplayDetails() {
        return String.format(
            "  Name: %s\n" +
            "  Rarity: %s\n" +
            "  Variant: %s\n" +
            "  Base Value: $%.2f\n" +
            "  Calculated Value: $%.2f",
            this.name, this.rarity, this.variant, this.baseValue, this.getCalculatedValue()
        );
    }

}
