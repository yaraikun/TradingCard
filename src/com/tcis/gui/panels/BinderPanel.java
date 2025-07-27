package com.tcis.gui.panels;

import com.tcis.InventorySystem;
import com.tcis.gui.main.MainFrame;
import com.tcis.models.binder.Binder;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * A JPanel that serves as the main screen for managing all binders.
 * It displays a list of existing binders and provides options to create, view,
 * sell, or delete them. This panel is intended to be a "card" in the MainFrame's
 * CardLayout.
 */
public class BinderPanel extends JPanel {
    private final InventorySystem inventory;
    private final MainFrame mainFrame;
    private DefaultListModel<String> binderListModel;
    private JList<String> binderList;
    private JButton viewButton;
    private JButton sellButton;
    private JButton deleteButton;

    /**
     * Constructs the BinderPanel.
     * @param mainFrame The main application window for navigation.
     * @param inventory The backend facade for all application logic.
     */
    public BinderPanel(MainFrame mainFrame, InventorySystem inventory) {
        this.mainFrame = mainFrame;
        this.inventory = inventory;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Title ---
        JLabel titleLabel = new JLabel("Binder Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // --- Center: Binder List ---
        binderListModel = new DefaultListModel<>();
        binderList = new JList<>(binderListModel);
        binderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(binderList), BorderLayout.CENTER);

        // --- Right: Action Buttons ---
        JPanel actionButtonPanel = new JPanel();
        actionButtonPanel.setLayout(new BoxLayout(actionButtonPanel, BoxLayout.Y_AXIS));
        
        JButton createBtn = new JButton("Create Binder...");
        viewButton = new JButton("View/Manage Contents...");
        sellButton = new JButton("Sell Binder...");
        deleteButton = new JButton("Delete Binder...");

        Dimension buttonSize = new Dimension(200, 40);
        createBtn.setPreferredSize(buttonSize);
        viewButton.setPreferredSize(buttonSize);
        sellButton.setPreferredSize(buttonSize);
        deleteButton.setPreferredSize(buttonSize);
        createBtn.setMaximumSize(buttonSize);
        viewButton.setMaximumSize(buttonSize);
        sellButton.setMaximumSize(buttonSize);
        deleteButton.setMaximumSize(buttonSize);
        createBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sellButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        actionButtonPanel.add(Box.createVerticalGlue());
        actionButtonPanel.add(createBtn);
        actionButtonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        actionButtonPanel.add(viewButton);
        actionButtonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        actionButtonPanel.add(sellButton);
        actionButtonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        actionButtonPanel.add(deleteButton);
        actionButtonPanel.add(Box.createVerticalGlue());
        
        JPanel actionWrapperPanel = new JPanel(new BorderLayout());
        actionWrapperPanel.add(actionButtonPanel, BorderLayout.CENTER);
        add(actionWrapperPanel, BorderLayout.EAST);

        // --- Bottom: Back Button ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back to Main Menu");
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- Add Listeners ---
        binderList.addListSelectionListener(e -> updateButtonStates());
        backButton.addActionListener(e -> mainFrame.showPanel("mainMenu"));
        createBtn.addActionListener(e -> handleCreate());
        viewButton.addActionListener(e -> handleView());
        deleteButton.addActionListener(e -> handleDelete());
        sellButton.addActionListener(e -> handleSell());
        
        updateButtonStates();
    }

    /**
     * Public method to refresh the entire view. It re-fetches data from the
     * backend and updates the UI components. This should be called by the
     * MainFrame whenever this panel is shown.
     */
    public void refreshView() {
        // Save the currently selected index
        int selectedIndex = binderList.getSelectedIndex();

        binderListModel.clear();
        ArrayList<Binder> binders = inventory.getBinders();
        binders.sort(Comparator.comparing(Binder::getName));
        for (Binder binder : binders) {
            String type = binder.getClass().getSimpleName().replace("Binder", "");
            binderListModel.addElement(String.format("%s (%s) [%d/%d]", binder.getName(), type, binder.getCardCount(), Binder.MAX_CAPACITY));
        }

        // Restore selection if possible
        if (selectedIndex >= 0 && selectedIndex < binderListModel.getSize()) {
            binderList.setSelectedIndex(selectedIndex);
        }

        updateButtonStates();
    }

    /**
     * Enables or disables action buttons based on the current list selection and binder properties.
     */
    private void updateButtonStates() {
        int selectedIndex = binderList.getSelectedIndex();
        boolean isSelected = selectedIndex != -1;

        viewButton.setEnabled(isSelected);
        deleteButton.setEnabled(isSelected);

        if (isSelected) {
            String selectedValue = binderListModel.getElementAt(selectedIndex);
            String binderName = selectedValue.split(" \\(")[0];
            Binder binder = inventory.findBinder(binderName);
            sellButton.setEnabled(binder != null && binder.isSellable());
        } else {
            sellButton.setEnabled(false);
        }
    }

    /**
     * Handles the workflow for creating a new binder using a custom JOptionPane.
     */
    private void handleCreate() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        JTextField nameField = new JTextField();
        String[] binderTypes = {"Non-curated", "Collector", "Pauper", "Rares", "Luxury"};
        JComboBox<String> typeComboBox = new JComboBox<>(binderTypes);

        panel.add(new JLabel("Binder Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Binder Type:"));
        panel.add(typeComboBox);

        int result = JOptionPane.showConfirmDialog(mainFrame, panel, "Create New Binder",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String type = (String) typeComboBox.getSelectedItem();

            if (inventory.createBinder(name, type)) {
                JOptionPane.showMessageDialog(mainFrame, "Binder '" + name + "' created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshView();
            } else {
                // Backend prints a more specific error
                JOptionPane.showMessageDialog(mainFrame, "Failed to create binder. Name may be blank or already in use.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Handles navigating to the BinderContentsPanel for the selected binder.
     */
    private void handleView() {
        String selectedValue = binderList.getSelectedValue();
        if (selectedValue == null) return;
        
        String binderName = selectedValue.split(" \\(")[0];
        // Tell the MainFrame to load and show the contents panel
        mainFrame.showBinderContents(binderName);
    }

    /**
     * Handles the workflow for deleting the selected binder.
     */
    private void handleDelete() {
        String selectedValue = binderList.getSelectedValue();
        if (selectedValue == null) return;

        String binderName = selectedValue.split(" \\(")[0];
        int choice = JOptionPane.showConfirmDialog(
            mainFrame,
            "Delete binder '" + binderName + "'? All cards will be returned to your collection.",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            if (inventory.deleteBinder(binderName)) {
                JOptionPane.showMessageDialog(mainFrame, "Binder deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshView();
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Failed to delete the binder.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Handles the workflow for selling the selected binder.
     */
    private void handleSell() {
        String selectedValue = binderList.getSelectedValue();
        if (selectedValue == null) return;

        String binderName = selectedValue.split(" \\(")[0];
        Binder binder = inventory.findBinder(binderName);

        if (binder == null) return; // Should not happen if selected

        double price = binder.calculatePrice();
        int choice = JOptionPane.showConfirmDialog(
            mainFrame,
            String.format("Sell binder '%s' for $%.2f? This includes any fees. This action is permanent.", binderName, price),
            "Confirm Sale",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            if (inventory.sellBinder(binderName)) {
                JOptionPane.showMessageDialog(mainFrame, "Binder sold successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshView();
                mainFrame.updateTotalMoney();
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Failed to sell the binder.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
