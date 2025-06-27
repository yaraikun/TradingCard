package com.tcis;

public class Binder {

    public static final int MAX_CAPACITY = 20;

    private final String name;
    private final Card[] cards; 
    private int cardCount;      

    public Binder(String name) {
        this.name = name;
        this.cards = new Card[MAX_CAPACITY];
        this.cardCount = 0;
    }

    public String getName() {
        return this.name;
    }

    public int getCardCount() {
        return this.cardCount;
    }

    public Card[] getCardsForDisplay() {
        Card[] displayCards = new Card[this.cardCount];
        System.arraycopy(this.cards, 0, displayCards, 0, this.cardCount);
        return displayCards;
    }

    public boolean addCard(Card card) {
        if (isFull()) {
            return false;
        }

        this.cards[this.cardCount] = card;
        this.cardCount++;

        return true;
    }

    public boolean isFull() {
        return this.cardCount >= MAX_CAPACITY;
    }
}
