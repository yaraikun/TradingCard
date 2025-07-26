package com.tcis.models.deck;

/**
 * Represents a deck that is intended to be sold as a whole unit.
 * This class inherits all the base functionality of a Deck from its superclass
 * and provides a concrete implementation for the {@code isSellable} method.
 */
public class SellableDeck extends Deck {

    /**
     * Constructs a new SellableDeck with a specified name.
     * It calls the superclass constructor to initialize the name and card
     * list.
     * @param name The name for the deck. Cannot be null or blank.
     */
    public SellableDeck(String name) {
        super(name);
    }

    /**
     * Determines if the deck can be sold.
     * For a SellableDeck, this is always true.
     * @return true, as Sellable Decks are designed to be sold.
     */
    @Override
    public boolean isSellable() {
        return true;
    }
}
