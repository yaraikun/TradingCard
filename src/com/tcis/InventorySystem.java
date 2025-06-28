package com.tcis;

import com.tcis.backend.BinderManager;
import com.tcis.backend.CollectionManager;
import com.tcis.backend.DeckManager;
import com.tcis.ui.Menu;
import java.util.Scanner;

/*
    Class: InventorySystem
    
    Purpose:
    The main controller and entry point of the application logic. It acts as a
    central hub that initializes and owns all major backend and UI components.
    Its primary responsibility is to start the main menu loop and manage the
    lifecycle of the global Scanner resource.
*/
public class InventorySystem {
    /*
        Attribute: scanner
        
        Purpose:
        The single, global Scanner instance used for all user input throughout
        the application. It is created here and passed to the UI components that need it.
    */
    private final Scanner scanner;

    /*
        Attribute: collectionManager
        
        Purpose:
        The instance of the CollectionManager, which handles all logic related to the
        main card collection.
    */
    private final CollectionManager collectionManager;

    /*
        Attribute: binderManager
        
        Purpose:
        The instance of the BinderManager, which handles all logic for binders.
    */
    private final BinderManager binderManager;

    /*
        Attribute: deckManager
        
        Purpose:
        The instance of the DeckManager, which handles all logic for decks.
    */
    private final DeckManager deckManager;

    /*
        Attribute: menu
        
        Purpose:
        The primary UI orchestrator object. The InventorySystem delegates control
        to this object to handle all user-facing menus and interactions.
    */
    private final Menu menu;

    /*
        Constructor: InventorySystem
        
        Purpose:
        Constructs the entire application's object graph. It creates and wires
        together all necessary backend managers and the main UI menu system,
        ensuring all dependencies are properly injected.
    */
    public InventorySystem() {
        this.scanner = new Scanner(System.in);
        this.collectionManager = new CollectionManager();
        this.binderManager = new BinderManager(this.collectionManager);
        this.deckManager = new DeckManager(this.collectionManager);
        this.menu = new Menu(scanner, collectionManager, binderManager, deckManager);
    }

    /*
        Method: run
        
        Purpose:
        Starts the main application loop by calling the entry point of the UI menu
        system. It also handles the final "welcome" and "goodbye" messages and
        ensures the Scanner resource is closed properly upon exit.
        
        Returns:
        void. The application terminates when this method completes.
    */
    public void run() {
        System.out.println("Welcome to the Trading Card Inventory System!");
        menu.runMainMenu();
        System.out.println("Exiting. Goodbye!");
        scanner.close();
    }
}
