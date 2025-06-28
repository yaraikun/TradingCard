package com.tcis.models;

import java.util.ArrayList;

/**
 * Represents a binder that can hold up to a fixed number of cards.
 */
public class Binder {
    public static final int MAX_CAPACITY = 20;
    private final String name;
    private final ArrayList<Card> cards;

    public Binder(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Binder name cannot be null or blank.");
        }
        this.name = name.trim();
        this.cards = new ArrayList<>();
    }

    public String getName() { return name; }
    public ArrayList<Card> getCards() { return new ArrayList<>(cards); }
    public int getCardCount() { return cards.size(); }
    public boolean isFull() { return cards.size() >= MAX_CAPACITY; }

    public boolean addCard(Card card) {
        if (!isFull()) {
            cards.add(card);
            return true;
        }
        return false;
    }

    public Card removeCard(int index) {
        if (index >= 0 && index < cards.size()) {
            return cards.remove(index);
        }
        return null;
    }
}
