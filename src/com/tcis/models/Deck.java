package com.tcis.models;

import java.util.ArrayList;

/**
 * Represents a deck that can hold up to a fixed number of unique cards.
 * Its constructor ensures that every deck must have a valid name.
 */
public class Deck {
    public static final int MAX_CAPACITY = 10;
    private final String name;
    private final ArrayList<Card> cards;

    /**
     * Constructs a new Deck, validating the name.
     * @param name The name for the deck. Cannot be null or blank.
     * @throws IllegalArgumentException if the name is invalid.
     */
    public Deck(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Deck name cannot be null or blank.");
        }
        this.name = name.trim();
        this.cards = new ArrayList<>();
    }

    public String getName() { return name; }
    
    /**
     * Returns a defensive copy of the cards list to protect the internal state.
     * @return A new ArrayList containing the cards in this deck.
     */
    public ArrayList<Card> getCards() { return new ArrayList<>(cards); }
    public int getCardCount() { return cards.size(); }
    public boolean isFull() { return cards.size() >= MAX_CAPACITY; }

    /**
     * Checks if a card with the given name is already in the deck. This check is case-insensitive.
     * @param cardName The name of the card to check.
     * @return true if a card with that name exists, false otherwise.
     */
    public boolean containsCard(String cardName) {
        for (Card card : cards) {
            if (card.getName().equalsIgnoreCase(cardName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a card to the deck if it is not full and does not already contain a card with the same name.
     * @param card The Card object to add.
     * @return true if the card was added, false otherwise.
     */
    public boolean addCard(Card card) {
        if (!isFull() && !containsCard(card.getName())) {
            cards.add(card);
            return true;
        }
        return false;
    }

    /**
     * Removes a card from a specific index.
     * @param index The index of the card to remove.
     * @return The removed Card object, or null if the index was invalid.
     */
    public Card removeCard(int index) {
        if (index >= 0 && index < cards.size()) {
            return cards.remove(index);
        }
        return null;
    }
}
