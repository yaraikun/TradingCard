package com.tcis.models;

/**
 * Represents an immutable card type with its core properties.
 * Its constructor is fortified to prevent the creation of invalid card objects,
 * acting as the final gatekeeper for data integrity.
 */
public class Card {
    private final String name;
    private final double baseValue;
    private final CardRarity rarity;
    private final CardVariant variant;

    /**
     * Constructs a new Card, validating all input.
     * @param name The name of the card. Cannot be null or blank.
     * @param baseValue The base dollar value. Cannot be negative.
     * @param rarity The card's rarity. Cannot be null.
     * @param variant The card's variant. Cannot be null.
     * @throws IllegalArgumentException if any parameter is invalid.
     */
    public Card(String name, double baseValue, CardRarity rarity, CardVariant variant) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Card name cannot be null or blank.");
        }
        if (baseValue < 0) {
            throw new IllegalArgumentException("Base value cannot be negative.");
        }
        if (rarity == null || variant == null) {
            throw new IllegalArgumentException("Rarity and Variant cannot be null.");
        }
        this.name = name.trim();
        this.baseValue = baseValue;
        this.rarity = rarity;
        this.variant = variant;
    }

    public String getName() { return name; }
    public double getBaseValue() { return baseValue; }
    public CardRarity getRarity() { return rarity; }
    public CardVariant getVariant() { return variant; }
    public double getCalculatedValue() { return baseValue * variant.getMultiplier(); }
}
