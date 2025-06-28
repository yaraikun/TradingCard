package com.tcis.models;

import com.tcis.models.card.Rarity;
import com.tcis.models.card.Variant;

/**
 * Represents an immutable card type with its core properties.
 * Encapsulates its properties, which include name, value, rarity and variant
 */
public class Card {
    private String name;
    private final double baseValue;
    private final Rarity rarity;
    private final Variant variant;

    /*
    Method: Card Constructor
    Purpose: Creates an instance of a Card with its necessary properties
    @param name: Name of the Card
    @param baseValue: The initial value of the card, given by the user
    @param rarity: The card's rarity, represented with a rarity enum set by the user
    @param rarity: The card's variant, represented with a variant enum. Normal by default
    */
    public Card(String name, double baseValue, Rarity rarity, Variant variant) {
        this.name = name.trim();
        this.baseValue = baseValue;
        this.rarity = rarity;
        this.variant = variant;
    }
    /*
        Method: getName
        Purpose: A getter for the card's name
        Returns: Display name of the Card instance's name
    */
    public String getName() {
        return this.name;
    }

    /*
        Method: getBaseValue
        Purpose: A getter for the card's base value. Has a use in trading
        Returns: Base value of the Card instance
    */
    public double getBaseValue() {
        return this.baseValue;
    }

    /*
        Method: getRarity
        Purpose: A getter for the card's rarity
        Returns: Rarity of the Card instance
    */
    public Rarity getRarity() {
        return this.rarity;
    }

    /*
        Method: getVariant
        Purpose: A getter for the card's variant
        Returns: Variant of the Card instance
    */
    public Variant getVariant() {
        return this.variant;
    }

    /*
       Method: getVariant
       Purpose: A getter for the card's calculated value, which is obtained by
                multiplying the base value, and the multiplier of its attached variant
       Returns: Calculated value of the Card instance
   */
    public double getCalculatedValue() {
        return this.baseValue * this.variant.getMultiplier();
    }
}
