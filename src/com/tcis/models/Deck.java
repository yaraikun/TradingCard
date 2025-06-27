package com.tcis.models;

import java.util.ArrayList;

public class Deck {
    public static final int MAX_CAPACITY = 10;
    private final String name;
    private final ArrayList<Card> cards;

    public Deck(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Deck name cannot be null or blank.");
        }
        this.name = name.trim();
        this.cards = new ArrayList<>();
    }

    public String getName() { return name; }
    public ArrayList<Card> getCards() { return new ArrayList<>(cards); }
    public int getCardCount() { return cards.size(); }
    public boolean isFull() { return cards.size() >= MAX_CAPACITY; }

    public boolean containsCard(String cardName) {
        for (Card card : cards) {
            if (card.getName().equalsIgnoreCase(cardName)) {
                return true;
            }
        }
        return false;
    }

    public boolean addCard(Card card) {
        if (!isFull() && !containsCard(card.getName())) {
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
