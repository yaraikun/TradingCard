package com.tcis.backend;

import com.tcis.models.Card;
import com.tcis.models.card.Rarity;
import com.tcis.models.card.Variant;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manages the master list of all unique card types and their quantities.
 * This class acts as the single source of truth for the player's entire card pool,
 * handling the core logic of card creation and inventory counts.
 */
public class CollectionManager {
    private final ArrayList<Card> cardTypes;
    private final HashMap<String, Integer> cardCounts;

    /**
     * Constructs a new, empty CollectionManager.
     */
    public CollectionManager() {
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
     * Creates and adds a new, unique card type to the master list.
     * This method is the primary entry point for introducing a new card into the system.
     * It will fail if a card with the same name already exists.
     * It relies on the Card constructor to throw an exception for invalid data.
     * @param name The name for the new card.
     * @param baseValue The base value for the new card.
     * @param rarity The rarity for the new card.
     * @param variant The variant for the new card.
     * @return true if the card was successfully created and added, false otherwise.
     */
    public boolean addNewCard(String name, double baseValue, Rarity rarity, Variant variant) {
        if (findCard(name) != null) {
            System.out.println("Error: A card with this name already exists.");
            return false;
        }
        try {
            Card newCard = new Card(name, baseValue, rarity, variant);
            cardTypes.add(newCard);
            cardCounts.put(name.toLowerCase(), 1); // Start with one copy
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating card: " + e.getMessage());
            return false;
        }
    }

    /**
     * Increases the count of an existing card.
     * @param name The name of the card.
     * @param amount The positive integer amount to increase by.
     * @return true if the count was successfully updated, false if the card doesn't exist or amount is invalid.
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
     * This is used to determine if a card can be moved to a binder or deck.
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
     * Gets the current count for a specific card.
     * @param name The name of the card.
     * @return The quantity of the card, or 0 if not found.
     */
    public int getCardCount(String name) {
        if (name == null) return 0;
        return cardCounts.getOrDefault(name.toLowerCase().trim(), 0);
    }
}
