package com.tcis.backend;

import com.tcis.models.Binder;
import com.tcis.models.Card;
import java.util.ArrayList;

/*
    Class Name: BinderManager

    Purpose: Manages the lifecycle and contents of all Binder objects. It
    handles the logic for creating, deleting, and modifying binders, and
    orchestrates the movement of cards between binders and the main collection.
*/
public class BinderManager {
    private final ArrayList<Binder> binders;
    private final CollectionManager collectionManager;

    /*
        Method: BinderManager

        Purpose: Constructs a new BinderManager.

        @param collectionManager The central CollectionManager that this
        manager will interact with.
    */
    public BinderManager(CollectionManager collectionManager) {
        this.binders = new ArrayList<>();
        this.collectionManager = collectionManager;
    }

    /*
        Method: findBinder

        Purpose: Finds a binder by its name (case-insensitive).

        Returns: The Binder object if found, otherwise null.

        @param name: The name of the binder to find.
    */
    public Binder findBinder(String name) {
        if (name == null)
            return null;

        for (Binder binder : binders)
            if (binder.getName().equalsIgnoreCase(name.trim()))
                return binder;

        return null;
    }

    /*
        Method: createBinder

        Purpose: Creates a new, empty binder with the given name.
        Fails if a binder with the same name already exists.

        Returns: true if the binder was created successfully, false otherwise.

        @param name: The name for the new binder.
    */
    public boolean createBinder(String name) {
        if (findBinder(name) != null) {
            System.out.println("Error: A binder with this name already exists.");
            return false;
        }
        try {
            binders.add(new Binder(name));
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating binder: " + e.getMessage());
            return false;
        }
    }
    
    /*
          Purpose: Deletes a binder and returns all its cards to the main
          collection.
    
          Return: true if the binder was found and deleted, false otherwise.
    
          @param name: The name of the binder to delete.
    */
    public boolean deleteBinder(String name) {
        Binder binderToDelete = findBinder(name);
        if (binderToDelete == null) {
            System.out.println("Error: Binder not found.");
            return false;
        }

        for (Card card : binderToDelete.getCards()) {
            collectionManager.increaseCount(card.getName(), 1);
        }
        
        return binders.remove(binderToDelete);
    }

    /*
        Method: addCardToBinder

        Purpose: Moves a card from the main collection to a specified binder.

        Returns: 0 for success, 1 for not found, 2 for no copies, 3 for binder full.

        @param cardName: The name of the card to move.
        @param binderName: The name of the target binder.
    */
    public int addCardToBinder(String cardName, String binderName) {
        Binder binder = findBinder(binderName);
        Card card = collectionManager.findCard(cardName);

        if (binder == null || card == null) return 1;
        if (!collectionManager.isCardAvailable(cardName)) return 2;
        if (binder.isFull()) return 3;

        collectionManager.decreaseCount(cardName, 1);
        binder.addCard(card);
        return 0;
    }

    /*
        Method: removeCardFromBinder

        Purpose: Removes a card from a binder at a specific index and returns
        it to the main collection.

        Returns: true if the removal was successful, false otherwise.

        @param cardIndex: The index of the card to remove from the binder's list.
        @param binderName: The name of the binder.
    */
    public boolean removeCardFromBinder(int cardIndex, String binderName) {
        Binder binder = findBinder(binderName);
        if (binder == null) return false;

        Card removedCard = binder.removeCard(cardIndex);
        if (removedCard != null) {
            collectionManager.increaseCount(removedCard.getName(), 1);
            return true;
        }
        return false;
    }

    /*
        Method: performTrade

        Purpose: Executes a 1-for-1 card trade. The outgoing card is removed =
        permanently, and the incoming card is added to the binder.

        @return true if the trade was successful, false otherwise.

        @param binderName The name of the binder where the trade occurs.
        @param outgoingCardIndex The index of the card being given up.
        @param incomingCard The new Card object being received. Must be a
                            valid, pre-constructed card.
    */
    public boolean performTrade(String binderName, int outgoingCardIndex, Card incomingCard) {
        Binder binder = findBinder(binderName);
        if (binder == null || incomingCard == null) return false;

        Card outgoingCard = binder.removeCard(outgoingCardIndex);
        if (outgoingCard == null) return false;

        // If the incoming card is a new type, add it to the master list first.
        if (collectionManager.findCard(incomingCard.getName()) == null) {
            // Use the full add method which handles exceptions internally
            collectionManager.addNewCard(incomingCard.getName(), incomingCard.getBaseValue(), incomingCard.getRarity(), incomingCard.getVariant());
            // Immediately decrease its count, as it's going into the binder, not the "available" pool.
            collectionManager.decreaseCount(incomingCard.getName(), 1);
        }
        
        binder.addCard(incomingCard);
        return true;
    }

    /*
        Method: getBinders

        Purpose: Gets a defensive copy of the list of all binders.

        Returns: A new ArrayList containing all Binder objects.
    */
    public ArrayList<Binder> getBinders() {
        return new ArrayList<>(binders);
    }
}
