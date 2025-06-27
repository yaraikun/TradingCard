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

    public Card removeCardAtIndex(int index) {
        if (index < 0 || index >= this.cardCount) {
            return null; 
        }

        Card removedCard = this.cards[index];
        
        for (int i = index; i < this.cardCount - 1; i++) {
            this.cards[i] = this.cards[i + 1];
        }

        this.cards[this.cardCount - 1] = null; 
        this.cardCount--;

        return removedCard;
    }

    public boolean isFull() {
        return this.cardCount >= MAX_CAPACITY;
    }
}
