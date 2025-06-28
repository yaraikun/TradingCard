package com.tcis.backend;

import com.tcis.models.Deck;
import com.tcis.models.Card;
import java.util.ArrayList;

/**
 * Manages the lifecycle and contents of all Deck objects.
 * It handles logic for creating, deleting, and modifying decks, ensuring
 * rules like capacity and card uniqueness are followed.
 */
public class DeckManager {
    private final ArrayList<Deck> decks;
    private final Collection collection;

    /**
     * Constructs a new DeckManager.
     * @param collection The central Collection object that this manager will interact with.
     */
    public DeckManager(Collection collection) {
        this.decks = new ArrayList<>();
        this.collection = collection;
    }

    /**
     * Finds a deck by its name (case-insensitive).
     * @param name The name of the deck to find.
     * @return The Deck object if found, otherwise null.
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

    /**
     * Creates a new, empty deck with the given name.
     * Fails if a deck with the same name already exists.
     * @param name The name for the new deck.
     * @return true if the deck was created successfully, false otherwise.
     * @throws IllegalArgumentException if the name is invalid (handled by Deck constructor).
     */
    public boolean createDeck(String name) {
        if (findDeck(name) != null) return false;
        decks.add(new Deck(name));
        return true;
    }
    
    /**
     * Deletes a deck and returns all its cards to the main collection.
     * @param name The name of the deck to delete.
     * @return true if the deck was found and deleted, false otherwise.
     */
    public boolean deleteDeck(String name) {
        Deck deckToDelete = findDeck(name);
        if (deckToDelete == null) return false;
        
        for (Card card : deckToDelete.getCards()) {
            collection.increaseCount(card.getName(), 1);
        }
        
        decks.remove(deckToDelete);
        return true;
    }
    
    /**
     * Moves a card from the main collection to a specified deck.
     * @param cardName The name of the card to move.
     * @param deckName The name of the target deck.
     * @return An integer status code: 0 for success, 1 for not found, 2 for no copies, 3 for deck full, 4 for duplicate card.
     */
    public int addCardToDeck(String cardName, String deckName) {
        Deck deck = findDeck(deckName);
        Card card = collection.findCard(cardName);

        if (deck == null || card == null) return 1;
        if (!collection.isCardAvailable(cardName)) return 2;
        if (deck.isFull()) return 3;
        if (deck.containsCard(cardName)) return 4;

        collection.decreaseCount(cardName, 1);
        deck.addCard(card);
        return 0;
    }
    
    /**
     * Removes a card from a deck at a specific index and returns it to the main collection.
     * @param cardIndex The index of the card to remove from the deck's list.
     * @param deckName The name of the deck.
     * @return true if the removal was successful, false otherwise.
     */
    public boolean removeCardFromDeck(int cardIndex, String deckName) {
        Deck deck = findDeck(deckName);
        if (deck == null) return false;

        Card removedCard = deck.removeCard(cardIndex);
        if (removedCard != null) {
            collection.increaseCount(removedCard.getName(), 1);
            return true;
        }
        return false;
    }
    
    /**
     * Gets a defensive copy of the list of all decks.
     * @return A new ArrayList containing all Deck objects.
     */
    public ArrayList<Deck> getDecks() {
        return new ArrayList<>(decks);
    }
}
