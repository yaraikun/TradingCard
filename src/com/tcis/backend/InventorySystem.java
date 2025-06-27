package com.tcis.backend;

import com.tcis.models.*;
import java.util.ArrayList;
import java.util.HashMap;

public class InventorySystem {
    private final Collection collection;
    private final BinderManager binderManager;
    private final DeckManager deckManager;

    public InventorySystem() {
        this.collection = new Collection();
        this.binderManager = new BinderManager(this.collection);
        this.deckManager = new DeckManager(this.collection);
    }
    
    public boolean addCardToCollection(String name, double baseValue, CardRarity rarity, CardVariant variant) {
        try {
            Card card = new Card(name, baseValue, rarity, variant);
            return collection.addCardType(card);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    public Card findCard(String name) { return collection.findCard(name); }
    public boolean increaseCardCount(String name, int amount) { return collection.increaseCount(name, amount); }
    public boolean decreaseCardCount(String name, int amount) { return collection.decreaseCount(name, amount); }
    public ArrayList<Card> getCardTypes() { return collection.getCardTypes(); }
    public HashMap<String, Integer> getCardCounts() { return collection.getCardCounts(); }

    public boolean createBinder(String name) {
        try { return binderManager.createBinder(name); } catch (IllegalArgumentException e) { System.out.println("Error: " + e.getMessage()); return false; }
    }
    public boolean deleteBinder(String name) { return binderManager.deleteBinder(name); }
    public int addCardToBinder(String c, String b) { return binderManager.addCardToBinder(c, b); }
    public boolean removeCardFromBinder(int i, String n) { return binderManager.removeCardFromBinder(i, n); }
    public boolean performTrade(String b, int i, Card c) { return binderManager.performTrade(b, i, c); }
    public ArrayList<Binder> getBinders() { return binderManager.getBinders(); }
    public Binder findBinder(String name) { return binderManager.findBinder(name); }

    public boolean createDeck(String name) {
        try { return deckManager.createDeck(name); } catch (IllegalArgumentException e) { System.out.println("Error: " + e.getMessage()); return false; }
    }
    public boolean deleteDeck(String name) { return deckManager.deleteDeck(name); }
    public int addCardToDeck(String c, String d) { return deckManager.addCardToDeck(c, d); }
    public boolean removeCardFromDeck(int i, String n) { return deckManager.removeCardFromDeck(i, n); }
    public ArrayList<Deck> getDecks() { return deckManager.getDecks(); }
    public Deck findDeck(String name) { return deckManager.findDeck(name); }
}
