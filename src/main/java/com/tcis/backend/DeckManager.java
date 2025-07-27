package com.tcis.backend;

import java.util.ArrayList;

import com.tcis.models.card.Card;
import com.tcis.models.deck.Deck;
import com.tcis.models.deck.NormalDeck;
import com.tcis.models.deck.SellableDeck;

/**
 * Manages the lifecycle and contents of all Deck objects.
 *
 * <p>
 * It handles logic for creating, deleting, and modifying different types of
 * decks, ensuring rules like capacity and card uniqueness are followed. It
 * leverages polymorphism to handle different deck types seamlessly.
 * </p>
 */
public class DeckManager {
    /**
     * The list of all decks managed by the system. It holds the abstract Deck
     * superclass to allow for polymorphic behavior between Normal and Sellable
     * decks.
     */
    private final ArrayList<Deck> decks;

    /**
     * A reference to the central CollectionManager, required for updating card
     * counts when cards are moved or decks are deleted/sold.
     */
    private final CollectionManager collectionManager;

    /**
     * Constructs a new DeckManager.
     *
     * @param collectionManager The central CollectionManager that this manager
     *                          will depend on for all card inventory
     *                          operations.
     */
    public DeckManager(CollectionManager collectionManager) {
        this.decks = new ArrayList<>();
        this.collectionManager = collectionManager;
    }

    /**
     * Finds a deck by its name (case-insensitive).
     *
     * @param name The name of the deck to find.
     * @return The Deck object if found, otherwise null.
     */
    public Deck findDeck(String name) {
        if (name == null)
            return null;

        for (Deck deck : decks)
            if (deck.getName().equalsIgnoreCase(name.trim()))
                return deck;

        return null;
    }

    /**
     * Creates a new, empty deck of a specific type.
     *
     * <p>
     * This method acts as a factory, instantiating the correct Deck
     * subclass. Fails if a deck with the same name already exists.
     * </p>
     *
     * @param name The name for the new deck.
     * @param type The string representing the type of deck to create
     *             ("Normal" or "Sellable").
     * @return true if the deck was created successfully, false otherwise.
     */
    public boolean createDeck(String name, String type) {
        if (findDeck(name) != null) {
            System.out.println("Error: A deck with this name already exists.");
            return false;
        }
        try {
            Deck newDeck;
            switch (type.toLowerCase().trim()) {
                case "normal":
                    newDeck = new NormalDeck(name);
                    break;
                case "sellable":
                    newDeck = new SellableDeck(name);
                    break;
                default:
                    System.out.println("Error: Unknown deck type '" + type + "'.");
                    return false;
            }
            decks.add(newDeck);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating deck: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes a deck and returns all its cards to the main collection.
     *
     * @param name The name of the deck to delete.
     * @return true if the deck was found and deleted, false otherwise.
     */
    public boolean deleteDeck(String name) {
        Deck deckToDelete = findDeck(name);
        if (deckToDelete == null) {
            System.out.println("Error: Deck not found.");
            return false;
        }

        for (Card card : deckToDelete.getCards())
            collectionManager.increaseCount(card.getName(), 1);

        return decks.remove(deckToDelete);
    }

    /**
     * Sells a deck if it is sellable.
     *
     * <p>
     * The sale price is the sum of the real values of all cards inside.
     * When sold, the deck and its cards are removed permanently.
     * </p>
     *
     * @param name The name of the deck to sell.
     * @return The calculated sale price if sold successfully. Returns a value
     *         less than or equal to 0 on failure.
     */
    public double sellDeck(String name) {
        Deck deckToSell = findDeck(name);
        if (deckToSell == null) {
            System.out.println("Error: Deck not found.");
            return 0.0;
        }

        if (!deckToSell.isSellable()) {
            System.out.println("Error: This deck type ('" +
                    deckToSell.getClass().getSimpleName() + "') cannot be sold.");
            return 0.0;
        }

        double totalPrice = 0.0;
        for (Card card : deckToSell.getCards())
            totalPrice += card.getCalculatedValue();

        decks.remove(deckToSell);
        return totalPrice;
    }

    /**
     * Moves a card from the main collection to a specified deck.
     *
     * @param cardName The name of the card to move.
     * @param deckName The name of the target deck.
     * @return An integer status code: 0 for success, 1 for not found, 2 for
     *         no copies, 3 for deck full, 4 for duplicate card.
     */
    public int addCardToDeck(String cardName, String deckName) {
        Deck deck = findDeck(deckName);
        Card card = collectionManager.findCard(cardName);

        if (deck == null || card == null)
            return 1;

        if (!collectionManager.isCardAvailable(cardName))
            return 2;

        if (!deck.addCard(card)) {
            if (deck.isFull())
                return 3;
            else
                return 4;
        }

        collectionManager.decreaseCount(card.getName(), 1);
        return 0;
    }

    /**
     * Removes a card from a deck at a specific index and returns it to the
     * main collection.
     *
     * @param cardIndex The index of the card to remove from the deck's list.
     * @param deckName  The name of the deck.
     * @return true if the removal was successful, false otherwise.
     */
    public boolean removeCardFromDeck(int cardIndex, String deckName) {
        Deck deck = findDeck(deckName);
        if (deck == null)
            return false;

        Card removedCard = deck.removeCard(cardIndex);
        if (removedCard != null) {
            collectionManager.increaseCount(removedCard.getName(), 1);
            return true;
        }

        return false;
    }

    /**
     * Gets a defensive copy of the list of all decks.
     *
     * @return A new ArrayList containing all Deck objects.
     */
    public ArrayList<Deck> getDecks() {
        return new ArrayList<>(decks);
    }
}
