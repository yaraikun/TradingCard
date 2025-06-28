package com.tcis.backend;

import com.tcis.models.Deck;
import com.tcis.models.Card;
import java.util.ArrayList;

/*
    Class Name: Deck Manager

    Purpose: Manages the lifecycle and contents of all Deck objects.
    It handles logic for creating, deleting, and modifying decks, ensuring
    rules like capacity and card uniqueness are followed.
*/
public class DeckManager {
    private final ArrayList<Deck> decks;
    private final CollectionManager collectionManager;

    /*
        Constructor: DeckManager

        Purpose: Constructs a new DeckManager.

        @param collectionManager: The central CollectionManager that this manager will interact with.
    */
    public DeckManager(CollectionManager collectionManager) {
        this.decks = new ArrayList<>();
        this.collectionManager = collectionManager;
    }

    /*
        Method: findDeck

        Purpose: Finds a deck by its name (case-insensitive).

        Returns: The Deck object if found, otherwise null.

        @param name: The name of the deck to find.
    */
    public Deck findDeck(String name) {
        if (name == null) return null;
        for (Deck deck : decks) {
            if (deck.getName().equalsIgnoreCase(name.trim())) {
                return deck;
            }
        }
        return null;
    }

    /*
        Method: createDeck
        Purpose: Creates a new, empty deck with the given name.
        Fails if a deck with the same name already exists.

        Returns:  true if the deck was created successfully, false otherwise.

        @param name: The name for the new deck.
    */
    public boolean createDeck(String name) {
        if (findDeck(name) != null) {
            System.out.println("Error: A deck with this name already exists.");
            return false;
        }
        try {
            decks.add(new Deck(name));
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating deck: " + e.getMessage());
            return false;
        }
    }
    
    /*
        Method: deleteDeck

        Purpose: Deletes a deck and returns all its cards to the main collection.

        Returns: true if the deck was found and deleted, false otherwise.

        @param name: The name of the deck to delete.
    */
    public boolean deleteDeck(String name) {
        Deck deckToDelete = findDeck(name);
        if (deckToDelete == null) {
            System.out.println("Error: Deck not found.");
            return false;
        }
        
        for (Card card : deckToDelete.getCards()) {
            collectionManager.increaseCount(card.getName(), 1);
        }
        
        return decks.remove(deckToDelete);
    }
    
    /*
        Purpose: Moves a card from the main collection to a specified deck.

        Returns: An integer status code: 0 for success, 1 for not found, 2 for no copies, 3 for deck full, 4 for duplicate card.

        @param cardName: The name of the card to move.
        @param deckName: The name of the target deck.
    */
    public int addCardToDeck(String cardName, String deckName) {
        Deck deck = findDeck(deckName);
        Card card = collectionManager.findCard(cardName);

        if (deck == null || card == null) return 1;
        if (!collectionManager.isCardAvailable(cardName)) return 2;
        if (deck.isFull()) return 3;
        if (deck.containsCard(cardName)) return 4;

        collectionManager.decreaseCount(cardName, 1);
        deck.addCard(card);
        return 0;
    }
    
    /*
        Method: removeCardFromDeck

        Removes a card from a deck at a specific index and returns it to the main collection.

        Returns: true if the removal was successful, false otherwise.

        @param cardIndex The index of the card to remove from the deck's list.
        @param deckName The name of the deck.
    */
    public boolean removeCardFromDeck(int cardIndex, String deckName) {
        Deck deck = findDeck(deckName);
        if (deck == null) return false;

        Card removedCard = deck.removeCard(cardIndex);
        if (removedCard != null) {
            collectionManager.increaseCount(removedCard.getName(), 1);
            return true;
        }
        return false;
    }
    
    /*
        Method: getDecks

        Purpose: Gets a defensive copy of the list of all decks.

        Returns: A new ArrayList containing all Deck objects.
    */
    public ArrayList<Deck> getDecks() {
        return new ArrayList<>(decks);
    }
}
