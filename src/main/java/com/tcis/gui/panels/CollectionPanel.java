package com.tcis.gui.panels;

import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import com.tcis.InventorySystem;
import com.tcis.gui.main.MainFrame;
import com.tcis.models.card.Card;
import com.tcis.models.card.Rarity;
import com.tcis.models.card.Variant;

/**
 * A JPanel that serves as the main screen for managing the card collection.
 *
 * <p>It displays a list of all unique cards and their counts and provides
 * actions to add, sell, or update card quantities. This panel is intended to
 * be a "card" in the MainFrame's CardLayout.</p>
 */
public class CollectionPanel extends JPanel {
    private final InventorySystem inventory;
    private final MainFrame mainFrame;
    private DefaultListModel<String> cardListModel;
    private JList<String> cardList;
    private JButton sellCardButton;
    private JButton updateCountButton;
    private JButton viewDetailsButton;

    /**
     * Constructs the CollectionPanel.
     *
     * @param mainFrame The main application window, used for navigation and
     *                  updating the money label.
     * @param inventory The backend facade for all application logic.
     */
    public CollectionPanel(MainFrame mainFrame, InventorySystem inventory) {
        this.mainFrame = mainFrame;
        this.inventory = inventory;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Card Collection", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        cardListModel = new DefaultListModel<>();
        cardList = new JList<>(cardListModel);
        cardList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(cardList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel actionButtonPanel = new JPanel();
        actionButtonPanel.setLayout(
            new BoxLayout(actionButtonPanel, BoxLayout.Y_AXIS));
        
        JButton addCardButton = new JButton("Add New Card...");
        viewDetailsButton = new JButton("View Details...");
        updateCountButton = new JButton("Update Count...");
        sellCardButton = new JButton("Sell Card...");

        Dimension buttonSize = new Dimension(180, 40);
        addCardButton.setPreferredSize(buttonSize);
        viewDetailsButton.setPreferredSize(buttonSize);
        updateCountButton.setPreferredSize(buttonSize);
        sellCardButton.setPreferredSize(buttonSize);
        addCardButton.setMaximumSize(buttonSize);
        viewDetailsButton.setMaximumSize(buttonSize);
        updateCountButton.setMaximumSize(buttonSize);
        sellCardButton.setMaximumSize(buttonSize);
        
        actionButtonPanel.add(addCardButton);
        actionButtonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        actionButtonPanel.add(viewDetailsButton);
        actionButtonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        actionButtonPanel.add(updateCountButton);
        actionButtonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        actionButtonPanel.add(sellCardButton);
        add(actionButtonPanel, BorderLayout.EAST);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back to Main Menu");
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        cardList.addListSelectionListener(e -> updateButtonStates());
        backButton.addActionListener(e -> mainFrame.showPanel("mainMenu"));
        addCardButton.addActionListener(e -> handleAddCard());
        viewDetailsButton.addActionListener(e -> handleViewDetails());
        updateCountButton.addActionListener(e -> handleUpdateCount());
        sellCardButton.addActionListener(e -> handleSellCard());

        updateButtonStates();
    }

    /**
     * Refreshes the entire view. It re-fetches data from the backend, sorts
     * it, and updates the UI components.
     */
    public void refreshView() {
        int selectedIndex = cardList.getSelectedIndex();

        cardListModel.clear();
        ArrayList<Card> cardTypes = inventory.getCardTypes();
        HashMap<String, Integer> cardCounts = inventory.getCardCounts();
        
        cardTypes.sort(Comparator.comparing(Card::getName));

        for (Card card : cardTypes) {
            int count = cardCounts.getOrDefault(
                card.getName().toLowerCase(), 0);
            cardListModel.addElement(
                String.format("%s (Count: %d)", card.getName(), count));
        }

        if (selectedIndex >= 0 && selectedIndex < cardListModel.getSize())
            cardList.setSelectedIndex(selectedIndex);
        
        updateButtonStates();
    }
    
    /**
     * Enables or disables action buttons based on the current list selection
     * and card count.
     */
    private void updateButtonStates() {
        int selectedIndex = cardList.getSelectedIndex();
        boolean isSelected = selectedIndex != -1;

        viewDetailsButton.setEnabled(isSelected);
        updateCountButton.setEnabled(isSelected);

        if (isSelected) {
            String selectedValue = cardListModel.getElementAt(selectedIndex);
            String cardName = selectedValue.split(" \\(Count:")[0];
            sellCardButton.setEnabled(inventory.isCardAvailable(cardName));
        } else {
            sellCardButton.setEnabled(false);
        }
    }

    /**
     * Handles the workflow for adding a new card using a custom JOptionPane.
     */
    private void handleAddCard() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        JTextField nameField = new JTextField();
        JComboBox<Rarity> rarityComboBox = new JComboBox<>(Rarity.values());
        JComboBox<Variant> variantComboBox = new JComboBox<>(Variant.values());
        JTextField valueField = new JTextField();

        panel.add(new JLabel("Card Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Rarity:"));
        panel.add(rarityComboBox);
        panel.add(new JLabel("Variant:"));
        panel.add(variantComboBox);
        panel.add(new JLabel("Base Value ($):"));
        panel.add(valueField);

        Runnable updateVariantState = () -> {
            Rarity selectedRarity = (Rarity) rarityComboBox.getSelectedItem();
            boolean isSpecial = selectedRarity == Rarity.RARE ||
                                selectedRarity == Rarity.LEGENDARY;
            variantComboBox.setEnabled(isSpecial);
            if (!isSpecial)
                variantComboBox.setSelectedItem(Variant.NORMAL);
        };
        rarityComboBox.addActionListener(e -> updateVariantState.run());
        updateVariantState.run();

        int result = JOptionPane.showConfirmDialog(mainFrame, panel,
            "Add New Card", JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION)
            try {
                String name = nameField.getText();
                double value = Double.parseDouble(valueField.getText());
                Rarity rarity = (Rarity) rarityComboBox.getSelectedItem();
                Variant variant = (Variant) variantComboBox.getSelectedItem();

                if (inventory.addNewCard(name, value, rarity, variant)) {
                    JOptionPane.showMessageDialog(mainFrame, "Card '" + name +
                        "' added successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                    refreshView();
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Failed to add " +
                        "card. Check name is unique and details are valid.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainFrame,
                    "Base value must be a valid number.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            }
    }

    /**
     * Handles showing the details of the selected card in a message dialog.
     */
    private void handleViewDetails() {
        String selectedValue = cardList.getSelectedValue();
        if (selectedValue == null)
            return;
        
        String cardName = selectedValue.split(" \\(Count:")[0];
        Card card = inventory.findCard(cardName);
        
        if (card != null) {
            String details = String.format(
                "Name: %s\nRarity: %s\nVariant: %s\nBase Value: $%.2f\n" +
                "Calculated Value: $%.2f",
                card.getName(), card.getRarity().getDisplayName(),
                card.getVariant().getDisplayName(), card.getBaseValue(),
                card.getCalculatedValue());
            JOptionPane.showMessageDialog(mainFrame, details,
                "Card Details: " + card.getName(),
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Handles the workflow for increasing or decreasing the count of the
     * selected card.
     */
    private void handleUpdateCount() {
        String selectedValue = cardList.getSelectedValue();
        if (selectedValue == null)
            return;

        String cardName = selectedValue.split(" \\(Count:")[0];
        
        Object[] options = {"Increase", "Decrease", "Cancel"};
        int action = JOptionPane.showOptionDialog(mainFrame,
            "Update count for '" + cardName + "'?", "Update Card Count",
            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
            null, options, options[2]);

        if (action == 0 || action == 1) {
            String amountStr = JOptionPane.showInputDialog(
                mainFrame, "Enter amount:");
            if (amountStr != null)
                try {
                    int amount = Integer.parseInt(amountStr);
                    if (amount <= 0) {
                        JOptionPane.showMessageDialog(mainFrame,
                            "Amount must be a positive number.", "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    boolean success = (action == 0)
                        ? inventory.increaseCardCount(cardName, amount)
                        : inventory.decreaseCardCount(cardName, amount);
                    if (success) {
                        JOptionPane.showMessageDialog(mainFrame,
                            "Count updated.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                        refreshView();
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "Operation " +
                            "failed. Not enough cards to decrease.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(mainFrame,
                        "Please enter a valid whole number.", "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                }
        }
    }

    /**
     * Handles the workflow for selling the selected card.
     */
    private void handleSellCard() {
        String selectedValue = cardList.getSelectedValue();
        if (selectedValue == null)
            return;
        
        String cardName = selectedValue.split(" \\(Count:")[0];
        Card cardToSell = inventory.findCard(cardName);
        if (cardToSell == null)
            return;

        int choice = JOptionPane.showConfirmDialog(mainFrame, 
            String.format("Sell one '%s' for $%.2f?",
            cardName, cardToSell.getCalculatedValue()),
            "Confirm Sale", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            if (inventory.sellCardFromCollection(cardName)) {
                JOptionPane.showMessageDialog(mainFrame,
                    "Card sold successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                refreshView();
                mainFrame.updateTotalMoney();
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Failed to sell " +
                    "card. Not enough copies available.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
