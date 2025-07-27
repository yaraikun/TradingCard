package com.tcis.models.card;

/**
 * Represents an immutable card type with its core properties.
 *
 * <p>
 * It encapsulates all fundamental attributes of a card, including its name,
 * value, rarity, and variant. Its constructor is fortified to prevent the
 * creation of invalid card objects, acting as the final gatekeeper for data
 * integrity.
 * </p>
 */
public class Card {
    /**
     * The name of the card, which is immutable after creation.
     */
    private final String name;

    /**
     * The base dollar value of the card before any variant multipliers are
     * applied. This field is final to ensure it is immutable after creation.
     */
    private final double baseValue;
    
    /**
     * The Rarity enum constant associated with the card. This field is final.
     */
    private final Rarity rarity;

    /**
     * The Variant enum constant associated with the card. This field is final.
     */
    private final Variant variant;

    /**
     * Constructs a new Card, validating all input.
     *
     * @param name The name of the card. Cannot be null or blank.
     * @param baseValue The base dollar value. Cannot be negative.
     * @param rarity The card's rarity. Cannot be null.
     * @param variant The card's variant. Cannot be null.
     * @throws IllegalArgumentException if any parameter is invalid.
     */
    public Card(String name, double baseValue, Rarity rarity, Variant variant) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException(
                "Card name cannot be null or blank.");

        if (baseValue < 0)
            throw new IllegalArgumentException(
                "Base value cannot be negative.");

        if (rarity == null || variant == null)
            throw new IllegalArgumentException(
                "Rarity and Variant cannot be null.");

        this.name = name.trim();
        this.baseValue = baseValue;
        this.rarity = rarity;
        this.variant = variant;
    }

    /**
     * Gets the name of the card.
     *
     * @return The non-null, trimmed name of the card.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the base value of the card.
     *
     * @return The non-negative base value.
     */
    public double getBaseValue() {
        return this.baseValue;
    }

    /**
     * Gets the rarity of the card.
     *
     * @return The Rarity enum constant for this card.
     */
    public Rarity getRarity() {
        return this.rarity;
    }

    /**
     * Gets the variant of the card.
     *
     * @return The Variant enum constant for this card.
     */
    public Variant getVariant() {
        return this.variant;
    }

    /**
     * Calculates the card's real value by applying the variant's multiplier to
     * the base value. This is used for selling cards and calculating binder /
     * deck prices.
     *
     * @return The final calculated value of the card.
     */
    public double getCalculatedValue() {
        return this.baseValue * this.variant.getMultiplier();
    }
}
