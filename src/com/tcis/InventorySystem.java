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
 * <p>The GUI layer interacts exclusively with this class, which then delegates
 * requests to the appropriate manager (CollectionManager, BinderManager,
 * DeckManager). This decouples the UI from the backend's internal
 * implementation and manages the application's overall state, such as the
 * player's total money.</p>
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
    public boolean sellCardFromCollection(String cardName) {
        Card card = collectionManager.findCard(cardName);
        if (card != null && collectionManager.sellCard(cardName)) {
            this.totalMoney += card.getCalculatedValue();
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

    // --- Delegation Methods ---
    // The following methods act as a clean pass-through to the appropriate
    // manager, hiding the backend complexity from the GUI.

    public boolean createBinder(String name, String type) {
        return binderManager.createBinder(name, type);
    }
    public boolean deleteBinder(String name) {
        return binderManager.deleteBinder(name);
    }
    public ArrayList<Binder> getBinders() {
        return binderManager.getBinders();
    }
    public Binder findBinder(String name) {
        return binderManager.findBinder(name);
    }
    public int addCardToBinder(String c, String b) {
        return binderManager.addCardToBinder(c, b);
    }
    public boolean removeCardFromBinder(int i, String n) {
        return binderManager.removeCardFromBinder(i, n);
    }
    public boolean performTrade(String b, int i, Card c) {
        return binderManager.performTrade(b, i, c);
    }
    
    public boolean createDeck(String name, String type) {
        return deckManager.createDeck(name, type);
    }
    public boolean deleteDeck(String name) {
        return deckManager.deleteDeck(name);
    }
    public ArrayList<Deck> getDecks() {
        return deckManager.getDecks();
    }
    public Deck findDeck(String name) {
        return deckManager.findDeck(name);
    }
    public int addCardToDeck(String c, String d) {
        return deckManager.addCardToDeck(c, d);
    }
    public boolean removeCardFromDeck(int i, String n) {
        return deckManager.removeCardFromDeck(i, n);
    }
    
    public boolean addNewCard(String n, double v, Rarity r, Variant t) {
        return collectionManager.addNewCard(n, v, r, t);
    }
    public Card findCard(String name) {
        return collectionManager.findCard(name);
    }
    public boolean increaseCardCount(String name, int amount) {
        return collectionManager.increaseCount(name, amount);
    }
    public boolean decreaseCardCount(String name, int amount) {
        return collectionManager.decreaseCount(name, amount);
    }
    public ArrayList<Card> getCardTypes() {
        return collectionManager.getCardTypes();
    }
    public HashMap<String, Integer> getCardCounts() {
        return collectionManager.getCardCounts();
    }
    public boolean isCardAvailable(String name) {
        return collectionManager.isCardAvailable(name);
    }
}
