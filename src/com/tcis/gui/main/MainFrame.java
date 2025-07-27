package com.tcis.gui.main;

import com.tcis.InventorySystem;
import com.tcis.gui.panels.*;
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
    private BinderContentsPanel binderContentsPanel;
    private DeckContentsPanel deckContentsPanel;

    /**
     * Constructs the MainFrame, initializing the CardLayout and all associated panels.
     * @param inventory The backend facade which provides all necessary data and logic.
     */
    public MainFrame(InventorySystem inventory) {
        this.inventory = inventory;

        setTitle("Trading Card Inventory System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // --- Create all the panels (views) ---
        mainMenuPanel = new MainMenuPanel(this);
        collectionPanel = new CollectionPanel(this, inventory);
        binderPanel = new BinderPanel(this, inventory);
        deckPanel = new DeckPanel(this, inventory);
        binderContentsPanel = new BinderContentsPanel(this, inventory);
        deckContentsPanel = new DeckContentsPanel(this, inventory);

        // --- Add panels to the CardLayout with unique string identifiers ---
        mainPanel.add(mainMenuPanel, "mainMenu");
        mainPanel.add(collectionPanel, "collectionPanel");
        mainPanel.add(binderPanel, "binderPanel");
        mainPanel.add(deckPanel, "deckPanel");
        mainPanel.add(binderContentsPanel, "binderContentsPanel");
        mainPanel.add(deckContentsPanel, "deckContentsPanel");

        // --- Top Panel for Title and Money (this will be static across all views) ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("TCIS MCO2");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        totalMoneyLabel = new JLabel();
        totalMoneyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        updateTotalMoney();

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(totalMoneyLabel, BorderLayout.EAST);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(mainPanel, BorderLayout.CENTER);

        cardLayout.show(mainPanel, "mainMenu");
    }

    /**
     * Public method that allows other panels to request a switch to a different panel.
     * This is the core navigation method for the entire GUI. Before showing a panel,
     * it calls that panel's refresh method to ensure data is up-to-date.
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
     * A specialized method to show the binder contents panel, ensuring it's loaded
     * with the correct binder's data before being displayed.
     * @param binderName The name of the binder whose contents should be displayed.
     */
    public void showBinderContents(String binderName) {
        binderContentsPanel.loadBinder(binderName);
        cardLayout.show(mainPanel, "binderContentsPanel");
    }

    /**
     * A specialized method to show the deck contents panel, ensuring it's loaded
     * with the correct deck's data before being displayed.
     * @param deckName The name of the deck whose contents should be displayed.
     */
    public void showDeckContents(String deckName) {
        deckContentsPanel.loadDeck(deckName);
        cardLayout.show(mainPanel, "deckContentsPanel");
    }

    /**
     * Public method that allows other panels to request an update of the money display.
     * This is called after any action that changes the user's total money.
     */
    public void updateTotalMoney() {
        totalMoneyLabel.setText(String.format("Total Money: $%.2f", inventory.getTotalMoney()));
    }
}
