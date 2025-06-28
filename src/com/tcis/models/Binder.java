package com.tcis.models;

import java.util.ArrayList;

/**
 * Represents a binder that can hold up to a fixed number of cards, including duplicates.
 * Its constructor ensures that every binder must have a valid name.
 */
public class Binder {
    public static final int MAX_CAPACITY = 20;
    private final String name;
    private final ArrayList<Card> cards;

    /**
     * Constructs a new Binder, validating the name.
     * @param name The name for the binder. Cannot be null or blank.
     * @throws IllegalArgumentException if the name is invalid.
     */
    public Binder(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Binder name cannot be null or blank.");
        }
        this.name = name.trim();
        this.cards = new ArrayList<>();
    }

    public String getName() { return name; }

    /**
     * Returns a defensive copy of the cards list to protect the internal state.
     * @return A new ArrayList containing the cards in this binder.
     */
    public ArrayList<Card> getCards() { return new ArrayList<>(cards); }
    public int getCardCount() { return cards.size(); }
    public boolean isFull() { return cards.size() >= MAX_CAPACITY; }

    /**
     * Adds a card to the binder if it is not full.
     * @param card The Card object to add.
     * @return true if the card was added, false if the binder was full.
     */
    public boolean addCard(Card card) {
        if (!isFull()) {
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
