package com.tcis.backend;

import com.tcis.models.Card;
import java.util.ArrayList;
import java.util.HashMap;

public class Collection {
    private final ArrayList<Card> cardTypes;
    private final HashMap<String, Integer> cardCounts;

    public Collection() {
        this.cardTypes = new ArrayList<>();
        this.cardCounts = new HashMap<>();
    }

    public Card findCard(String name) {
        if (name == null) return null;
        for (Card card : cardTypes) {
            if (card.getName().equalsIgnoreCase(name.trim())) {
                return card;
            }
        }
        return null;
    }

    public boolean addCardType(Card card) {
        if (card == null || findCard(card.getName()) != null) {
            return false;
        }
        cardTypes.add(card);
        cardCounts.put(card.getName().toLowerCase(), 1);
        return true;
    }

    public boolean increaseCount(String name, int amount) {
        String key = name.toLowerCase().trim();
        if (findCard(name) == null || amount <= 0) return false;
        cardCounts.put(key, cardCounts.getOrDefault(key, 0) + amount);
        return true;
    }

    public boolean decreaseCount(String name, int amount) {
        String key = name.toLowerCase().trim();
        if (findCard(name) == null || amount <= 0 || cardCounts.getOrDefault(key, 0) < amount) {
            return false;
        }
        cardCounts.put(key, cardCounts.get(key) - amount);
        return true;
    }

    public boolean isCardAvailable(String name) {
        if (name == null) return false;
        return cardCounts.getOrDefault(name.toLowerCase().trim(), 0) > 0;
    }

    public ArrayList<Card> getCardTypes() {
        return new ArrayList<>(cardTypes);
    }

    public HashMap<String, Integer> getCardCounts() {
        return new HashMap<>(cardCounts);
    }
}
