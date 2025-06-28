package com.tcis.models;

import com.tcis.models.card.Rarity;
import com.tcis.models.card.Variant;

/**
 * Represents an immutable card type with its core properties.
 */
public class Card {
    private final String name;
    private final double baseValue;
    private final Rarity rarity;
    private final Variant variant;

    public Card(String name, double baseValue, Rarity rarity, Variant variant) {
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
    public Rarity getRarity() { return rarity; }
    public Variant getVariant() { return variant; }
    public double getCalculatedValue() { return baseValue * variant.getMultiplier(); }
}
