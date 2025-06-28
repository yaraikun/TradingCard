package com.tcis.ui;

import com.tcis.models.Binder;
import com.tcis.models.Card;
import com.tcis.models.Deck;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A utility class responsible for formatting all complex data into user-readable strings.
 * This class decouples the data formatting from the action of printing to the console.
 */
public final class Display {
    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Display() {}

    /**
     * Formats the main menu string.
     * @return A formatted string for the main menu.
     */
    public static String getMainMenu() {
        return "\n=========================================\n" +
               "              MAIN MENU\n" +
               "=========================================\n" +
               " (1) Collection Menu\n" +
               " (2) Binder Management\n" +
               " (3) Deck Management\n" +
               " (0) Exit\n" +
               "=========================================";
    }

    /**
     * Formats the collection menu string.
     * @return A formatted string for the collection menu.
     */
    public static String getCollectionMenu() {
        return "\n--- Collection Menu ---\n" +
               " (1) View Collection\n" +
               " (2) Add New Card\n" +
               " (3) Update Card Count\n" +
               " (0) Back to Main Menu";
    }

    /**
     * Formats the binder management menu string.
     * @return A formatted string for the binder menu.
     */
    public static String getBinderMenu() {
        return "\n--- Binder Management ---\n" +
               " (1) View All Binders\n" +
               " (2) Create New Binder\n" +
               " (3) Add Card to a Binder\n" +
               " (4) Remove Card from a Binder\n" +
               " (5) Trade Card from a Binder\n" +
               " (6) Delete a Binder\n" +
               " (0) Back to Main Menu";
    }

    /**
     * Formats the deck management menu string.
     * @return A formatted string for the deck menu.
     */
    public static String getDeckMenu() {
        return "\n--- Deck Management ---\n" +
               " (1) View All Decks\n" +
               " (2) Create New Deck\n" +
               " (3) Add Card to a Deck\n" +
               " (4) Remove Card from a Deck\n" +
               " (5) Delete a Deck\n" +
               " (0) Back to Main Menu";
    }

    /**
     * Formats the detailed information of a single card.
     * @param card The Card object to display.
     * @return A formatted string of the card's details.
     */
    public static String getCardDetails(Card card) {
        if (card == null) return "Card not found.";
        return String.format("\n--- Card Details ---\n" +
            "  Name: %s\n" +
            "  Rarity: %s\n" +
            "  Variant: %s\n" +
            "  Base Value: $%.2f\n" +
            "  Calculated Value: $%.2f",
            card.getName(), card.getRarity().getDisplayName(), card.getVariant().getDisplayName(), 
            card.getBaseValue(), card.getCalculatedValue()
        );
    }
    
    /**
     * Formats a generic list of strings into a numbered menu.
     * @param items The list of strings to display.
     * @param title The title to display above the list.
     * @return A formatted, numbered list as a string.
     */
    public static String getList(ArrayList<String> items, String title) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("\n--- %s ---\n", title));
        if (items.isEmpty()) {
            sb.append("Nothing to select.\n");
            return sb.toString();
        }
        Collections.sort(items);
        sb.append(" (0) Back\n");
        for (int i = 0; i < items.size(); i++) {
            sb.append(String.format(" (%d) %s\n", i + 1, items.get(i)));
        }
        return sb.toString();
    }
}
