package com.tcis.backend;

import com.tcis.models.Card;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manages the master list of all unique card types and their quantities.
 * This class acts as the single source of truth for the player's entire card pool.
 */
public class Collection {
    private final ArrayList<Card> cardTypes;
    private final HashMap<String, Integer> cardCounts;

    /**
     * Constructs a new, empty Collection.
     */
    public Collection() {
        this.cardTypes = new ArrayList<>();
        this.cardCounts = new HashMap<>();
    }

    /**
     * Finds a card type in the collection by its name (case-insensitive).
     * @param name The name of the card to find.
     * @return The Card object if found, otherwise null.
     */
    public Card findCard(String name) {
        if (name == null) return null;
        for (Card card : cardTypes) {
            if (card.getName().equalsIgnoreCase(name.trim())) {
                return card;
            }
        }
        return null;
    }

    /**
     * Adds a new, unique card type to the master list.
     * If a card with the same name already exists, the operation fails.
     * @param card The Card object to add. Must not be null.
     * @return true if the card was successfully added, false otherwise.
     */
    public boolean addCardType(Card card) {
        if (card == null || findCard(card.getName()) != null) {
            return false;
        }
        cardTypes.add(card);
        cardCounts.put(card.getName().toLowerCase(), 1);
        return true;
    }

    /**
     * Increases the count of an existing card.
     * @param name The name of the card.
     * @param amount The positive integer amount to increase by.
     * @return true if the count was successfully updated, false otherwise.
     */
    public boolean increaseCount(String name, int amount) {
        String key = name.toLowerCase().trim();
        if (findCard(name) == null || amount <= 0) return false;
        cardCounts.put(key, cardCounts.getOrDefault(key, 0) + amount);
        return true;
    }

    /**
     * Decreases the count of an existing card. Fails if the card doesn't exist,
     * the amount is not positive, or if it would result in a negative count.
     * @param name The name of the card.
     * @param amount The positive integer amount to decrease by.
     * @return true if the count was successfully updated, false otherwise.
     */
    public boolean decreaseCount(String name, int amount) {
        String key = name.toLowerCase().trim();
        if (findCard(name) == null || amount <= 0 || cardCounts.getOrDefault(key, 0) < amount) {
            return false;
        }
        cardCounts.put(key, cardCounts.get(key) - amount);
        return true;
    }

    /**
     * Checks if at least one copy of a card is available in the collection.
     * @param name The name of the card.
     * @return true if the card count is greater than 0, false otherwise.
     */
    public boolean isCardAvailable(String name) {
        if (name == null) return false;
        return cardCounts.getOrDefault(name.toLowerCase().trim(), 0) > 0;
    }

    /**
     * Gets a defensive copy of the list of unique card types.
     * @return A new ArrayList containing all unique Card objects.
     */
    public ArrayList<Card> getCardTypes() {
        return new ArrayList<>(cardTypes);
    }

    /**
     * Gets a defensive copy of the map of card names to their counts.
     * @return A new HashMap containing card names and their quantities.
     */
    public HashMap<String, Integer> getCardCounts() {
        return new HashMap<>(cardCounts);
    }
}
