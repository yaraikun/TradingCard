package com.tcis.ui;

import com.tcis.backend.BinderManager;
import com.tcis.backend.CollectionManager;
import com.tcis.backend.DeckManager;
import com.tcis.models.Binder;
import com.tcis.models.Card;
import com.tcis.models.Deck;
import com.tcis.models.card.Rarity;
import com.tcis.models.card.Variant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Contains handler methods that execute the logic for specific UI menu options.
 */
public class Handler {
    private final Scanner scanner;
    private final CollectionManager collectionManager;
    private final BinderManager binderManager;
    private final DeckManager deckManager;

    public Handler(Scanner s, CollectionManager cm, BinderManager bm, DeckManager dm) {
        this.scanner = s;
        this.collectionManager = cm;
        this.binderManager = bm;
        this.deckManager = dm;
    }
    
    // --- Collection Handlers ---
    public void handleViewCollection() {
        Card card = selectCardFromCollection("Select a card to view details");
        if (card != null) {
            System.out.println(Display.getCardDetails(card));
        }
    }

    public void handleAddNewCard() {
        System.out.println(Display.getHeader("ADD NEW CARD"));
        String name = Inputter.getValidName(scanner, "Enter card name: ");
        if (collectionManager.findCard(name) != null) { System.out.println("A card with this name already exists."); return; }
        
        Rarity rarity = Inputter.getValidRarity(scanner);
        Variant variant = Inputter.getValidVariant(scanner, rarity);
        double value = Inputter.getValidPositiveDouble(scanner, "Enter base dollar value: ");
        
        if (collectionManager.addNewCard(name, value, rarity, variant)) {
            System.out.println("Success! Card '" + name + "' added.");
        }
    }

    public void handleUpdateCardCount() {
        Card card = selectCardFromCollection("Select Card to Update Count");
        if (card == null) return;

        System.out.printf("Selected: %s\n", card.getName());
        System.out.println(" (1) Increase\n (2) Decrease\n (0) Cancel");
        int action = Inputter.getIntInput(scanner, "Choose an action: ");
        if (action == 1 || action == 2) {
            int amount = Inputter.getValidPositiveInt(scanner, "Enter amount: ");
            boolean success = (action == 1) 
                ? collectionManager.increaseCount(card.getName(), amount)
                : collectionManager.decreaseCount(card.getName(), amount);
            if (success) System.out.println("Count updated."); else System.out.println("Operation failed (e.g., not enough cards).");
        }
    }

    // --- Binder Handlers ---
    public void handleViewBinders() {
        Binder binder = selectBinder("Select a Binder to View");
        if (binder != null) {
            ArrayList<Card> cards = binder.getCards();
            if (cards.isEmpty()) { System.out.println("This binder is empty."); return; }
            cards.sort(Comparator.comparing(Card::getName));
            System.out.println(Display.getHeader("CARDS IN " + binder.getName().toUpperCase()));
            for (Card c : cards) {
                System.out.println("- " + c.getName());
            }
            System.out.println("=========================================");
        }
    }

    public void handleCreateBinder() {
        System.out.println(Display.getHeader("CREATE BINDER"));
        String name = Inputter.getValidName(scanner, "Enter name for new binder: ");
        if (binderManager.createBinder(name)) System.out.println("Binder created successfully.");
    }

    public void handleDeleteBinder() {
        Binder binder = selectBinder("Select a Binder to Delete");
        if (binder != null) {
            String confirm = Inputter.getStringInput(scanner, "Delete '"+binder.getName()+"'? All cards will be returned. (yes/no): ");
            if (confirm.equalsIgnoreCase("yes") && binderManager.deleteBinder(binder.getName())) System.out.println("Binder deleted.");
        }
    }

    public void handleAddCardToBinder() {
        Binder binder = selectBinder("Select a Binder to add a card to");
        if (binder == null) return;
        Card card = selectCardFromCollection("Select a card to add");
        if (card == null) return;
        
        int result = binderManager.addCardToBinder(card.getName(), binder.getName());
        if (result == 0) System.out.println("Card added.");
        else System.out.println("Failed to add card. (Not found, no copies, or binder full)");
    }
    
    public void handleRemoveCardFromBinder() {
        Binder binder = selectBinder("Select a Binder to remove a card from");
        if (binder == null) return;
        Card card = selectCardFromList(binder.getCards(), "Select a card to remove from "+binder.getName());
        if (card == null) return;

        int originalIndex = findCardIndexInList(binder.getCards(), card.getName());
        if (binderManager.removeCardFromBinder(originalIndex, binder.getName())) System.out.println("Card removed and returned to collection.");
        else System.out.println("Failed to remove card.");
    }
    
    public void handleTradeFromBinder() {
        Binder binder = selectBinder("Select a Binder to trade from");
        if (binder == null || binder.getCards().isEmpty()) { System.out.println("Binder is empty or does not exist."); return; }
        
        Card outgoingCard = selectCardFromList(binder.getCards(), "Select a card to TRADE AWAY");
        if (outgoingCard == null) return;

        System.out.println(Display.getHeader("INCOMING CARD DETAILS"));
        String inName = Inputter.getValidName(scanner, "Enter name: ");
        Rarity inRarity = Inputter.getValidRarity(scanner);
        Variant inVariant = Inputter.getValidVariant(scanner, inRarity);
        double inValue = Inputter.getValidPositiveDouble(scanner, "Enter base value: ");

        try {
            Card incomingCard = new Card(inName, inValue, inRarity, inVariant);
            double diff = Math.abs(outgoingCard.getCalculatedValue() - incomingCard.getCalculatedValue());
            System.out.printf("Value difference is $%.2f.\n", diff);
            if (diff >= 1.0) {
                String confirm = Inputter.getStringInput(scanner, "This may be an unfair trade. Proceed? (yes/no): ");
                if (!confirm.equalsIgnoreCase("yes")) { System.out.println("Trade canceled."); return; }
            }
            int outgoingIndex = findCardIndexInList(binder.getCards(), outgoingCard.getName());
            if (binderManager.performTrade(binder.getName(), outgoingIndex, incomingCard)) System.out.println("Trade successful!");
            else System.out.println("Trade failed.");
        } catch (IllegalArgumentException e) {
            System.out.println("Trade failed: " + e.getMessage());
        }
    }

    // --- Deck Handlers ---
    public void handleCreateDeck() {
        System.out.println(Display.getHeader("CREATE DECK"));
        String name = Inputter.getValidName(scanner, "Enter name for new deck: ");
        if(deckManager.createDeck(name)) System.out.println("Deck created successfully.");
    }
    public void handleViewDecks() {
        Deck deck = selectDeck("Select a Deck to View");
        if (deck != null) {
            ArrayList<Card> cards = deck.getCards();
            if (cards.isEmpty()) { System.out.println("This deck is empty."); return; }
            cards.sort(Comparator.comparing(Card::getName));
            System.out.println(Display.getHeader("CARDS IN " + deck.getName().toUpperCase()));
            for (Card c : cards) { System.out.println("- " + c.getName()); }
            System.out.println("=========================================");
        }
    }
    public void handleAddCardToDeck() {
        Deck deck = selectDeck("Select a Deck to add a card to");
        if (deck == null) return;
        Card card = selectCardFromCollection("Select a card to add");
        if (card == null) return;
        int result = deckManager.addCardToDeck(card.getName(), deck.getName());
        if (result == 0) System.out.println("Card added.");
        else System.out.println("Failed to add card. (Not found, no copies, deck full, or duplicate)");
    }
    public void handleRemoveCardFromDeck() {
        Deck deck = selectDeck("Select a Deck to remove a card from");
        if (deck == null) return;
        Card card = selectCardFromList(deck.getCards(), "Select a card to remove from " + deck.getName());
        if (card == null) return;
        int originalIndex = findCardIndexInList(deck.getCards(), card.getName());
        if (deckManager.removeCardFromDeck(originalIndex, deck.getName())) System.out.println("Card removed and returned to collection.");
    }
    public void handleDeleteDeck() {
        Deck deck = selectDeck("Select a Deck to Delete");
        if (deck != null) {
            String confirm = Inputter.getStringInput(scanner, "Delete '"+deck.getName()+"'? (yes/no): ");
            if (confirm.equalsIgnoreCase("yes") && deckManager.deleteDeck(deck.getName())) System.out.println("Deck deleted.");
        }
    }

    // --- Private Selection Helpers ---
    private Card selectCardFromCollection(String prompt) {
        ArrayList<Card> cardTypes = collectionManager.getCardTypes();
        if (cardTypes.isEmpty()) { System.out.println("Collection is empty. Nothing to select."); return null; }
        
        cardTypes.sort(Comparator.comparing(Card::getName));
        System.out.println(Display.getHeader(prompt.toUpperCase()));
        System.out.println(" (0) Back");
        for (int i = 0; i < cardTypes.size(); i++) {
            Card c = cardTypes.get(i);
            System.out.printf(" (%d) %s (Count: %d)\n", i+1, c.getName(), collectionManager.getCardCount(c.getName()));
        }
        System.out.println("=========================================");
        int choice = Inputter.getIntInput(scanner, "Select a card: ");
        if (choice > 0 && choice <= cardTypes.size()) return cardTypes.get(choice - 1);
        return null;
    }
    private Card selectCardFromList(ArrayList<Card> list, String prompt) {
        if (list.isEmpty()) { System.out.println("There are no cards to select."); return null; }
        list.sort(Comparator.comparing(Card::getName));
        System.out.println(Display.getHeader(prompt.toUpperCase()));
        System.out.println(" (0) Back");
        for (int i = 0; i < list.size(); i++) { System.out.printf(" (%d) %s\n", i + 1, list.get(i).getName()); }
        System.out.println("=========================================");
        int choice = Inputter.getIntInput(scanner, "Select a card: ");
        if (choice > 0 && choice <= list.size()) return list.get(choice - 1);
        return null;
    }
    private Binder selectBinder(String prompt) {
        ArrayList<Binder> binders = binderManager.getBinders();
        if (binders.isEmpty()) { System.out.println("No binders exist."); return null; }
        ArrayList<String> names = new ArrayList<>();
        for (Binder b : binders) names.add(b.getName() + " ("+b.getCardCount()+"/"+Binder.MAX_CAPACITY+")");
        System.out.println(Display.getList(names, prompt));
        int choice = Inputter.getIntInput(scanner, "Select a binder: ");
        if (choice > 0 && choice <= binders.size()) {
            binders.sort(Comparator.comparing(Binder::getName));
            return binders.get(choice-1);
        }
        return null;
    }
    private Deck selectDeck(String prompt) {
        ArrayList<Deck> decks = deckManager.getDecks();
        if (decks.isEmpty()) { System.out.println("No decks exist."); return null; }
        ArrayList<String> names = new ArrayList<>();
        for (Deck d : decks) names.add(d.getName() + " ("+d.getCardCount()+"/"+Deck.MAX_CAPACITY+")");
        System.out.println(Display.getList(names, prompt));
        int choice = Inputter.getIntInput(scanner, "Select a deck: ");
        if (choice > 0 && choice <= decks.size()) {
            decks.sort(Comparator.comparing(Deck::getName));
            return decks.get(choice-1);
        }
        return null;
    }
    private int findCardIndexInList(ArrayList<Card> list, String name) {
        for (int i=0; i < list.size(); i++) if(list.get(i).getName().equalsIgnoreCase(name)) return i;
        return -1;
    }
}
