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
}
