package com.tcis.models;

import com.tcis.models.card.Rarity;
import com.tcis.models.card.Variant;

/*
    Class: Card

    Purpose: Represents an immutable card type with its core properties. It
    encapsulates all fundamental attributes of a card, including its name,
    value, rarity, and variant.
*/
public class Card {
    /*
        Attribute: name
        
        Purpose: Stores the unique name of the card.
    */
    private String name;

    /*
        Attribute: baseValue
        
        Purpose: Stores the base dollar value of the card before any variant
        multipliers are applied. This field is final to ensure it is immutable
        after creation.
    */
    private final double baseValue;
    
    /*
        Attribute: rarity
        
        Purpose: Stores the Rarity enum constant associated with the card.
        This field is final.
    */
    private final Rarity rarity;

    /*
        Attribute: variant
        
        Purpose: Stores the Variant enum constant associated with the card.
        This field is final.
    */
    private final Variant variant;

    /*
        Constructor: Card
        
        Purpose: Creates an instance of a Card with its necessary properties.
        It takes all fundamental attributes as parameters to ensure a Card
        object is always fully initialized upon creation.
        
        @param name: The name of the Card.
        @param baseValue: The initial value of the card, given by the user.
        @param rarity: The card's rarity, represented with a Rarity enum set by the user.
        @param variant: The card's variant, represented with a Variant enum.
    */
    public Card(String name, double baseValue, Rarity rarity, Variant variant) {
        this.name = name.trim();
        this.baseValue = baseValue;
        this.rarity = rarity;
        this.variant = variant;
    }

    /*
        Method: getName
        
        Purpose: A public getter for the card's name.
        
        Returns: A String containing the name of the Card instance.
    */
    public String getName() {
        return this.name;
    }

    /*
        Method: getBaseValue
        
        Purpose: A public getter for the card's base value. This value is used
        as the foundation for calculating the final trade value.
        
        Returns: A double representing the base value of the Card instance.
    */
    public double getBaseValue() {
        return this.baseValue;
    }

    /*
        Method: getRarity
        
        Purpose: A public getter for the card's rarity.
        
        Returns: The Rarity enum constant of the Card instance.
    */
    public Rarity getRarity() {
        return this.rarity;
    }

    /*
        Method: getVariant
        
        Purpose: A public getter for the card's variant.
        
        Returns: The Variant enum constant of the Card instance.
    */
    public Variant getVariant() {
        return this.variant;
    }

    /*
       Method: getCalculatedValue
       
       Purpose: A public getter for the card's final calculated value. This is
       obtained by multiplying the card's base value by the multiplier of its
       associated variant.
       
       Returns: A double representing the final calculated value of the Card
       instance.
   */
    public double getCalculatedValue() {
        return this.baseValue * this.variant.getMultiplier();
    }
}
