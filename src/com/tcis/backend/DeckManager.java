package com.tcis.backend;

import com.tcis.models.Deck;
import com.tcis.models.Card;
import java.util.ArrayList;

public class DeckManager {
    private final ArrayList<Deck> decks;
    private final Collection collection;

    public DeckManager(Collection collection) {
        this.decks = new ArrayList<>();
        this.collection = collection;
    }

    public Deck findDeck(String name) {
        if (name == null) return null;
        for (Deck deck : decks) {
            if (deck.getName().equalsIgnoreCase(name.trim())) {
                return deck;
            }
        }
        return null;
    }

    public boolean createDeck(String name) {
        if (findDeck(name) != null) return false;
        decks.add(new Deck(name)); // Can throw IllegalArgumentException
        return true;
    }
    
    public boolean deleteDeck(String name) {
        Deck deckToDelete = findDeck(name);
        if (deckToDelete == null) return false;
        
        for (Card card : deckToDelete.getCards()) {
            collection.increaseCount(card.getName(), 1);
        }
        
        decks.remove(deckToDelete);
        return true;
    }
    
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
    
    public ArrayList<Deck> getDecks() {
        return new ArrayList<>(decks);
    }
}
