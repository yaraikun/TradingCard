package com.tcis.models.deck;

/**
 * Represents a standard, non-sellable deck.
 * This class inherits all the base functionality of a Deck from its superclass
 * and provides a concrete implementation for the {@code isSellable} method.
 */
public class NormalDeck extends Deck {

    /**
     * Constructs a new NormalDeck with a specified name.
     * It calls the superclass constructor to initialize the name and card
     * list.
     * @param name The name for the deck. Cannot be null or blank.
     */
    public NormalDeck(String name) {
        super(name);
    }

    /**
     * Determines if the deck can be sold.
     * For a NormalDeck, this is always false.
     * @return false, as Normal Decks cannot be sold.
     */
    @Override
    public boolean isSellable() {
        return false;
    }
}
