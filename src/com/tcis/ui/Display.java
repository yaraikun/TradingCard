package com.tcis.ui;

import com.tcis.models.Binder;
import com.tcis.models.Card;
import com.tcis.models.Deck;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
    Class: Display
    
    Purpose:
    A utility class responsible for formatting all complex data into user-readable
    strings. It centralizes all visual elements like headers and separators for a
    consistent UI look and feel. This class never prints to the console directly;
    it only returns formatted strings.
*/
public final class Display {
    /*
        Attribute: SEPARATOR
        
        Purpose:
        A private constant string used to create consistent visual separation
        between different sections of the UI.
    */
    private static final String SEPARATOR = "=========================================";

    /*
        Constructor: Display
        
        Purpose:
        A private constructor to prevent instantiation of this utility class, as all
        its methods are static.
    */
    private Display() {}

    /*
        Method: getHeader
        
        Purpose:
        Creates a standardized, centered header string for use in various menus and displays.
        
        Returns:
        A String containing the formatted header.
        
        @param title: The title to be centered within the header.
    */
    public static String getHeader(String title) {
        return "\n" + SEPARATOR + "\n" +
               "              " + title + "\n" +
               SEPARATOR;
    }

    /*
        Method: getMainMenu
        
        Purpose:
        Formats and returns the complete text for the main menu.
        
        Returns:
        A String representing the formatted main menu.
    */
    public static String getMainMenu() {
        return getHeader("MAIN MENU") + "\n" +
               " (1) Collection Menu\n" +
               " (2) Binder Management\n" +
               " (3) Deck Management\n" +
               " (0) Exit\n" +
               SEPARATOR;
    }

    /*
        Method: getCollectionMenu
        
        Purpose:
        Formats and returns the complete text for the collection management menu.
        
        Returns:
        A String representing the formatted collection menu.
    */
    public static String getCollectionMenu() {
        return getHeader("COLLECTION MENU") + "\n" +
               " (1) View Collection\n" +
               " (2) Add New Card\n" +
               " (3) Update Card Count\n" +
               " (0) Back to Main Menu\n" +
               SEPARATOR;
    }

    /*
        Method: getBinderMenu
        
        Purpose:
        Formats and returns the complete text for the binder management menu.
        
        Returns:
        A String representing the formatted binder menu.
    */
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

    /*
        Method: getDeckMenu
        
        Purpose:
        Formats and returns the complete text for the deck management menu.
        
        Returns:
        A String representing the formatted deck menu.
    */
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

    /*
        Method: getCardDetails
        
        Purpose:
        Formats the detailed information of a single card into a clean, readable block of text.
        
        Returns:
        A String containing all details of the card, or a "not found" message if the card is null.
        
        @param card: The Card object whose details are to be displayed.
    */
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
    
    /*
        Method: getList
        
        Purpose:
        Formats a generic list of strings into a numbered, selectable menu. The list is
        sorted alphabetically before being formatted.
        
        Returns:
        A formatted String representing the numbered list, including a title and a "Back" option.
        
        @param items: An ArrayList of String items to be displayed.
        @param title: The title to be displayed above the list.
    */
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
