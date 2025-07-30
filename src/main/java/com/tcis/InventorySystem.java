package com.tcis;

import java.util.ArrayList;
import java.util.HashMap;

import com.tcis.backend.BinderManager;
import com.tcis.backend.CollectionManager;
import com.tcis.backend.DeckManager;
import com.tcis.gui.main.MainFrame;
import com.tcis.models.binder.Binder;
import com.tcis.models.card.Card;
import com.tcis.models.card.Rarity;
import com.tcis.models.card.Variant;
import com.tcis.models.deck.Deck;

/**
 * Acts as a Facade for the entire backend system and the main controller for
 * the application.
 *
 * <p>
 * The GUI layer interacts exclusively with this class, which then delegates
 * requests to the appropriate manager (CollectionManager, BinderManager,
 * DeckManager). This decouples the UI from the backend's internal
 * implementation and manages the application's overall state, such as the
 * player's total money.
 * </p>
 */
public class InventorySystem {
    /**
     * The total amount of money the player has accumulated from selling cards,
     * binders, or decks.
     */
    private double totalMoney;

    /**
     * The single instance of the CollectionManager, which handles all logic
     * related to the main card collection.
     */
    private final CollectionManager collectionManager;

    /**
     * The single instance of the BinderManager, which handles all logic for
     * binders.
     */
    private final BinderManager binderManager;

    /**
     * The single instance of the DeckManager, which handles all logic for
     * decks.
     */
    private final DeckManager deckManager;

    /**
     * Constructs the InventorySystem, initializing all backend components and
     * setting the initial money to zero. This creates the entire object graph
     * for the application's backend.
     */
    public InventorySystem() {
        this.totalMoney = 0.0;
        this.collectionManager = new CollectionManager();
        this.binderManager = new BinderManager(this.collectionManager);
        this.deckManager = new DeckManager(this.collectionManager);
    }

    /**
     * Starts the application by creating and showing the main GUI Frame.
     * This is the entry point for the user-facing part of the application,
     * called from the {@code Main} class.
     */
    public void run() {
        MainFrame mainFrame = new MainFrame(this);
        mainFrame.setVisible(true);
    }

    /**
     * Gets the current total money of the player.
     *
     * @return a double representing the player's total money.
     */
    public double getTotalMoney() {
        return this.totalMoney;
    }

    /**
     * Sells a single card from the main collection. If successful, the card's
     * real value is added to the player's total money.
     *
     * @param cardName The name of the card to sell.
     * @return true if the sale was successful, false otherwise.
     */
    public boolean sellCardFromCollection(String cardName, int amount) {
        Card card = collectionManager.findCard(cardName);

        if (card != null && collectionManager.sellCard(cardName, amount)) {
            this.totalMoney += card.getCalculatedValue() * amount;
            return true;
        }

        return false;
    }

    /**
     * Sells a sellable binder. If successful, the binder's calculated price is
     * added to the player's total money.
     *
     * @param binderName The name of the binder to sell.
     * @return true if the sale was successful, false otherwise.
     */
    public boolean sellBinder(String binderName) {
        double price = binderManager.sellBinder(binderName);

        if (price > 0) { // sellBinder returns <= 0 on failure
            this.totalMoney += price;
            return true;
        }

        return false;
    }

    /**
     * Sells a sellable deck. If successful, the deck's calculated price is
     * added to the player's total money.
     *
     * @param deckName The name of the deck to sell.
     * @return true if the sale was successful, false otherwise.
     */
    public boolean sellDeck(String deckName) {
        double price = deckManager.sellDeck(deckName);

        if (price > 0) { // sellDeck returns <= 0 on failure
            this.totalMoney += price;
            return true;
        }

        return false;
    }

    // --- Binder Delegation Methods ---

    /**
     * Delegates the request to create a new binder to the BinderManager.
     *
     * @param name The name for the new binder.
     * @param type The string representing the type of binder to create.
     * @return true if the binder was created successfully, false otherwise.
     */
    public boolean createBinder(String name, String type) {
        return binderManager.createBinder(name, type);
    }

    /**
     * Delegates the request to delete a binder to the BinderManager.
     *
     * @param name The name of the binder to delete.
     * @return true if the binder was found and deleted, false otherwise.
     */
    public boolean deleteBinder(String name) {
        return binderManager.deleteBinder(name);
    }

    /**
     * Delegates the request to get all binders to the BinderManager.
     *
     * @return A defensive copy of the list of all Binder objects.
     */
    public ArrayList<Binder> getBinders() {
        return binderManager.getBinders();
    }

    /**
     * Delegates the request to find a specific binder to the BinderManager.
     *
     * @param name The name of the binder to find.
     * @return The Binder object if found, otherwise null.
     */
    public Binder findBinder(String name) {
        return binderManager.findBinder(name);
    }

    /**
     * Delegates the request to add a card to a binder to the BinderManager.
     *
     * @param cardName   The name of the card to move.
     * @param binderName The name of the target binder.
     * @return An integer status code representing the outcome.
     */
    public int addCardToBinder(String cardName, String binderName) {
        return binderManager.addCardToBinder(cardName, binderName);
    }

    /**
     * Delegates the request to remove a card from a binder to the BinderManager.
     *
     * @param cardIndex  The index of the card to remove.
     * @param binderName The name of the binder.
     * @return true if the removal was successful, false otherwise.
     */
    public boolean removeCardFromBinder(int cardIndex, String binderName) {
        return binderManager.removeCardFromBinder(cardIndex, binderName);
    }

    /**
     * Delegates a trade request to the BinderManager.
     *
     * @param binderName        The name of the binder where the trade occurs.
     * @param outgoingCardIndex The index of the card being given up.
     * @param incomingCard      The new Card object being received.
     * @return true if the trade was successful, false otherwise.
     */
    public boolean performTrade(String binderName, int outgoingCardIndex, Card incomingCard) {
        return binderManager.performTrade(binderName, outgoingCardIndex, incomingCard);
    }

    // --- Deck Delegation Methods ---

    /**
     * Delegates the request to create a new deck to the DeckManager.
     *
     * @param name The name for the new deck.
     * @param type The string representing the type of deck to create.
     * @return true if the deck was created successfully, false otherwise.
     */
    public boolean createDeck(String name, String type) {
        return deckManager.createDeck(name, type);
    }

    /**
     * Delegates the request to delete a deck to the DeckManager.
     *
     * @param name The name of the deck to delete.
     * @return true if the deck was found and deleted, false otherwise.
     */
    public boolean deleteDeck(String name) {
        return deckManager.deleteDeck(name);
    }

    /**
     * Delegates the request to get all decks to the DeckManager.
     *
     * @return A defensive copy of the list of all Deck objects.
     */
    public ArrayList<Deck> getDecks() {
        return deckManager.getDecks();
    }

    /**
     * Delegates the request to find a specific deck to the DeckManager.
     *
     * @param name The name of the deck to find.
     * @return The Deck object if found, otherwise null.
     */
    public Deck findDeck(String name) {
        return deckManager.findDeck(name);
    }

    /**
     * Delegates the request to add a card to a deck to the DeckManager.
     *
     * @param cardName The name of the card to move.
     * @param deckName The name of the target deck.
     * @return An integer status code representing the outcome.
     */
    public int addCardToDeck(String cardName, String deckName) {
        return deckManager.addCardToDeck(cardName, deckName);
    }

    /**
     * Delegates the request to remove a card from a deck to the DeckManager.
     *
     * @param cardIndex The index of the card to remove.
     * @param deckName  The name of the deck.
     * @return true if the removal was successful, false otherwise.
     */
    public boolean removeCardFromDeck(int cardIndex, String deckName) {
        return deckManager.removeCardFromDeck(cardIndex, deckName);
    }

    // --- Collection Delegation Methods ---

    /**
     * Delegates the request to add a new card type to the CollectionManager.
     *
     * @param name    The name for the new card.
     * @param value   The base value for the new card.
     * @param rarity  The rarity for the new card.
     * @param variant The variant for the new card.
     * @return true if the card was successfully added, false otherwise.
     */
    public boolean addNewCard(String name, double value, Rarity rarity, Variant variant) {
        return collectionManager.addNewCard(name, value, rarity, variant);
    }

    /**
     * Delegates the request to find a specific card type to the CollectionManager.
     *
     * @param name The name of the card to find.
     * @return The Card object if found, otherwise null.
     */
    public Card findCard(String name) {
        return collectionManager.findCard(name);
    }

    /**
     * Delegates the request to increase a card's count to the CollectionManager.
     *
     * @param name   The name of the card.
     * @param amount The positive integer amount to increase by.
     * @return true if the count was successfully updated, false otherwise.
     */
    public boolean increaseCardCount(String name, int amount) {
        return collectionManager.increaseCount(name, amount);
    }

    /**
     * Delegates the request to decrease a card's count to the CollectionManager.
     *
     * @param name   The name of the card.
     * @param amount The positive integer amount to decrease by.
     * @return true if the count was successfully updated, false otherwise.
     */
    public boolean decreaseCardCount(String name, int amount) {
        return collectionManager.decreaseCount(name, amount);
    }

    /**
     * Delegates the request to get all unique card types to the CollectionManager.
     *
     * @return A defensive copy of the list of all unique Card objects.
     */
    public ArrayList<Card> getCardTypes() {
        return collectionManager.getCardTypes();
    }

    /**
     * Delegates the request to get all card counts to the CollectionManager.
     *
     * @return A defensive copy of the map of card names to their counts.
     */
    public HashMap<String, Integer> getCardCounts() {
        return collectionManager.getCardCounts();
    }

    /**
     * Delegates the request to check if a card is available to the
     * CollectionManager.
     *
     * @param name The name of the card.
     * @return true if the card count is greater than 0, false otherwise.
     */
    public boolean isCardAvailable(String name) {
        return collectionManager.isCardAvailable(name);
    }
}
