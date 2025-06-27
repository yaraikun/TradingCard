package com.tcis.backend;

import com.tcis.models.Binder;
import com.tcis.models.Card;
import java.util.ArrayList;

public class BinderManager {
    private final ArrayList<Binder> binders;
    private final Collection collection;

    public BinderManager(Collection collection) {
        this.binders = new ArrayList<>();
        this.collection = collection;
    }

    public Binder findBinder(String name) {
        if (name == null) return null;
        for (Binder binder : binders) {
            if (binder.getName().equalsIgnoreCase(name.trim())) {
                return binder;
            }
        }
        return null;
    }

    public boolean createBinder(String name) {
        if (findBinder(name) != null) return false;
        binders.add(new Binder(name)); // Can throw IllegalArgumentException
        return true;
    }
    
    public boolean deleteBinder(String name) {
        Binder binderToDelete = findBinder(name);
        if (binderToDelete == null) return false;

        for (Card card : binderToDelete.getCards()) {
            collection.increaseCount(card.getName(), 1);
        }
        
        binders.remove(binderToDelete);
        return true;
    }

    public int addCardToBinder(String cardName, String binderName) {
        Binder binder = findBinder(binderName);
        Card card = collection.findCard(cardName);

        if (binder == null || card == null) return 1;
        if (!collection.isCardAvailable(cardName)) return 2;
        if (binder.isFull()) return 3;

        collection.decreaseCount(cardName, 1);
        binder.addCard(card);
        return 0;
    }

    public boolean removeCardFromBinder(int cardIndex, String binderName) {
        Binder binder = findBinder(binderName);
        if (binder == null) return false;

        Card removedCard = binder.removeCard(cardIndex);
        if (removedCard != null) {
            collection.increaseCount(removedCard.getName(), 1);
            return true;
        }
        return false;
    }

    public boolean performTrade(String binderName, int outgoingCardIndex, Card incomingCard) {
        Binder binder = findBinder(binderName);
        if (binder == null || incomingCard == null) return false;

        Card outgoingCard = binder.removeCard(outgoingCardIndex);
        if (outgoingCard == null) return false;

        if (collection.findCard(incomingCard.getName()) == null) {
            collection.addCardType(incomingCard);
            collection.decreaseCount(incomingCard.getName(), 1);
        }
        
        binder.addCard(incomingCard);
        return true;
    }

    public ArrayList<Binder> getBinders() {
        return new ArrayList<>(binders);
    }
}
