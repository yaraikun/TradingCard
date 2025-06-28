package com.tcis.ui;

import com.tcis.backend.BinderManager;
import com.tcis.backend.CollectionManager;
import com.tcis.backend.DeckManager;
import java.util.Scanner;

/*
    Class: Menu
    
    Purpose:
    The main orchestrator for the entire UI. It displays the primary menus and
    delegates the chosen actions to the Handler class, effectively managing the
    application's user-facing state machine. It controls the flow from one menu
    to another.
*/
public class Menu {
    /*
        Attribute: scanner
        
        Purpose:
        The single, global Scanner instance used for all user input throughout
        the application.
    */
    private final Scanner scanner;

    /*
        Attribute: handler
        
        Purpose:
        A reference to the single Handler instance, which contains the logic to
        execute all possible user actions.
    */
    private final Handler handler;

    /*
        Constructor: Menu
        
        Purpose:
        Initializes the Menu system by creating the single Handler instance that
        will execute all user actions. It injects the necessary backend managers
        and the scanner into the Handler.
        
        @param scanner: The global Scanner instance.
        @param cm: The CollectionManager instance from the backend.
        @param bm: The BinderManager instance from the backend.
        @param dm: The DeckManager instance from the backend.
    */
    public Menu(Scanner scanner, CollectionManager cm, BinderManager bm, DeckManager dm) {
        this.scanner = scanner;
        this.handler = new Handler(scanner, cm, bm, dm);
    }

    /*
        Method: runMainMenu
        
        Purpose:
        Runs the top-level main menu loop. This is the primary loop of the
        application's UI, which continues until the user chooses to exit.
        
        Returns:
        void. The method terminates when the application is exited.
    */
    public void runMainMenu() {
        boolean running = true;
        while (running) {
            System.out.println(Display.getMainMenu());
            int choice = Inputter.getIntInput(scanner, "Choose an option: ");
            switch (choice) {
                case 1: runCollectionMenu(); break;
                case 2: runBinderMenu(); break;
                case 3: runDeckMenu(); break;
                case 0: running = false; break;
                default: System.out.println("Invalid option.");
            }
        }
    }

    /*
        Method: runCollectionMenu
        
        Purpose:
        Runs the collection-specific sub-menu loop. This method is called from the
        main menu and manages all actions related to the main card collection.
        
        Returns:
        void. The method terminates when the user chooses to go back to the main menu.
    */
    private void runCollectionMenu() {
        boolean inMenu = true;
        while(inMenu) {
            System.out.println(Display.getCollectionMenu());
            int choice = Inputter.getIntInput(scanner, "Choose an option: ");
            switch(choice) {
                case 1: handler.handleViewCollection(); break;
                case 2: handler.handleAddNewCard(); break;
                case 3: handler.handleUpdateCardCount(); break;
                case 0: inMenu = false; break;
                default: System.out.println("Invalid option.");
            }
        }
    }
    
    /*
        Method: runBinderMenu
        
        Purpose:
        Runs the binder-specific sub-menu loop. This method is called from the
        main menu and manages all actions related to binders.
        
        Returns:
        void. The method terminates when the user chooses to go back to the main menu.
    */
    private void runBinderMenu() {
        boolean inMenu = true;
        while(inMenu) {
            System.out.println(Display.getBinderMenu());
            int choice = Inputter.getIntInput(scanner, "Choose an option: ");
            switch(choice) {
                case 1: handler.handleViewBinders(); break;
                case 2: handler.handleCreateBinder(); break;
                case 3: handler.handleAddCardToBinder(); break;
                case 4: handler.handleRemoveCardFromBinder(); break;
                case 5: handler.handleTradeFromBinder(); break;
                case 6: handler.handleDeleteBinder(); break;
                case 0: inMenu = false; break;
                default: System.out.println("Invalid option.");
            }
        }
    }
    
    /*
        Method: runDeckMenu
        
        Purpose:
        Runs the deck-specific sub-menu loop. This method is called from the
        main menu and manages all actions related to decks.
        
        Returns:
        void. The method terminates when the user chooses to go back to the main menu.
    */
    private void runDeckMenu() {
        boolean inMenu = true;
        while(inMenu) {
            System.out.println(Display.getDeckMenu());
            int choice = Inputter.getIntInput(scanner, "Choose an option: ");
            switch(choice) {
                case 1: handler.handleViewDecks(); break;
                case 2: handler.handleCreateDeck(); break;
                case 3: handler.handleAddCardToDeck(); break;
                case 4: handler.handleRemoveCardFromDeck(); break;
                case 5: handler.handleDeleteDeck(); break;
                case 0: inMenu = false; break;
                default: System.out.println("Invalid option.");
            }
        }
    }
}
