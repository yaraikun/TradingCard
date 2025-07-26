package com.tcis.gui;

import com.tcis.InventorySystem;
import com.tcis.gui.main.MainFrame;
import com.tcis.models.card.Card;
import com.tcis.models.deck.Deck;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * A JPanel that serves as the main screen for managing all decks.
 * It displays a list of existing decks and provides options to create, view,
 * sell, or delete them. This panel is intended to be a "card" in the MainFrame's
 * CardLayout.
 */
public class DeckPanel extends JPanel {
    private final InventorySystem inventory;
    private final MainFrame mainFrame;
    private DefaultListModel<String> deckListModel;
    private JList<String> deckList;
    private JButton viewButton;
    private JButton sellButton;
    private JButton deleteButton;

    /**
     * Constructs the DeckPanel.
     * @param mainFrame The main application window, used for navigation (switching panels).
     * @param inventory The backend facade for all application logic.
     */
    public DeckPanel(MainFrame mainFrame, InventorySystem inventory) {
        this.mainFrame = mainFrame;
        this.inventory = inventory;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Title ---
        JLabel titleLabel = new JLabel("Deck Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // --- Center: Deck List ---
        deckListModel = new DefaultListModel<>();
        deckList = new JList<>(deckListModel);
        deckList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(deckList);
        add(scrollPane, BorderLayout.CENTER);

        // --- Right: Action Buttons ---
        JPanel actionButtonPanel = new JPanel();
        actionButtonPanel.setLayout(new BoxLayout(actionButtonPanel, BoxLayout.Y_AXIS));

        JButton createBtn = new JButton("Create Deck...");
        viewButton = new JButton("View Contents...");
        sellButton = new JButton("Sell Deck...");
        deleteButton = new JButton("Delete Deck...");

        Dimension buttonSize = new Dimension(180, 40);
        createBtn.setPreferredSize(buttonSize);
        viewButton.setPreferredSize(buttonSize);
        sellButton.setPreferredSize(buttonSize);
        deleteButton.setPreferredSize(buttonSize);
        createBtn.setMaximumSize(buttonSize);
        viewButton.setMaximumSize(buttonSize);
        sellButton.setMaximumSize(buttonSize);
        deleteButton.setMaximumSize(buttonSize);

        actionButtonPanel.add(createBtn);
        actionButtonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        actionButtonPanel.add(viewButton);
        actionButtonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        actionButtonPanel.add(sellButton);
        actionButtonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        actionButtonPanel.add(deleteButton);
        add(actionButtonPanel, BorderLayout.EAST);

        // --- Bottom: Back Button ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back to Main Menu");
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- Add Listeners ---
        deckList.addListSelectionListener(e -> updateButtonStates());

        backButton.addActionListener(e -> mainFrame.showPanel("mainMenu"));
        createBtn.addActionListener(e -> handleCreate());
        viewButton.addActionListener(e -> handleView());
        deleteButton.addActionListener(e -> handleDelete());
        sellButton.addActionListener(e -> handleSell());

        // Initial State
        updateButtonStates();
    }

    /**
     * Public method to refresh the entire view. It re-fetches data from the
     * backend and updates the UI components.
     */
    public void refreshView() {
        deckListModel.clear();
        ArrayList<Deck> decks = inventory.getDecks();
        decks.sort(Comparator.comparing(Deck::getName));
        for (Deck deck : decks) {
            String type = deck.isSellable() ? "Sellable" : "Normal";
            deckListModel.addElement(String.format("%s (%s) [%d/%d]", deck.getName(), type, deck.getCardCount(), Deck.MAX_CAPACITY));
        }
        updateButtonStates();
    }

    /**
     * Enables or disables action buttons based on the current list selection.
     */
    private void updateButtonStates() {
        int selectedIndex = deckList.getSelectedIndex();
        boolean isSelected = selectedIndex != -1;

        viewButton.setEnabled(isSelected);
        deleteButton.setEnabled(isSelected);

        if (isSelected) {
            String selectedValue = deckListModel.getElementAt(selectedIndex);
            String deckName = selectedValue.split(" \\(")[0];
            Deck deck = inventory.findDeck(deckName);
            sellButton.setEnabled(deck != null && deck.isSellable());
        } else {
            sellButton.setEnabled(false);
        }
    }

    /**
     * Handles the workflow for creating a new deck using a custom JOptionPane.
     */
    private void handleCreate() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        JTextField nameField = new JTextField();
        String[] deckTypes = {"Normal", "Sellable"};
        JComboBox<String> typeComboBox = new JComboBox<>(deckTypes);

        panel.add(new JLabel("Deck Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Deck Type:"));
        panel.add(typeComboBox);

        int result = JOptionPane.showConfirmDialog(mainFrame, panel, "Create New Deck",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String type = (String) typeComboBox.getSelectedItem();

            if (inventory.createDeck(name, type)) {
                JOptionPane.showMessageDialog(mainFrame, "Deck '" + name + "' created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshView();
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Failed to create deck. The name might be blank or already exist.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Handles opening a simple dialog to view the contents of the selected deck.
     */
    private void handleView() {
        String selectedValue = deckList.getSelectedValue();
        if (selectedValue == null) return;

        String deckName = selectedValue.split(" \\(")[0];
        Deck deck = inventory.findDeck(deckName);
        if (deck != null) {
            ArrayList<Card> cards = deck.getCards();
            cards.sort(Comparator.comparing(Card::getName));
            StringBuilder content = new StringBuilder();
            if (cards.isEmpty()){
                content.append("This deck is empty.");
            } else {
                for(Card card : cards){
                    content.append("- ").append(card.getName()).append("\n");
                }
            }
            // A dedicated DeckContentsDialog would be better for complex interactions,
            // but JOptionPane is sufficient for a simple view.
            JOptionPane.showMessageDialog(mainFrame, new JScrollPane(new JTextArea(content.toString())), "Contents of " + deck.getName(), JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Handles the workflow for deleting the selected deck.
     */
    private void handleDelete() {
        String selectedValue = deckList.getSelectedValue();
        if (selectedValue == null) return;

        String deckName = selectedValue.split(" \\(")[0];
        int choice = JOptionPane.showConfirmDialog(
            mainFrame,
            "Delete deck '" + deckName + "'? All cards will be returned to your collection.",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            if (inventory.deleteDeck(deckName)) {
                JOptionPane.showMessageDialog(mainFrame, "Deck deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshView();
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Failed to delete the deck.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Handles the workflow for selling the selected deck.
     */
    private void handleSell() {
        String selectedValue = deckList.getSelectedValue();
        if (selectedValue == null) return;

        String deckName = selectedValue.split(" \\(")[0];
        Deck deck = inventory.findDeck(deckName);
        if (deck == null) return;

        double price = 0;
        for (Card card : deck.getCards()) {
            price += card.getCalculatedValue();
        }
        
        int choice = JOptionPane.showConfirmDialog(
            mainFrame,
            String.format("Sell deck '%s' for a total of $%.2f? This action is permanent.", deckName, price),
            "Confirm Sale",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            if (inventory.sellDeck(deckName)) {
                JOptionPane.showMessageDialog(mainFrame, "Deck sold successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshView();
                mainFrame.updateTotalMoney();
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Failed to sell the deck.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
