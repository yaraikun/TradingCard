package com.tcis.ui;

import com.tcis.backend.InventorySystem;
import java.util.Scanner;

public class MainMenu {
    private final InventorySystem inventory;
    private final DisplaySystem display;
    private final Scanner scanner;
    private final CollectionMenu collectionMenu;
    private final BinderMenu binderMenu;
    private final DeckMenu deckMenu;

    public MainMenu(InventorySystem inventory, DisplaySystem display, Scanner scanner) {
        this.inventory = inventory;
        this.display = display;
        this.scanner = scanner;
        this.collectionMenu = new CollectionMenu(inventory, display, scanner);
        this.binderMenu = new BinderMenu(inventory, display, scanner);
        this.deckMenu = new DeckMenu(inventory, display, scanner);
    }

    public int run() {
        boolean running = true;
        while (running) {
            System.out.println(displayOptions());
            int choice = InputHelper.getIntInput(scanner, "Choose an option: ");
            System.out.println(); // Add a line break for cleaner output

            switch (choice) {
                case 1:
                    collectionMenu.handleAddCard();
                    break;
                case 2:
                    if (!inventory.getCardTypes().isEmpty()) {
                        collectionMenu.handleIncreaseDecreaseCount();
                    } else {
                        System.out.println("Invalid option. You must add a card to your collection first.");
                    }
                    break;
                case 3:
                    if (!inventory.getCardTypes().isEmpty()) {
                        collectionMenu.handleDisplayCollection();
                    } else {
                        System.out.println("Invalid option. You must add a card to your collection first.");
                    }
                    break;
                case 4:
                    binderMenu.runBinderManagement();
                    break;
                case 5:
                    deckMenu.runDeckManagement();
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
            System.out.println(); // Add a line break for spacing
        }
        return 0; // Indicate successful exit
    }

    private String displayOptions() {
        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append("              MAIN MENU\n");
        sb.append("=========================================\n");
        sb.append("1. Add a New Card Type\n");
        
        if (!inventory.getCardTypes().isEmpty()) {
            sb.append("2. Increase/Decrease Card Count\n");
            sb.append("3. Display Entire Collection\n");
        }
        
        sb.append("4. Manage Binders\n");
        sb.append("5. Manage Decks\n");
        sb.append("6. Exit\n");
        sb.append("=========================================");
        return sb.toString();
    }
}
