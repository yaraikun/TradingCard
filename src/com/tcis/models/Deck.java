package com.tcis.models;

import java.util.ArrayList;

/*
    Class: Deck
    
    Purpose: Represents a deck, which is a specialized container for cards
    intended for gameplay. It enforces a maximum capacity and ensures that all
    cards within it are unique by name. The constructor validates the deck's
    name to ensure data integrity.
*/
public class Deck {
    
    /*
        Attribute: MAX_CAPACITY
        
        Purpose: A public constant representing the maximum number of cards a 
        deck can hold.
    */
    public static final int MAX_CAPACITY = 10;

    /*
        Attribute: name
        
        Purpose: The name of the deck, which is immutable after the object is
        created.
    */
    private final String name;

    /*
        Attribute: cards
        
        Purpose: The internal list of Card objects contained within the deck.
    */
    private final ArrayList<Card> cards;

    /*
        Constructor: Deck
        
        Purpose: Initializes a new Deck with a specified name. The name is
        validated to ensure it is not null, empty, or only contains whitespace.
        
        @param name: The name for the deck. Cannot be null or blank.
        @throws IllegalArgumentException: if the provided name is invalid.
    */
    public Deck(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Deck name cannot be null or blank.");

        this.name = name.trim();
        this.cards = new ArrayList<>();
    }

    /*
        Method: Get Name
        
        Purpose: Gets the name of the deck.
        
        Returns: The non-null, trimmed name of the deck as a String.
    */
    public String getName() {
        return name;
    }

    /*
        Method: Get Cards
        
        Purpose: Returns a defensive copy of the list of cards in this deck.
        This prevents external classes from modifying the internal list
        directly, ensuring the integrity of the deck's state.
        
        Returns: A new ArrayList<Card> containing the cards currently in this
        deck.
    */
    public ArrayList<Card> getCards() {
        return new ArrayList<>(cards);
    }

    /*
        Method: Get Card Count
        
        Purpose: Gets the current number of cards in the deck.
        
        Returns: The integer count of cards in the deck.
    */
    public int getCardCount() {
        return cards.size();
    }

    /*
        Method: isFull
        
        Purpose: Checks if the deck has reached its maximum capacity.
        
        Returns: true if the number of cards is equal to or
        greater than MAX_CAPACITY, false otherwise.
    */
    public boolean isFull() {
        return cards.size() >= MAX_CAPACITY;
    }

    /*
        Method: containsCard
        
        Purpose: Checks if a card with the given name is already in the deck.
        This check is case-insensitive.
        
        Returns: true if a card with that name exists, false otherwise.
        
        @param cardName: The name of the card to check for uniqueness.
    */
    public boolean containsCard(String cardName) {
        for (Card card : cards)
            if (card.getName().equalsIgnoreCase(cardName))
                return true;

        return false;
    }

    /*
        Method: addCard
        
        Purpose: Adds a card to the deck if it is not full and does not already
        contain a card with the same name.
        
        Returns: A boolean value: true if the card was successfully added,
        false otherwise.

        @param card: The Card object to add. Should not be null.
    */
    public boolean addCard(Card card) {
        if (!isFull() && !containsCard(card.getName())) {
            cards.add(card);
            return true;
        }
        return false;
    }

    /*
        Method: removeCard
        
        Purpose: Removes a card from a specific index in the deck.
        
        Returns: The removed Card object if the index was valid, or null if
        the index was out of bounds.
        
        @param index: The zero-based index of the card to remove.
    */
    public Card removeCard(int index) {
        if (index >= 0 && index < cards.size())
            return cards.remove(index);
        return null;
    }
}
