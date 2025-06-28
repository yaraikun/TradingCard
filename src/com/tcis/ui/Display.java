package com.tcis.ui;

import com.tcis.models.Binder;
import com.tcis.models.Card;
import com.tcis.models.Deck;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A utility class responsible for formatting all complex data into user-readable strings.
 * It centralizes all visual elements like headers and separators for a consistent UI.
 */
public final class Display {
    private static final String SEPARATOR = "=========================================";

    private Display() {}

    /**
     * Creates a standardized, centered header string.
     * @param title The title to be centered.
     * @return The formatted header string.
     */
    public static String getHeader(String title) {
        return "\n" + SEPARATOR + "\n" +
               "              " + title + "\n" +
               SEPARATOR;
    }

    public static String getMainMenu() {
        return getHeader("MAIN MENU") + "\n" +
               " (1) Collection Menu\n" +
               " (2) Binder Management\n" +
               " (3) Deck Management\n" +
               " (0) Exit\n" +
               SEPARATOR;
    }

    public static String getCollectionMenu() {
        return getHeader("COLLECTION MENU") + "\n" +
               " (1) View Collection\n" +
               " (2) Add New Card\n" +
               " (3) Update Card Count\n" +
               " (0) Back to Main Menu\n" +
               SEPARATOR;
    }

    public static String getBinderMenu() {
        return getHeader("BINDER MANAGEMENT") + "\n" +
               " (1) View All Binders\n" +
               " (2) Create New Binder\n" +
               " (3) Add Card to a Binder\n" +
               " (4) Remove Card from a Binder\n" +
               " (5) Trade Card from a Binder\n" +
               " (6) Delete a Binder\n" +
               " (0) Back to Main Menu\n" +
               SEPARATOR;
    }

    public static String getDeckMenu() {
        return getHeader("DECK MANAGEMENT") + "\n" +
               " (1) View All Decks\n" +
               " (2) Create New Deck\n" +
               " (3) Add Card to a Deck\n" +
               " (4) Remove Card from a Deck\n" +
               " (5) Delete a Deck\n" +
               " (0) Back to Main Menu\n" +
               SEPARATOR;
    }

    public static String getCardDetails(Card card) {
        if (card == null) return "Card not found.";
        return getHeader("CARD DETAILS") + "\n" +
            String.format("  Name: %s\n", card.getName()) +
            String.format("  Rarity: %s\n", card.getRarity().getDisplayName()) +
            String.format("  Variant: %s\n", card.getVariant().getDisplayName()) +
            String.format("  Base Value: $%.2f\n", card.getBaseValue()) +
            String.format("  Calculated Value: $%.2f\n", card.getCalculatedValue()) +
            SEPARATOR;
    }
    
    public static String getList(ArrayList<String> items, String title) {
        StringBuilder sb = new StringBuilder();
        sb.append(getHeader(title.toUpperCase()));
        if (items.isEmpty()) {
            sb.append("\nNothing to select.\n");
        } else {
            Collections.sort(items);
            sb.append("\n (0) Back\n");
            for (int i = 0; i < items.size(); i++) {
                sb.append(String.format(" (%d) %s\n", i + 1, items.get(i)));
            }
        }
        sb.append(SEPARATOR);
        return sb.toString();
    }
}
