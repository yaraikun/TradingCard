package com.tcis;

import com.tcis.backend.BinderManager;
import com.tcis.backend.CollectionManager;
import com.tcis.backend.DeckManager;
import com.tcis.ui.Menu;
import java.util.Scanner;

/**
 * The main controller and entry point of the application logic.
 * It initializes all backend and UI components and starts the main menu loop.
 */
public class InventorySystem {
    private final Scanner scanner;
    private final CollectionManager collectionManager;
    private final BinderManager binderManager;
    private final DeckManager deckManager;
    private final Menu menu;

    /**
     * Constructs the InventorySystem, creating and wiring together all components.
     */
    public InventorySystem() {
        this.scanner = new Scanner(System.in);
        this.collectionManager = new CollectionManager();
        this.binderManager = new BinderManager(this.collectionManager);
        this.deckManager = new DeckManager(this.collectionManager);
        this.menu = new Menu(scanner, collectionManager, binderManager, deckManager);
    }

    /**
     * Starts and runs the main application loop.
     */
    public void run() {
        System.out.println("Welcome to the Trading Card Inventory System!");
        menu.runMainMenu();
        System.out.println("Exiting. Goodbye!");
        scanner.close();
    }
}
