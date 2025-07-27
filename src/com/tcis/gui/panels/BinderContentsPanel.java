package com.tcis.gui.panels;

import com.tcis.InventorySystem;
import com.tcis.gui.main.MainFrame;
import com.tcis.models.binder.Binder;
import com.tcis.models.binder.LuxuryBinder;
import com.tcis.models.card.Card;
import com.tcis.models.card.Rarity;
import com.tcis.models.card.Variant;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * A JPanel that displays the contents of a single, specific binder.
 * It provides the user interface for adding cards from the main collection
 * into the binder, removing cards from the binder, and initiating trades or
 * setting prices.
 */
public class BinderContentsPanel extends JPanel {
    private final InventorySystem inventory;
    private final MainFrame mainFrame;
    private Binder currentBinder;

    private DefaultListModel<String> binderListModel;
    private JList<String> binderCardList;
    private DefaultListModel<String> collectionListModel;
    private JList<String> collectionCardList;

    private JButton addCardButton;
    private JButton removeCardButton;
    private JButton tradeButton;
    private JButton setPriceButton;
    private JLabel binderTitleLabel;

    /**
     * Constructs the BinderContentsPanel.
     * @param mainFrame The main application window for navigation.
     * @param inventory The backend facade for all application logic.
     */
    public BinderContentsPanel(MainFrame mainFrame, InventorySystem inventory) {
        this.mainFrame = mainFrame;
        this.inventory = inventory;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        binderTitleLabel = new JLabel("Binder Contents", SwingConstants.CENTER);
        binderTitleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(binderTitleLabel, BorderLayout.NORTH);

        JPanel listsPanel = new JPanel(new GridLayout(1, 2, 10, 0));

        binderListModel = new DefaultListModel<>();
        binderCardList = new JList<>(binderListModel);
        binderCardList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane binderScrollPane = new JScrollPane(binderCardList);
        listsPanel.add(binderScrollPane);

        collectionListModel = new DefaultListModel<>();
        collectionCardList = new JList<>(collectionListModel);
        collectionCardList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane collectionScrollPane = new JScrollPane(collectionCardList);
        collectionScrollPane.setBorder(BorderFactory.createTitledBorder("Available in Collection"));
        listsPanel.add(collectionScrollPane);

        add(listsPanel, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));

        addCardButton = new JButton("<< Add Selected Card");
        removeCardButton = new JButton("Remove Selected Card >>");
        tradeButton = new JButton("Trade from Binder...");
        setPriceButton = new JButton("Set Custom Price...");
        
        Dimension buttonSize = new Dimension(200, 40);
        addCardButton.setPreferredSize(buttonSize);
        removeCardButton.setPreferredSize(buttonSize);
        tradeButton.setPreferredSize(buttonSize);
        setPriceButton.setPreferredSize(buttonSize);
        addCardButton.setMaximumSize(buttonSize);
        removeCardButton.setMaximumSize(buttonSize);
        tradeButton.setMaximumSize(buttonSize);
        setPriceButton.setMaximumSize(buttonSize);
        addCardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeCardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        tradeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        setPriceButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        actionPanel.add(Box.createVerticalGlue());
        actionPanel.add(addCardButton);
        actionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        actionPanel.add(removeCardButton);
        actionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        actionPanel.add(tradeButton);
        actionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        actionPanel.add(setPriceButton);
        actionPanel.add(Box.createVerticalGlue());

        JPanel actionWrapperPanel = new JPanel(new BorderLayout());
        actionWrapperPanel.add(actionPanel, BorderLayout.CENTER);
        add(actionWrapperPanel, BorderLayout.EAST);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back to Binder List");
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> mainFrame.showPanel("binderPanel"));
        addCardButton.addActionListener(e -> handleAddCard());
        removeCardButton.addActionListener(e -> handleRemoveCard());
        tradeButton.addActionListener(e -> handleTrade());
        setPriceButton.addActionListener(e -> handleSetPrice());
    }

    public void loadBinder(String binderName) {
        this.currentBinder = inventory.findBinder(binderName);

        if (currentBinder == null) {
            JOptionPane.showMessageDialog(mainFrame, "Could not load binder. It may have been deleted.", "Error", JOptionPane.ERROR_MESSAGE);
            mainFrame.showPanel("binderPanel");
            return;
        }

        binderTitleLabel.setText("Managing: " + currentBinder.getName());
        refreshView();
    }
    
    private void refreshView() {
        if (currentBinder == null)
            return;
        
        binderListModel.clear();
        ArrayList<Card> binderCards = currentBinder.getCards();
        binderCards.sort(Comparator.comparing(Card::getName));

        for (Card card : binderCards)
            binderListModel.addElement(card.getName());

        collectionListModel.clear();
        ArrayList<Card> collectionCards = inventory.getCardTypes();
        HashMap<String, Integer> counts = inventory.getCardCounts();
        collectionCards.sort(Comparator.comparing(Card::getName));

        for (Card card : collectionCards) {
            int count = counts.getOrDefault(card.getName().toLowerCase(), 0);

            if (count > 0)
                collectionListModel.addElement(String.format("%s (Available: %d)", card.getName(), count));
        }
        
        JScrollPane binderScrollPane = (JScrollPane) binderCardList.getParent().getParent();
        binderScrollPane.setBorder(BorderFactory.createTitledBorder("Cards in Binder (" + currentBinder.getCardCount() + "/" + Binder.MAX_CAPACITY + ")"));
        updateButtonStates();
    }

    private void updateButtonStates() {
        tradeButton.setEnabled(currentBinder.canTrade());
        setPriceButton.setEnabled(currentBinder instanceof LuxuryBinder);
    }

    private void handleAddCard() {
        String selectedValue = collectionCardList.getSelectedValue();

        if (selectedValue == null) {
            JOptionPane.showMessageDialog(this, "Please select a card from the 'Available in Collection' list to add.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cardName = selectedValue.split(" \\(")[0];
        int result = inventory.addCardToBinder(cardName, currentBinder.getName());

        if (result == 4) {
            JOptionPane.showMessageDialog(this, "This card is not allowed in this type of binder.", "Rule Violation", JOptionPane.ERROR_MESSAGE);
        } else if (result == 3) {
            JOptionPane.showMessageDialog(this, "This binder is full.", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (result != 0) {
            JOptionPane.showMessageDialog(this, "An error occurred while adding the card.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        refreshView();
    }

    private void handleRemoveCard() {
        String selectedValue = binderCardList.getSelectedValue();

        if (selectedValue == null) {
            JOptionPane.showMessageDialog(this, "Please select a card from the 'Cards in Binder' list to remove.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cardName = selectedValue;

        int originalIndex = -1;
        ArrayList<Card> originalCards = inventory.findBinder(currentBinder.getName()).getCards();
        for (int i = 0; i < originalCards.size(); i++) {
            if (originalCards.get(i).getName().equals(cardName)) {
                originalIndex = i;
                break;
            }
        }

        if (originalIndex != -1 && inventory.removeCardFromBinder(originalIndex, currentBinder.getName())) {
        } else {
            JOptionPane.showMessageDialog(this, "An error occurred while removing the card.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        refreshView();
    }

    private void handleTrade() {
        int selectedViewIndex = binderCardList.getSelectedIndex();
        if (selectedViewIndex == -1) {
             JOptionPane.showMessageDialog(this, "Please select a card from the binder to trade away.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String selectedName = binderListModel.getElementAt(selectedViewIndex);

        int outgoingCardIndex = -1;
        Card outgoingCard = null;
        ArrayList<Card> originalCards = inventory.findBinder(currentBinder.getName()).getCards();
        for (int i = 0; i < originalCards.size(); i++) {
            if (originalCards.get(i).getName().equals(selectedName)) {
                outgoingCardIndex = i;
                outgoingCard = originalCards.get(i);
                break;
            }
        }

        if (outgoingCard == null) {
            JOptionPane.showMessageDialog(this, "Could not find selected card. Please refresh.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        JTextField nameField = new JTextField();
        JComboBox<Rarity> rarityComboBox = new JComboBox<>(Rarity.values());
        JComboBox<Variant> variantComboBox = new JComboBox<>(Variant.values());
        JTextField valueField = new JTextField();
        panel.add(new JLabel("Incoming Card Name:")); panel.add(nameField);
        panel.add(new JLabel("Rarity:")); panel.add(rarityComboBox);
        panel.add(new JLabel("Variant:")); panel.add(variantComboBox);
        panel.add(new JLabel("Base Value ($):")); panel.add(valueField);
        
        Runnable updateVariantState = () -> {
            Rarity selectedRarity = (Rarity) rarityComboBox.getSelectedItem();
            boolean isSpecial = selectedRarity == Rarity.RARE || selectedRarity == Rarity.LEGENDARY;
            variantComboBox.setEnabled(isSpecial);
            if (!isSpecial) {
                variantComboBox.setSelectedItem(Variant.NORMAL);
            }
        };
        rarityComboBox.addActionListener(e -> updateVariantState.run());
        updateVariantState.run();
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Enter Incoming Card Details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
             try {
                String inName = nameField.getText();
                Rarity inRarity = (Rarity) rarityComboBox.getSelectedItem();
                Variant inVariant = (Variant) variantComboBox.getSelectedItem();
                double inValue = Double.parseDouble(valueField.getText());
                
                Card incomingCard = new Card(inName, inValue, inRarity, inVariant);
                double diff = Math.abs(outgoingCard.getCalculatedValue() - incomingCard.getCalculatedValue());
                if (diff >= 1.0) {
                    int confirm = JOptionPane.showConfirmDialog(this, String.format("Value difference is $%.2f. This may be an unfair trade. Proceed?", diff), "Confirm Unfair Trade", JOptionPane.YES_NO_OPTION);
                    if (confirm != JOptionPane.YES_OPTION) { System.out.println("Trade canceled by user."); return; }
                }

                if (inventory.performTrade(currentBinder.getName(), outgoingCardIndex, incomingCard)) {
                    JOptionPane.showMessageDialog(this, "Trade successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Trade failed. Check console for details.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                refreshView();
             } catch (Exception e) {
                 JOptionPane.showMessageDialog(this, "Invalid trade details provided: " + e.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
             }
        }
    }
    
    private void handleSetPrice() {
        if (currentBinder instanceof LuxuryBinder) {
            LuxuryBinder luxuryBinder = (LuxuryBinder) currentBinder;
            String priceStr = JOptionPane.showInputDialog(this, "Enter the custom price for this binder:", "Set Custom Price", JOptionPane.PLAIN_MESSAGE);
            if (priceStr != null) {
                try {
                    double price = Double.parseDouble(priceStr);
                    if (luxuryBinder.setPrice(price)) {
                        JOptionPane.showMessageDialog(this, "Price set successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Price cannot be lower than the total value of the cards inside.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
