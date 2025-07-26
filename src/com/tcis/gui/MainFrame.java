package com.tcis.gui.main;

import com.tcis.InventorySystem;
import com.tcis.gui.BinderPanel;
import com.tcis.gui.CollectionPanel;
import com.tcis.gui.DeckPanel;
import com.tcis.gui.MainMenuPanel;
import javax.swing.*;
import java.awt.*;

/**
 * The main and only JFrame for the Trading Card Inventory System application.
 * It uses a CardLayout to manage and switch between different JPanels, creating a
 * single-window user experience. It acts as the central navigator for the GUI.
 */
public class MainFrame extends JFrame {
    private final InventorySystem inventory;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private JLabel totalMoneyLabel;

    // Panels that will be part of the CardLayout
    private MainMenuPanel mainMenuPanel;
    private CollectionPanel collectionPanel;
    private BinderPanel binderPanel;
    private DeckPanel deckPanel;

    /**
     * Constructs the MainFrame, initializing the CardLayout and all associated panels.
     * @param inventory The backend facade which provides all necessary data and logic.
     */
    public MainFrame(InventorySystem inventory) {
        this.inventory = inventory;

        setTitle("Trading Card Inventory System");
        setSize(800, 600); // A more spacious default size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        // --- Setup the CardLayout Panel ---
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // --- Create all the panels (views) ---
        mainMenuPanel = new MainMenuPanel(this);
        collectionPanel = new CollectionPanel(this, inventory);
        binderPanel = new BinderPanel(this, inventory);
        deckPanel = new DeckPanel(this, inventory);

        // --- Add panels to the CardLayout with unique string identifiers ---
        mainPanel.add(mainMenuPanel, "mainMenu");
        mainPanel.add(collectionPanel, "collectionPanel");
        mainPanel.add(binderPanel, "binderPanel");
        mainPanel.add(deckPanel, "deckPanel");

        // --- Top Panel for Title and Money (this will be static across all views) ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("TCIS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        totalMoneyLabel = new JLabel();
        totalMoneyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        updateTotalMoney(); // Set initial value

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(totalMoneyLabel, BorderLayout.EAST);

        // --- Add main components to the frame ---
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(mainPanel, BorderLayout.CENTER);

        // Show the main menu first
        cardLayout.show(mainPanel, "mainMenu");
    }

    /**
     * Public method that allows other panels to request a switch to a different panel.
     * This is the core navigation method for the entire GUI.
     * @param panelName The string identifier of the panel to show.
     */
    public void showPanel(String panelName) {
        // Before showing a panel, refresh its data to ensure it's up-to-date
        switch (panelName) {
            case "collectionPanel":
                collectionPanel.refreshView();
                break;
            case "binderPanel":
                binderPanel.refreshView();
                break;
            case "deckPanel":
                deckPanel.refreshView();
                break;
        }
        cardLayout.show(mainPanel, panelName);
    }

    /**
     * Public method that allows other panels to request an update of the money display.
     * This is called after any action that changes the user's total money.
     */
    public void updateTotalMoney() {
        totalMoneyLabel.setText(String.format("Total Money: $%.2f", inventory.getTotalMoney()));
    }
}
