package com.tcis.ui;

import com.tcis.backend.BinderManager;
import com.tcis.backend.CollectionManager;
import com.tcis.backend.DeckManager;
import java.util.Scanner;

/**
 * The main orchestrator for the entire UI.
 * It displays the primary menus and delegates the chosen actions to the Handler class,
 * effectively managing the application's user-facing state machine.
 */
public class Menu {
    private final Scanner scanner;
    private final Handler handler;

    /**
     * Constructs the Menu system, creating the single Handler instance that
     * will execute all user actions.
     * @param scanner The global Scanner instance.
     * @param cm The CollectionManager instance from the backend.
     * @param bm The BinderManager instance from the backend.
     * @param dm The DeckManager instance from the backend.
     */
    public Menu(Scanner scanner, CollectionManager cm, BinderManager bm, DeckManager dm) {
        this.scanner = scanner;
        this.handler = new Handler(scanner, cm, bm, dm);
    }

    /**
     * Runs the top-level main menu loop.
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

    /**
     * Runs the collection-specific sub-menu loop.
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
    
    /**
     * Runs the binder-specific sub-menu loop.
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
    
    /**
     * Runs the deck-specific sub-menu loop.
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
