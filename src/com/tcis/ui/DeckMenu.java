package com.tcis.ui;

import com.tcis.backend.InventorySystem;
import com.tcis.models.*;
import java.util.ArrayList;
import java.util.Scanner;

class DeckMenu {
    private final InventorySystem inventory;
    private final DisplaySystem display;
    private final Scanner scanner;
    
    public DeckMenu(InventorySystem inventory, DisplaySystem display, Scanner scanner) {
        this.inventory = inventory;
        this.display = display;
        this.scanner = scanner;
    }

    public boolean runDeckManagement() {
        boolean inDeckManagement = true;
        while (inDeckManagement) {
            System.out.println("\n--- Deck Management ---");
            System.out.println("1. Create a new Deck");
            System.out.println("2. Select a Deck to manage");
            System.out.println("3. Go Back to Main Menu");
            int choice = InputHelper.getIntInput(scanner, "Choose an option: ");

            switch(choice) {
                case 1:
                    createDeck();
                    break;
                case 2:
                    selectDeckToManage();
                    break;
                case 3:
                    inDeckManagement = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
        return true;
    }

    private boolean createDeck() {
        String name = InputHelper.getStringInput(scanner, "Enter a name for the new deck: ");
        boolean success = inventory.createDeck(name);
        if (success) {
            System.out.println("Deck '" + name + "' created.");
        } else {
            System.out.println("Error: A deck with this name already exists or the name is invalid.");
        }
        return success;
    }

    private boolean selectDeckToManage() {
        ArrayList<String> names = new ArrayList<>();
        for (Deck d : inventory.getDecks()) {
            names.add(d.getName());
        }
        
        System.out.println(display.getListDisplay("Select a Deck", names));
        if (names.isEmpty()) {
            return true;
        }

        int choice = InputHelper.getIntInput(scanner, "Choose an option (or 0 to go back): ");
        if (choice == 0) return true;

        if(choice > 0 && choice <= names.size()) {
            return deckSubMenu(names.get(choice - 1));
        } else {
            System.out.println("Invalid selection.");
            return false;
        }
    }

    private boolean deckSubMenu(String name) {
        boolean inSubMenu = true;
        while (inSubMenu) {
            System.out.printf("\n--- Managing Deck: %s ---\n", name);
            System.out.println("1. View Cards");
            System.out.println("2. Add Card");
            System.out.println("3. Remove Card");
            System.out.println("4. Delete this Deck");
            System.out.println("5. Back to Deck Selection");
            int choice = InputHelper.getIntInput(scanner, "Choose an option: ");

            switch (choice) {
                case 1:
                    System.out.println(display.getContainerDisplay("Deck", name, inventory.findDeck(name).getCards(), Deck.MAX_CAPACITY)); 
                    break;
                case 2:
                    addCard(name);
                    break;
                case 3:
                    removeCard(name);
                    break;
                case 4:
                    if (deleteDeck(name)) {
                        inSubMenu = false; // Exit if deleted
                    }
                    break;
                case 5:
                    inSubMenu = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
        return true;
    }
    
    private boolean addCard(String deckName) {
        String cardName = InputHelper.getStringInput(scanner, "Enter name of card from your collection to add: ");
        int result = inventory.addCardToDeck(cardName, deckName);
        switch (result) {
            case 0: System.out.println("Success! Card added to deck."); return true;
            case 1: System.out.println("Error: Card or Deck not found."); break;
            case 2: System.out.println("Error: No available copies of this card."); break;
            case 3: System.out.println("Error: Deck is full."); break;
            case 4: System.out.println("Error: A card with this name is already in the deck."); break;
            default: System.out.println("An unknown error occurred."); break;
        }
        return false;
    }
    
    private boolean removeCard(String deckName) {
        Deck deck = inventory.findDeck(deckName);
        System.out.println(display.getContainerDisplay("Deck", deckName, deck.getCards(), Deck.MAX_CAPACITY));
        if (deck.getCards().isEmpty()) {
            return true;
        }
        int cardChoice = InputHelper.getIntInput(scanner, "Enter number of the card to remove: ") - 1;
        boolean success = inventory.removeCardFromDeck(cardChoice, deckName);
        if (success) {
            System.out.println("Card returned to collection.");
        } else {
            System.out.println("Failed to remove card. Invalid index.");
        }
        return success;
    }
    
    private boolean deleteDeck(String deckName) {
        String confirm = InputHelper.getStringInput(scanner, String.format("Delete deck '%s'? All cards will be returned. (yes/no): ", deckName));
        if (confirm.equalsIgnoreCase("yes")) {
            boolean success = inventory.deleteDeck(deckName);
            if (success) {
                System.out.println("Deck deleted.");
                return true;
            }
        }
        System.out.println("Deletion canceled.");
        return false;
    }
}
