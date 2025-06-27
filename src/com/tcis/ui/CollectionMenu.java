package com.tcis.ui;

import com.tcis.backend.InventorySystem;
import com.tcis.models.*;
import java.util.Scanner;

class CollectionMenu {
    private final InventorySystem inventory;
    private final DisplaySystem display;
    private final Scanner scanner;

    public CollectionMenu(InventorySystem inventory, DisplaySystem display, Scanner scanner) {
        this.inventory = inventory;
        this.display = display;
        this.scanner = scanner;
    }

    public boolean handleAddCard() {
        System.out.println("\n--- Add a New Card Type ---");
        String name;
        do {
            name = InputHelper.getStringInput(scanner, "Enter card name: ");
            if (!InputValidator.isValidName(name)) {
                System.out.println("Card name cannot be blank.");
            } else if (inventory.findCard(name) != null) {
                System.out.println("A card with this name already exists.");
                return false; // Fail early if name is a duplicate
            }
        } while (!InputValidator.isValidName(name));

        String rarityStr;
        do {
            rarityStr = InputHelper.getStringInput(scanner, "Enter rarity (Common, Uncommon, Rare, Legendary): ");
            if (!InputValidator.isValidRarity(rarityStr)) {
                System.out.println("Invalid rarity. Please choose from the provided options.");
            }
        } while (!InputValidator.isValidRarity(rarityStr));
        CardRarity rarity = CardRarity.fromString(rarityStr);
        
        CardVariant variant = CardVariant.NORMAL;
        if (rarity == CardRarity.RARE || rarity == CardRarity.LEGENDARY) {
            String variantStr;
            do {
                variantStr = InputHelper.getStringInput(scanner, "Enter variant (Normal, Extended-art, Full-art, Alt-art): ");
                if (!InputValidator.isValidVariant(variantStr)) {
                    System.out.println("Invalid variant. Please choose from the provided options.");
                }
            } while (!InputValidator.isValidVariant(variantStr));
            variant = CardVariant.fromString(variantStr);
        }
        
        double value;
        do {
            value = InputHelper.getDoubleInput(scanner, "Enter base dollar value: ");
            if (!InputValidator.isPositive(value)) {
                System.out.println("Base value cannot be negative.");
            }
        } while (!InputValidator.isPositive(value));
        
        if (inventory.addCardToCollection(name, value, rarity, variant)) {
            System.out.println("Success! Card '" + name + "' added.");
            return true;
        }
        // If it fails, the backend will have printed the specific error.
        return false;
    }

    public boolean handleIncreaseDecreaseCount() {
        System.out.println("\n--- Change Card Count ---");
        String name = InputHelper.getStringInput(scanner, "Enter the name of the card to modify: ");
        if (inventory.findCard(name) == null) {
            System.out.println("Error: Card not found.");
            return false;
        }
        
        int choice = InputHelper.getIntInput(scanner, "Do you want to (1) Increase or (2) Decrease the count? ");
        
        int amount;
        do {
            amount = InputHelper.getIntInput(scanner, "Enter amount: ");
            if (!InputValidator.isPositive(amount)) {
                System.out.println("Amount must be a positive number.");
            }
        } while (!InputValidator.isPositive(amount));
        
        boolean success = (choice == 1) 
            ? inventory.increaseCardCount(name, amount) 
            : inventory.decreaseCardCount(name, amount);
        
        if (success) {
            System.out.println("Count updated successfully.");
        } else {
            System.out.println("Failed to update count. (Not enough cards in collection)");
        }
        return success;
    }

    public boolean handleDisplayCollection() {
        System.out.println(display.getCollectionDisplay(inventory.getCardTypes(), inventory.getCardCounts()));
        
        System.out.print("\nEnter a card name to view details (or press Enter to return): ");
        String name = scanner.nextLine(); // No need for helper, blank is a valid "exit"
        if (!name.trim().isEmpty()) {
            System.out.println(display.getCardDetailsDisplay(inventory.findCard(name)));
        }
        return true;
    }
}
