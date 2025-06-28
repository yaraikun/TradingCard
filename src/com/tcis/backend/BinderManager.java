package com.tcis.backend;

import com.tcis.models.Binder;
import com.tcis.models.Card;
import java.util.ArrayList;

/**
 * Manages the lifecycle and contents of all Binder objects.
 * It handles the logic for creating, deleting, and modifying binders,
 * and orchestrates the movement of cards between binders and the main collection.
 */
public class BinderManager {
    private final ArrayList<Binder> binders;
    private final Collection collection;

    /**
     * Constructs a new BinderManager.
     * @param collection The central Collection object that this manager will interact with.
     */
    public BinderManager(Collection collection) {
        this.binders = new ArrayList<>();
        this.collection = collection;
    }

    /**
     * Finds a binder by its name (case-insensitive).
     * @param name The name of the binder to find.
     * @return The Binder object if found, otherwise null.
     */
    public Binder findBinder(String name) {
        if (name == null) return null;
        for (Binder binder : binders) {
            if (binder.getName().equalsIgnoreCase(name.trim())) {
                return binder;
            }
        }
        return null;
    }

    /**
     * Creates a new, empty binder with the given name.
     * Fails if a binder with the same name already exists.
     * @param name The name for the new binder.
     * @return true if the binder was created successfully, false otherwise.
     * @throws IllegalArgumentException if the name is invalid (handled by Binder constructor).
     */
    public boolean createBinder(String name) {
        if (findBinder(name) != null) return false;
        binders.add(new Binder(name));
        return true;
    }
    
    /**
     * Deletes a binder and returns all its cards to the main collection.
     * @param name The name of the binder to delete.
     * @return true if the binder was found and deleted, false otherwise.
     */
    public boolean deleteBinder(String name) {
        Binder binderToDelete = findBinder(name);
        if (binderToDelete == null) return false;

        for (Card card : binderToDelete.getCards()) {
            collection.increaseCount(card.getName(), 1);
        }
        
        binders.remove(binderToDelete);
        return true;
    }

    /**
     * Moves a card from the main collection to a specified binder.
     * @param cardName The name of the card to move.
     * @param binderName The name of the target binder.
     * @return An integer status code: 0 for success, 1 for not found, 2 for no copies, 3 for binder full.
     */
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

    /**
     * Removes a card from a binder at a specific index and returns it to the main collection.
     * @param cardIndex The index of the card to remove from the binder's list.
     * @param binderName The name of the binder.
     * @return true if the removal was successful, false otherwise.
     */
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

    /**
     * Executes a 1-for-1 card trade. The outgoing card is removed permanently,
     * and the incoming card is added to the binder.
     * @param binderName The name of the binder where the trade occurs.
     * @param outgoingCardIndex The index of the card being given up.
     * @param incomingCard The new Card object being received.
     * @return true if the trade was successful, false otherwise.
     */
    public boolean performTrade(String binderName, int outgoingCardIndex, Card incomingCard) {
        Binder binder = findBinder(binderName);
        if (binder == null || incomingCard == null) return false;

        Card outgoingCard = binder.removeCard(outgoingCardIndex);
        if (outgoingCard == null) return false;

        // If the incoming card is a new type, add it to the master list first.
        if (collection.findCard(incomingCard.getName()) == null) {
            collection.addCardType(incomingCard);
            // Immediately decrease its count, as it's going into the binder, not the "available" pool.
            collection.decreaseCount(incomingCard.getName(), 1);
        }
        
        binder.addCard(incomingCard);
        return true;
    }

    /**
     * Gets a defensive copy of the list of all binders.
     * @return A new ArrayList containing all Binder objects.
     */
    public ArrayList<Binder> getBinders() {
        return new ArrayList<>(binders);
    }
}
