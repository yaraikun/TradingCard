package com.tcis.models.binder;

import com.tcis.models.card.Card;
import java.util.ArrayList;

/**
 * An abstract superclass representing the fundamental structure and behavior
 * of a binder. It provides common functionality like storing cards, checking
 * capacity, and adding/removing cards. It defines several abstract methods
 * that must be implemented by concrete subclasses to enforce specific rules
 * regarding card eligibility, sellability, trading, and price calculation.
 */
public abstract class Binder {
    /**
     * A public constant representing the maximum number of cards a binder can
     * hold.
     */
    public static final int MAX_CAPACITY = 20;

    /**
     * The name of the binder, accessible by subclasses.
     */
    protected final String name;

    /**
     * The internal list of cards, accessible by subclasses.
     */
    protected final ArrayList<Card> cards;

    /**
     * Constructs a new Binder, validating the name. This is called by all
     * subclass constructors.
     * @param name The name for the binder. Cannot be null or blank.
     * @throws IllegalArgumentException if the name is invalid.
     */
    public Binder(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Binder name cannot be null or blank.");

        this.name = name.trim();
        this.cards = new ArrayList<>();
    }

    /**
     * Gets the name of the binder.
     * @return The non-null, trimmed name of the binder.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns a defensive copy of the cards list to protect the internal state.
     * @return A new ArrayList containing the cards in this binder.
     */
    public ArrayList<Card> getCards() {
        return new ArrayList<>(this.cards);
    }

    /**
     * Gets the current number of cards in the binder.
     * @return The integer count of cards.
     */
    public int getCardCount() {
        return this.cards.size();
    }

    /**
     * Checks if the binder has reached its maximum capacity.
     * @return true if the number of cards has reached the limit, false
     * otherwise.
     */
    public boolean isFull() {
        return this.cards.size() >= MAX_CAPACITY;
    }

    /**
     * Attempts to add a card to the binder. This method uses the Template
     * Method pattern: it calls the abstract {@code canAddCard} method, which
     * is defined by subclasses, to determine if the card is allowed before
     * adding it.
     * @param card The Card object to add.
     * @return true if the card was successfully added, false if the binder was
     * full or the card violated the binder's rules.
     */
    public boolean addCard(Card card) {
        if (!isFull() && canAddCard(card)) {
            this.cards.add(card);
            return true;
        }
        return false;
    }

    /**
     * Removes a card from a specific index in the binder.
     * @param index The zero-based index of the card to remove.
     * @return The removed Card object if the index was valid, or null if it
     * was out of bounds.
     */
    public Card removeCard(int index) {
        if (index >= 0 && index < this.cards.size()) {
            return this.cards.remove(index);
        }
        return null;
    }

    // --- Abstract Methods to be Implemented by Subclasses ---

    /**
     * An abstract method that defines the specific rules for what cards can be
     * added to this binder type.
     * @param card The card to check for eligibility.
     * @return true if the card is allowed in this binder, false otherwise.
     */
    public abstract boolean canAddCard(Card card);

    /**
     * An abstract method to define if this binder type can be sold as a whole
     * unit.
     * @return true if the binder is sellable, false otherwise.
     */
    public abstract boolean isSellable();

    /**
     * An abstract method to define if cards can be traded from this binder
     * type.
     * @return true if trading is allowed, false otherwise.
     */
    public abstract boolean canTrade();

    /**
     * An abstract method to calculate the sale price of the binder.
     * For non-sellable binders, this should return 0.
     * @return The total sale price of the binder, including any fees.
     */
    public abstract double calculatePrice();
}
