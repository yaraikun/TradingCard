package com.tcis.ui;

import com.tcis.backend.InventorySystem;
import com.tcis.models.*;
import java.util.ArrayList;
import java.util.Scanner;

class BinderMenu {
    private final InventorySystem inventory;
    private final DisplaySystem display;
    private final Scanner scanner;
    
    public BinderMenu(InventorySystem inventory, DisplaySystem display, Scanner scanner) {
        this.inventory = inventory;
        this.display = display;
        this.scanner = scanner;
    }

    public boolean runBinderManagement() {
        boolean inBinderManagement = true;
        while (inBinderManagement) {
            System.out.println("\n--- Binder Management ---");
            System.out.println("1. Create a new Binder");
            System.out.println("2. Select a Binder to manage");
            System.out.println("3. Go Back to Main Menu");
            int choice = InputHelper.getIntInput(scanner, "Choose an option: ");

            switch(choice) {
                case 1:
                    createBinder();
                    break;
                case 2:
                    selectBinderToManage();
                    break;
                case 3:
                    inBinderManagement = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
        return true;
    }
    
    private boolean createBinder() {
        String name = InputHelper.getStringInput(scanner, "Enter a name for the new binder: ");
        boolean success = inventory.createBinder(name);
        if (success) {
            System.out.println("Binder '" + name + "' created.");
        } else {
            // The backend prints a more specific error
        }
        return success;
    }

    private boolean selectBinderToManage() {
        ArrayList<String> names = new ArrayList<>();
        for (Binder b : inventory.getBinders()) {
            names.add(b.getName());
        }
        
        System.out.println(display.getListDisplay("Select a Binder", names));
        if (names.isEmpty()) {
            return true;
        }

        int choice = InputHelper.getIntInput(scanner, "Choose an option (or 0 to go back): ");
        if (choice == 0) return true;

        if(choice > 0 && choice <= names.size()) {
            return binderSubMenu(names.get(choice - 1));
        } else {
            System.out.println("Invalid selection.");
            return false;
        }
    }

    private boolean binderSubMenu(String name) {
        boolean inSubMenu = true;
        while (inSubMenu) {
            System.out.printf("\n--- Managing Binder: %s ---\n", name);
            System.out.println("1. View Cards");
            System.out.println("2. Add Card");
            System.out.println("3. Remove Card");
            System.out.println("4. Trade Card");
            System.out.println("5. Delete this Binder");
            System.out.println("6. Back to Binder Selection");
            int choice = InputHelper.getIntInput(scanner, "Choose an option: ");

            switch (choice) {
                case 1:
                    System.out.println(display.getContainerDisplay("Binder", name, inventory.findBinder(name).getCards(), Binder.MAX_CAPACITY)); 
                    break;
                case 2:
                    addCard(name);
                    break;
                case 3:
                    removeCard(name);
                    break;
                case 4:
                    tradeCard(name);
                    break;
                case 5:
                    if (deleteBinder(name)) {
                        inSubMenu = false; // Exit if deleted
                    }
                    break;
                case 6:
                    inSubMenu = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
        return true;
    }
    
    private boolean addCard(String binderName) {
        String cardName = InputHelper.getStringInput(scanner, "Enter name of card from your collection to add: ");
        int result = inventory.addCardToBinder(cardName, binderName);
        if (result == 0) {
            System.out.println("Success! Card added to binder.");
        } else {
            System.out.println("Failed to add card. (Card not found, no copies, or binder is full)");
        }
        return result == 0;
    }
    
    private boolean removeCard(String binderName) {
        Binder binder = inventory.findBinder(binderName);
        System.out.println(display.getContainerDisplay("Binder", binderName, binder.getCards(), Binder.MAX_CAPACITY));
        if (binder.getCards().isEmpty()) {
            return true;
        }
        int cardChoice = InputHelper.getIntInput(scanner, "Enter number of the card to remove: ") - 1;
        boolean success = inventory.removeCardFromBinder(cardChoice, binderName);
        if (success) {
            System.out.println("Card returned to collection.");
        } else {
            System.out.println("Failed to remove card. Invalid index.");
        }
        return success;
    }
    
    private boolean tradeCard(String binderName) {
        Binder binder = inventory.findBinder(binderName);
        System.out.println(display.getContainerDisplay("Binder", binderName, binder.getCards(), Binder.MAX_CAPACITY));
        if (binder.getCards().isEmpty()) {
            return true;
        }
        
        int outgoingIndex = InputHelper.getIntInput(scanner, "Select card to give up: ") - 1;
        if (outgoingIndex < 0 || outgoingIndex >= binder.getCardCount()) {
            System.out.println("Invalid selection.");
            return false;
        }
        Card outgoingCard = binder.getCards().get(outgoingIndex);
        
        System.out.println("--- Enter details for INCOMING card ---");
        
        // REVISED: Apply full validation to incoming card details
        String inName;
        do {
            inName = InputHelper.getStringInput(scanner, "Enter name: ");
            if (!InputValidator.isValidName(inName)) { 
                System.out.println("Incoming card name cannot be blank.");
            }
        } while (!InputValidator.isValidName(inName));
        
        String inRarityStr;
        do {
            inRarityStr = InputHelper.getStringInput(scanner, "Enter rarity: ");
            if (!InputValidator.isValidRarity(inRarityStr)) {
                System.out.println("Invalid rarity. Please enter a valid option.");
            }
        } while (!InputValidator.isValidRarity(inRarityStr));
        CardRarity inRarity = CardRarity.fromString(inRarityStr);
        
        double inValue;
        do {
            inValue = InputHelper.getDoubleInput(scanner, "Enter base value: ");
            if (!InputValidator.isPositive(inValue)) {
                System.out.println("Value cannot be negative.");
            }
        } while(!InputValidator.isPositive(inValue));
        
        CardVariant inVariant = CardVariant.NORMAL;
        if (inRarity == CardRarity.RARE || inRarity == CardRarity.LEGENDARY) { 
            String inVariantStr;
            do {
                inVariantStr = InputHelper.getStringInput(scanner, "Enter variant: ");
                if (!InputValidator.isValidVariant(inVariantStr)) {
                    System.out.println("Invalid variant. Please enter a valid option.");
                }
            } while (!InputValidator.isValidVariant(inVariantStr));
            inVariant = CardVariant.fromString(inVariantStr); 
        }
        
        try {
            Card incomingCard = new Card(inName, inValue, inRarity, inVariant);
            double diff = Math.abs(outgoingCard.getCalculatedValue() - incomingCard.getCalculatedValue());
            System.out.printf("Value difference is $%.2f.\n", diff);
            
            if (diff >= 1.0) {
                System.out.print("This may be an unfair trade. Proceed? (yes/no): ");
                if (!scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                    System.out.println("Trade canceled.");
                    return false;
                }
            }
            
            boolean success = inventory.performTrade(binderName, outgoingIndex, incomingCard);
            if (success) {
                System.out.println("Trade successful!");
            } else {
                System.out.println("Trade failed.");
            }
            return success;
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating incoming card: " + e.getMessage());
            return false;
        }
    }
    
    private boolean deleteBinder(String binderName) {
        String confirm = InputHelper.getStringInput(scanner, String.format("Delete binder '%s'? All cards will be returned. (yes/no): ", binderName));
        if (confirm.equalsIgnoreCase("yes")) {
            boolean success = inventory.deleteBinder(binderName);
            if (success) {
                System.out.println("Binder deleted.");
                return true;
            }
        }
        System.out.println("Deletion canceled.");
        return false;
    }
}
