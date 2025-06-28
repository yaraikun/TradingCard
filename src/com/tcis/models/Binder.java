package com.tcis.models;

import java.util.ArrayList;

/*
    Represents a binder that can hold up to a fixed number of cards,
    including duplicates. This class encapsulates the properties and behaviors
    of a physical trading card binder, such as its name, capacity, and the
    cards it contains. The constructor ensures that every binder must have a
    valid, non-blank name upon creation.
*/
public class Binder {

    /*
        A public constant representing the maximum number of cards a binder can hold.
    */
    public static final int MAX_CAPACITY = 20;

    /*
        The name of the binder, which is immutable after creation.
    */
    private final String name;

    /*
        The internal list of cards contained within the binder.
    */
    private final ArrayList<Card> cards;

    /*
        Method: Binder Constructor

        Purpose: Constructs a new Binder with a specified name. The name is
        validated to ensure it is not null or empty.
        @param name: The name for the binder. Cannot be null or blank.
        @throws IllegalArgumentException: if the provided name is null, empty,
                                          or only contains whitespace.
    */
    public Binder(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Binder name cannot be null or blank.");
        }
        this.name = name.trim();
        this.cards = new ArrayList<>();
    }

    /*
        Method: Get Name
        Purpose: Gets the name of the binder.
        Returns: The non-null, trimmed name of the binder.
    */
    public String getName() {
        return name;
    }

    /*
        Method: Get Cards

        Purpose: Returns a defensive copy of the list of cards in this binder.
        This prevents external classes from modifying the internal list 
        directly, ensuring the integrity of the binder's state.
     
        Returns: A new ArrayList containing the cards currently in this binder.
    */
    public ArrayList<Card> getCards() {
        return new ArrayList<>(cards);
    }

    /*
        Method: Get Card Count

        Purpose: Gets the current number of cards in the binder.

        Returns: The integer count of cards.
    */
    public int getCardCount() {
        return cards.size();
    }

    /*
        Method: Is Full

        Purpose: Checks if the binder has reached its maximum capacity.
     
        Returns: true if the number of cards is equal to or greater than
                 MAX_CAPACITY, false otherwise.
     */
    public boolean isFull() {
        return cards.size() >= MAX_CAPACITY;
    }

    /*
        Method: Add Card

        Purpose: Adds a card to the binder if there is available space.
     
        Returns: true if the card was successfully added, false if the binder
        was full.

        @param card: The Card object to add. Should not be null.
    */
    public boolean addCard(Card card) {
        if (!isFull()) {
            cards.add(card);
            return true;
        }
        return false;
    }

    /*
        Method: Remove Card

        Purpose: Removes a card from a specific index in the binder.
     
        Returns: The removed Card object if the index was valid, or null if the
        index was out of bounds.

        @param: index The zero-based index of the card to remove.
    */
    public Card removeCard(int index) {
        if (index >= 0 && index < cards.size()) {
            return cards.remove(index);
        }
        return null;
    }
}
