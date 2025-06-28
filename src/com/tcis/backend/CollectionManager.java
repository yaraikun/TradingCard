package com.tcis.backend;

import com.tcis.models.Card;
import com.tcis.models.card.Rarity;
import com.tcis.models.card.Variant;
import java.util.ArrayList;
import java.util.HashMap;

/*
    Class: Collection Manager
    
    Purpose: Manages the master list of all unique card types and their
    quantities. This class acts as the single source of truth for the player's
    entire card pool, handling the core logic of card creation and inventory counts.
*/
public class CollectionManager {
    /*
        Attribute: Card Types
        
        Purpose: Stores a list of the unique Card objects that have been
        created. This list defines all possible cards in the system.
    */
    private final ArrayList<Card> cardTypes;

    /*
        Attribute: Card Counts
        
        Purpose: Stores the quantity of each card. The key is the card's name
        in lowercase, and the value is the integer count of how many the player
        owns.
    */
    private final HashMap<String, Integer> cardCounts;

    /*
        Constructor: Collection Manager
        
        Purpose: Initializes a new, empty CollectionManager by creating new
        instances of the ArrayList for card types and the HashMap for card
        counts.
    */
    public CollectionManager() {
        this.cardTypes = new ArrayList<>();
        this.cardCounts = new HashMap<>();
    }

    /*
        Method: Find Card
        
        Purpose: Finds a card type in the collection by its name. The search
        is case-insensitive and trims whitespace from the provided name.
        
        Returns: The Card object if a match is found; otherwise, returns null.
        
        @param name: The name of the card to find.
    */
    public Card findCard(String name) {
        for (Card card : cardTypes)
            if (card.getName().equalsIgnoreCase(name.trim()))
                return card;

        return null;
    }

    /*
        Method: Add New Card
        
        Purpose: Creates and adds a new, unique card type to the master list.
        This is the primary entry point for introducing a new card into the
        system. It will fail if a card with the same name already exists. It
        relies on the Card constructor to throw an exception for invalid data,
        which is caught and handled here.
        
        Returns: A boolean value: true if the card was successfully created and
        added, false otherwise (e.g., if the name already exists or constructor
        validation fails).
        
        @param name: The name for the new card.
        @param baseValue: The base dollar value for the new card.
        @param rarity: The Rarity enum constant for the new card.
        @param variant: The Variant enum constant for the new card.
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

    /*
        Method: Increase Count
        
        Purpose: Increases the count of an existing card in the collection.
        
        Returns: A boolean value: true if the count was successfully updated,
        false if the card doesn't exist or the amount is invalid (zero or
        negative).
        
        @param name: The name of the card whose count will be increased.
        @param amount: The positive integer amount to increase the count by.
    */
    public boolean increaseCount(String name, int amount) {
        String key = name.toLowerCase().trim();

        if (findCard(name) == null || amount <= 0)
            return false;

        cardCounts.put(key, cardCounts.getOrDefault(key, 0) + amount);
        return true;
    }

    /*
        Method: Decrease Count
        
        Purpose: Decreases the count of an existing card. This operation will
        fail if the card doesn't exist, the amount is not positive, or if the
        current count is less than the amount to be decreased.
        
        Returns: true if the count was successfully updated, false otherwise.
        
        @param name: The name of the card whose count will be decreased.
        @param amount: The positive integer amount to decrease the count by.
    */
    public boolean decreaseCount(String name, int amount) {
        String key = name.toLowerCase().trim();

        if (findCard(name) == null || amount <= 0 || cardCounts.getOrDefault(key, 0) < amount)
            return false;

        cardCounts.put(key, cardCounts.get(key) - amount);
        return true;
    }

    /*
        Method: Is Card Available
        
        Purpose: Checks if at least one copy of a card is available in the
        collection. This is used to determine if a card can be moved from the
        main collection to a binder
        or deck.
        
        Returns: true if the card count is greater than 0,
        false otherwise.
        
        @param name: The name of the card to check.
    */
    public boolean isCardAvailable(String name) {
        if (name == null)
            return false;

        return cardCounts.getOrDefault(name.toLowerCase().trim(), 0) > 0;
    }

    /*
        Method: Get Card Type
        
        Purpose: Gets a defensive copy of the list of unique card types. This
        prevents external classes from modifying the master list directly.
        
        Returns: A new ArrayList<Card> containing all unique Card objects.
    */
    public ArrayList<Card> getCardTypes() {
        return new ArrayList<>(this.cardTypes);
    }
    
    /*
        Method: Get Card Count
        
        Purpose: Gets the current inventory count for a specific card.
        
        Returns: An integer representing the quantity of the specified card, or
        0 if the card is not found in the collection.
        
        @param name: The name of the card whose count is being requested.
    */
    public int getCardCount(String name) {
        if (name == null)
            return 0;

        return cardCounts.getOrDefault(name.toLowerCase().trim(), 0);
    }
}
