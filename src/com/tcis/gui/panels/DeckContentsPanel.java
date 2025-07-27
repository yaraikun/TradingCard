package com.tcis.gui.panels;

import com.tcis.InventorySystem;
import com.tcis.gui.main.MainFrame;
import com.tcis.models.card.Card;
import com.tcis.models.deck.Deck;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * A JPanel that displays the contents of a single, specific deck.
 * It provides the user interface for adding cards from the main collection into the deck
 * and removing cards from the deck.
 */
public class DeckContentsPanel extends JPanel {
    private final InventorySystem inventory;
    private final MainFrame mainFrame;
    private Deck currentDeck;

    private DefaultListModel<String> deckListModel;
    private JList<String> deckCardList;
    private DefaultListModel<String> collectionListModel;
    private JList<String> collectionCardList;

    private JLabel deckTitleLabel;

    /**
     * Constructs the DeckContentsPanel.
     * @param mainFrame The main application window for navigation.
     * @param inventory The backend facade for all application logic.
     */
    public DeckContentsPanel(MainFrame mainFrame, InventorySystem inventory) {
        this.mainFrame = mainFrame;
        this.inventory = inventory;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        deckTitleLabel = new JLabel("Deck Contents", SwingConstants.CENTER);
        deckTitleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(deckTitleLabel, BorderLayout.NORTH);

        JPanel listsPanel = new JPanel(new GridLayout(1, 2, 10, 0));

        deckListModel = new DefaultListModel<>();
        deckCardList = new JList<>(deckListModel);
        deckCardList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane deckScrollPane = new JScrollPane(deckCardList);
        listsPanel.add(deckScrollPane);

        collectionListModel = new DefaultListModel<>();
        collectionCardList = new JList<>(collectionListModel);
        collectionCardList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane collectionScrollPane = new JScrollPane(collectionCardList);
        collectionScrollPane.setBorder(BorderFactory.createTitledBorder("Available in Collection"));
        listsPanel.add(collectionScrollPane);

        add(listsPanel, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));

        JButton addCardButton = new JButton("<< Add Selected Card");
        JButton removeCardButton = new JButton("Remove Selected Card >>");
        
        Dimension buttonSize = new Dimension(200, 40);
        addCardButton.setPreferredSize(buttonSize);
        removeCardButton.setPreferredSize(buttonSize);
        addCardButton.setMaximumSize(buttonSize);
        removeCardButton.setMaximumSize(buttonSize);
        addCardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeCardButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        actionPanel.add(Box.createVerticalGlue());
        actionPanel.add(addCardButton);
        actionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        actionPanel.add(removeCardButton);
        actionPanel.add(Box.createVerticalGlue());

        JPanel actionWrapperPanel = new JPanel(new BorderLayout());
        actionWrapperPanel.add(actionPanel, BorderLayout.CENTER);
        add(actionWrapperPanel, BorderLayout.EAST);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back to Deck List");
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> mainFrame.showPanel("deckPanel"));
        addCardButton.addActionListener(e -> handleAddCard());
        removeCardButton.addActionListener(e -> handleRemoveCard());
    }

    public void loadDeck(String deckName) {
        this.currentDeck = inventory.findDeck(deckName);
        if (currentDeck == null) {
            JOptionPane.showMessageDialog(mainFrame, "Could not load deck.", "Error", JOptionPane.ERROR_MESSAGE);
            mainFrame.showPanel("deckPanel");
            return;
        }
        deckTitleLabel.setText("Managing: " + currentDeck.getName());
        refreshView();
    }
    
    private void refreshView() {
        if (currentDeck == null) return;
        
        deckListModel.clear();
        ArrayList<Card> deckCards = currentDeck.getCards();
        deckCards.sort(Comparator.comparing(Card::getName));
        for (Card card : deckCards) {
            deckListModel.addElement(card.getName());
        }

        collectionListModel.clear();
        ArrayList<Card> collectionCards = inventory.getCardTypes();
        HashMap<String, Integer> counts = inventory.getCardCounts();
        collectionCards.sort(Comparator.comparing(Card::getName));
        for (Card card : collectionCards) {
            int count = counts.getOrDefault(card.getName().toLowerCase(), 0);
            if (count > 0) {
                collectionListModel.addElement(String.format("%s (Available: %d)", card.getName(), count));
            }
        }
        
        JScrollPane deckScrollPane = (JScrollPane) deckCardList.getParent().getParent();
        deckScrollPane.setBorder(BorderFactory.createTitledBorder("Cards in Deck (" + currentDeck.getCardCount() + "/" + Deck.MAX_CAPACITY + ")"));
    }

    private void handleAddCard() {
        String selectedValue = collectionCardList.getSelectedValue();
        if (selectedValue == null) {
            JOptionPane.showMessageDialog(this, "Please select a card from the 'Available in Collection' list.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cardName = selectedValue.split(" \\(")[0];
        int result = inventory.addCardToDeck(cardName, currentDeck.getName());

        if (result != 0) {
            String error = "An unknown error occurred.";
            if (result == 4) error = "This card is already in the deck.";
            if (result == 3) error = "This deck is full.";
            if (result == 2) error = "No available copies of this card.";
            if (result == 1) error = "Card or Deck not found.";
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
        }
        refreshView();
    }

    private void handleRemoveCard() {
        String selectedValue = deckCardList.getSelectedValue();
        if (selectedValue == null) {
            JOptionPane.showMessageDialog(this, "Please select a card from the 'Cards in Deck' list.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String cardName = selectedValue;

        int originalIndex = -1;
        ArrayList<Card> originalCards = inventory.findDeck(currentDeck.getName()).getCards();
        for (int i = 0; i < originalCards.size(); i++) {
            if (originalCards.get(i).getName().equals(cardName)) {
                originalIndex = i;
                break;
            }
        }

        if (originalIndex != -1 && inventory.removeCardFromDeck(originalIndex, currentDeck.getName())) {
            // Success is silent I'm done refactoring this.
        } else {
            JOptionPane.showMessageDialog(this, "An error occurred while removing the card.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        refreshView();
    }
}
