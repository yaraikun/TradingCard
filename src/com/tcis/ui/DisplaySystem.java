package com.tcis.ui;

import com.tcis.models.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class DisplaySystem {
    public String getCollectionDisplay(ArrayList<Card> types, HashMap<String, Integer> counts) {
        if (types.isEmpty()) { return "Your collection is empty."; }
        
        ArrayList<Card> sortedTypes = new ArrayList<>(types);
        sortedTypes.sort(Comparator.comparing(Card::getName));
        
        StringBuilder sb = new StringBuilder();
        sb.append("\n--- Your Entire Collection ---\n");
        sb.append(String.format("%-30s | %s\n", "Card Name", "Count"));
        sb.append("-----------------------------------+-------\n");
        for (Card card : sortedTypes) {
            sb.append(String.format("%-30s | %d\n", card.getName(), counts.get(card.getName().toLowerCase())));
        }
        return sb.toString();
    }
    public String getCardDetailsDisplay(Card card) {
        if (card == null) { return "Card not found."; }
        return String.format(
            "\n--- Card Details ---\n" +
            "  Name: %s\n" +
            "  Rarity: %s\n" +
            "  Variant: %s\n" +
            "  Base Value: $%.2f\n" +
            "  Calculated Value: $%.2f",
            card.getName(), card.getRarity().getDisplayName(), card.getVariant().getDisplayName(), 
            card.getBaseValue(), card.getCalculatedValue()
        );
    }
    public String getContainerDisplay(String type, String name, ArrayList<Card> cards, int capacity) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("\n--- Cards in %s: %s (%d/%d) ---\n", type, name, cards.size(), capacity));
        if (cards.isEmpty()) {
            sb.append(String.format("This %s is empty.\n", type.toLowerCase()));
            return sb.toString();
        }
        
        ArrayList<Card> sortedCards = new ArrayList<>(cards);
        sortedCards.sort(Comparator.comparing(Card::getName));
        for (int i = 0; i < sortedCards.size(); i++) {
            sb.append(String.format("%d. %s\n", i + 1, sortedCards.get(i).getName()));
        }
        return sb.toString();
    }
    public String getListDisplay(String title, ArrayList<String> items) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("\n--- %s ---\n", title));
        if (items.isEmpty()) {
            sb.append("Nothing to select.\n");
            return sb.toString();
        }
        
        Collections.sort(items);
        for (int i = 0; i < items.size(); i++) {
            sb.append(String.format("%d. %s\n", i + 1, items.get(i)));
        }
        return sb.toString();
    }
}
