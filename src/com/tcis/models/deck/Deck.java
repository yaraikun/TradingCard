package com.tcis.models.deck;

import com.tcis.models.card.Card;
import java.util.ArrayList;

/**
 * An abstract superclass representing the fundamental properties and behaviors of a deck.
 * It enforces a maximum capacity and ensures that all cards within it are unique
 * by name. It defines an abstract method, {@code isSellable()}, which must be
 * implemented by concrete subclasses to specify their selling behavior.
 */
public abstract class Deck {
    /**
     * A public constant representing the maximum number of unique cards a deck can hold.
     */
    public static final int MAX_CAPACITY = 10;

    /**
     * The name of the deck, which is immutable after creation. Accessible by subclasses.
     */
    protected final String name;

    /**
     * The internal list of cards contained within the deck. Accessible by subclasses.
     */
    protected final ArrayList<Card> cards;

    /**
     * Constructs a new Deck, validating the name.
     * This constructor is called by subclasses.
     * @param name The name for the deck. Cannot be null or blank.
     * @throws IllegalArgumentException if the name is invalid.
     */
    public Deck(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Deck name cannot be null or blank.");

        this.name = name.trim();
        this.cards = new ArrayList<>();
    }

    /**
     * Gets the name of the deck.
     * @return The non-null, trimmed name of the deck.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns a defensive copy of the list of cards in this deck to protect the internal state.
     * @return A new ArrayList containing the cards currently in this deck.
     */
    public ArrayList<Card> getCards() {
        return new ArrayList<>(this.cards);
    }

    /**
     * Gets the current number of cards in the deck.
     * @return The integer count of cards.
     */
    public int getCardCount() {
        return this.cards.size();
    }

    /**
     * Checks if the deck has reached its maximum capacity.
     * @return true if the number of cards is equal to or greater than MAX_CAPACITY, false otherwise.
     */
    public boolean isFull() {
        return this.cards.size() >= MAX_CAPACITY;
    }

    /**

     * Checks if a card with the given name is already in the deck (case-insensitive).
     * @param cardName The name of the card to check.
     * @return true if a card with that name exists, false otherwise.
     */
    public boolean containsCard(String cardName) {
        for (Card card : cards)
            if (card.getName().equalsIgnoreCase(cardName))
                return true;

        return false;
    }

    /**
     * Adds a card to the deck if it is not full and does not already contain a card with the same name.
     * @param card The Card object to add.
     * @return true if the card was successfully added, false otherwise.
     */
    public boolean addCard(Card card) {
        if (!isFull() && !containsCard(card.getName())) {
            this.cards.add(card);
            return true;
        }

        return false;
    }

    /**
     * Removes a card from a specific index in the deck.
     * @param index The zero-based index of the card to remove.
     * @return The removed Card object if the index was valid, or null if the index was out of bounds.
     */
    public Card removeCard(int index) {
        if (index >= 0 && index < this.cards.size())
            return this.cards.remove(index);

        return null;
    }

    /**
     * An abstract method that must be implemented by subclasses to define whether the
     * deck can be sold as a whole.
     * @return true if the deck is sellable, false otherwise.
     */
    public abstract boolean isSellable();
}
