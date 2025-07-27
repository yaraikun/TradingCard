package com.tcis.backend;

import java.util.ArrayList;

import com.tcis.models.binder.Binder;
import com.tcis.models.binder.CollectorBinder;
import com.tcis.models.binder.LuxuryBinder;
import com.tcis.models.binder.NonCuratedBinder;
import com.tcis.models.binder.PauperBinder;
import com.tcis.models.binder.RaresBinder;
import com.tcis.models.card.Card;

/**
 * Manages the lifecycle and contents of all Binder objects.
 *
 * <p>
 * This class handles the logic for creating, deleting, and modifying
 * different types of binders, and orchestrates the movement of cards between
 * binders and the main collection based on MCO2 rules. It leverages
 * polymorphism to handle different binder types seamlessly.
 * </p>
 */
public class BinderManager {
    /**
     * The list of all binders managed by the system. It holds the abstract
     * Binder superclass to allow for polymorphic behavior among different
     * binder types.
     */
    private final ArrayList<Binder> binders;

    /**
     * A reference to the central CollectionManager, which is required for
     * updating card counts when cards are moved or binders are deleted.
     */
    private final CollectionManager collectionManager;

    /**
     * Constructs a new BinderManager.
     *
     * @param collectionManager The central CollectionManager that this manager
     *                          will depend on for all card inventory
     *                          operations.
     */
    public BinderManager(CollectionManager collectionManager) {
        this.binders = new ArrayList<>();
        this.collectionManager = collectionManager;
    }

    /**
     * Finds a binder by its name (case-insensitive).
     *
     * @param name The name of the binder to find.
     * @return The Binder object if found, otherwise null.
     */
    public Binder findBinder(String name) {
        if (name == null)
            return null;

        for (Binder binder : binders)
            if (binder.getName().equalsIgnoreCase(name.trim()))
                return binder;

        return null;
    }

    /**
     * Creates a new binder of a specific type.
     *
     * <p>
     * This method acts as a factory, instantiating the correct Binder
     * subclass based on user input. Fails if a binder with the same name
     * already exists.
     * </p>
     *
     * @param name The name for the new binder.
     * @param type The string representing the type of binder to create
     *             (e.g., "Pauper", "Collector").
     * @return true if the binder was created successfully, false otherwise.
     */
    public boolean createBinder(String name, String type) {
        if (findBinder(name) != null) {
            System.out.println("Error: A binder with this name already exists.");
            return false;
        }

        try {
            Binder newBinder;

            switch (type.toLowerCase().trim()) {
                case "non-curated":
                    newBinder = new NonCuratedBinder(name);
                    break;
                case "collector":
                    newBinder = new CollectorBinder(name);
                    break;
                case "pauper":
                    newBinder = new PauperBinder(name);
                    break;
                case "rares":
                    newBinder = new RaresBinder(name);
                    break;
                case "luxury":
                    newBinder = new LuxuryBinder(name);
                    break;
                default:
                    System.out.println("Error: Unknown binder type '" + type + "'.");
                    return false;
            }
            binders.add(newBinder);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating binder: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes a binder and returns all its cards to the main collection.
     * This action is for when a user simply wants to remove a binder, not
     * sell it.
     *
     * @param name The name of the binder to delete.
     * @return true if the binder was found and deleted, false otherwise.
     */
    public boolean deleteBinder(String name) {
        Binder binderToDelete = findBinder(name);

        if (binderToDelete == null) {
            System.out.println("Error: Binder not found.");
            return false;
        }

        for (Card card : binderToDelete.getCards())
            collectionManager.increaseCount(card.getName(), 1);

        return binders.remove(binderToDelete);
    }

    /**
     * Sells a binder if it is sellable.
     *
     * <p>
     * When sold, the binder and all cards within it are permanently removed
     * from the system. This method relies on polymorphism to call the correct
     * {@code isSellable()} and {@code calculatePrice()} methods of the
     * specific binder subclass.
     * </p>
     *
     * @param name The name of the binder to sell.
     * @return The calculated sale price of the binder if sold successfully.
     *         Returns a value less than or equal to 0 on failure (e.g., binder
     *         not found or not sellable), which the calling method should check 
     *         for.
     */
    public double sellBinder(String name) {
        Binder binderToSell = findBinder(name);

        if (binderToSell == null) {
            System.out.println("Error: Binder not found.");
            return 0.0;
        }

        if (!binderToSell.isSellable()) {
            System.out.println(
                    "Error: This binder type ('" + binderToSell.getClass().getSimpleName() + "') cannot be sold.");
            return 0.0;
        }

        double price = binderToSell.calculatePrice();
        binders.remove(binderToSell);
        return price;
    }

    /**
     * Moves a card from the main collection to a specified binder, respecting
     * the binder's specific rules.
     *
     * @param cardName   The name of the card to move.
     * @param binderName The name of the target binder.
     * @return An integer status code: 0 for success, 1 for card/binder not
     *         found, 2 for no copies available, 3 for binder is full, 4 for
     *         card violates binder's rules.
     */
    public int addCardToBinder(String cardName, String binderName) {
        Binder binder = findBinder(binderName);
        Card card = collectionManager.findCard(cardName);

        if (binder == null || card == null)
            return 1;

        if (!collectionManager.isCardAvailable(cardName))
            return 2;

        if (!binder.addCard(card)) {
            if (binder.isFull())
                return 3;
            else
                return 4;
        }

        collectionManager.decreaseCount(card.getName(), 1);
        return 0;
    }

    /**
     * Removes a card from a binder at a specific index and returns it to the
     * main collection.
     *
     * @param cardIndex  The index of the card to remove from the binder's list.
     * @param binderName The name of the binder.
     * @return true if the removal was successful, false otherwise.
     */
    public boolean removeCardFromBinder(int cardIndex, String binderName) {
        Binder binder = findBinder(binderName);
        if (binder == null)
            return false;

        Card removedCard = binder.removeCard(cardIndex);
        if (removedCard != null) {
            collectionManager.increaseCount(removedCard.getName(), 1);
            return true;
        }

        return false;
    }

    /**
     * Executes a 1-for-1 card trade if the binder type allows it.
     *
     * <p>
     * The outgoing card is removed permanently, and the incoming card is
     * added to the binder. This method uses polymorphism to check the trade
     * and add eligibility rules of the specific binder subclass.
     * </p>
     *
     * @param binderName        The name of the binder where the trade occurs.
     * @param outgoingCardIndex The index of the card being given up.
     * @param incomingCard      The new Card object being received. Must be a valid,
     *                          pre-constructed card.
     * @return true if the trade was successful, false otherwise.
     */
    public boolean performTrade(String binderName, int outgoingCardIndex, Card incomingCard) {
        Binder binder = findBinder(binderName);
        if (binder == null || incomingCard == null)
            return false;

        if (!binder.canTrade()) {
            System.out.println(
                    "Error: Cards cannot be traded from this type of binder.");
            return false;
        }

        if (!binder.canAddCard(incomingCard)) {
            System.out.println(
                    "Error: The incoming card does not meet the requirements for this binder.");
            return false;
        }

        Card outgoingCard = binder.removeCard(outgoingCardIndex);
        if (outgoingCard == null)
            return false;

        if (collectionManager.findCard(incomingCard.getName()) == null) {
            collectionManager.addNewCard(
                incomingCard.getName(), 
                incomingCard.getBaseValue(),
                incomingCard.getRarity(),
                incomingCard.getVariant()
            );

            collectionManager.decreaseCount(incomingCard.getName(), 1);
        }

        binder.addCard(incomingCard);
        return true;
    }

    /**
     * Gets a defensive copy of the list of all binders.
     *
     * @return A new ArrayList containing all Binder objects.
     */
    public ArrayList<Binder> getBinders() {
        return new ArrayList<>(binders);
    }
}
