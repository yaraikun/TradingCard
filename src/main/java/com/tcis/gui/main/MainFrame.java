package com.tcis.gui.main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tcis.InventorySystem;
import com.tcis.gui.panels.BinderContentsPanel;
import com.tcis.gui.panels.BinderPanel;
import com.tcis.gui.panels.CollectionPanel;
import com.tcis.gui.panels.DeckContentsPanel;
import com.tcis.gui.panels.DeckPanel;
import com.tcis.gui.panels.MainMenuPanel;

/**
 * The main and only JFrame for the Trading Card Inventory System application.
 *
 * <p>
 * It uses a CardLayout to manage and switch between different JPanels,
 * creating a single-window user experience. It acts as the central navigator
 * for the GUI, owning all the panel instances and controlling which one is
 * visible at any time.
 * </p>
 */
public class MainFrame extends JFrame {
    /**
     * A reference to the backend facade. This is passed to all child panels
     * so they can interact with the application's logic.
     */
    private final InventorySystem inventory;

    /**
     * The layout manager that enables switching between different panels.
     */
    private final CardLayout cardLayout;

    /**
     * The main container panel that holds all other view panels.
     */
    private final JPanel mainPanel;

    /**
     * The label used to display the user's current total money, updated after
     * any sale.
     */
    private JLabel totalMoneyLabel;

    /**
     * The panel that displays the main menu and primary navigation options.
     */
    private MainMenuPanel mainMenuPanel;

    /**
     * The panel for viewing and managing the entire card collection.
     */
    private CollectionPanel collectionPanel;

    /**
     * The panel for managing the list of binders (create, delete, sell).
     */
    private BinderPanel binderPanel;

    /**
     * The panel for managing the list of decks (create, delete, sell).
     */
    private DeckPanel deckPanel;

    /**
     * The panel for managing the contents of a single, selected binder.
     */
    private BinderContentsPanel binderContentsPanel;

    /**
     * The panel for managing the contents of a single, selected deck.
     */
    private DeckContentsPanel deckContentsPanel;

    /**
     * Constructs the MainFrame, initializing the CardLayout and all associated
     * view panels.
     *
     * @param inventory The backend facade which provides all necessary data
     *                  and logic for the GUI to function.
     */
    public MainFrame(InventorySystem inventory) {
        this.inventory = inventory;

        setTitle("Trading Card Inventory System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainMenuPanel = new MainMenuPanel(this);
        collectionPanel = new CollectionPanel(this, inventory);
        binderPanel = new BinderPanel(this, inventory);
        deckPanel = new DeckPanel(this, inventory);
        binderContentsPanel = new BinderContentsPanel(this, inventory);
        deckContentsPanel = new DeckContentsPanel(this, inventory);

        mainPanel.add(mainMenuPanel, "mainMenu");
        mainPanel.add(collectionPanel, "collectionPanel");
        mainPanel.add(binderPanel, "binderPanel");
        mainPanel.add(deckPanel, "deckPanel");
        mainPanel.add(binderContentsPanel, "binderContentsPanel");
        mainPanel.add(deckContentsPanel, "deckContentsPanel");

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
     * Allows other panels to request a switch to a different panel.
     *
     * <p>
     * This is the core navigation method for the entire GUI. Before showing
     * a panel, it calls that panel's refresh method to ensure its data is
     * up-to-date.
     * </p>
     *
     * @param panelName The string identifier of the panel to show.
     */
    public void showPanel(String panelName) {
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
     * A specialized method to show the binder contents panel, ensuring it's
     * loaded with the correct binder's data before being displayed.
     *
     * @param binderName The name of the binder whose contents should be
     *                   displayed.
     */
    public void showBinderContents(String binderName) {
        binderContentsPanel.loadBinder(binderName);
        cardLayout.show(mainPanel, "binderContentsPanel");
    }

    /**
     * A specialized method to show the deck contents panel, ensuring it's
     * loaded with the correct deck's data before being displayed.
     *
     * @param deckName The name of the deck whose contents should be displayed.
     */
    public void showDeckContents(String deckName) {
        deckContentsPanel.loadDeck(deckName);
        cardLayout.show(mainPanel, "deckContentsPanel");
    }

    /**
     * Allows other panels to request an update of the money display. This is
     * called after any action that changes the user's total money.
     */
    public void updateTotalMoney() {
        totalMoneyLabel.setText(
                String.format("Total Money: $%.2f", inventory.getTotalMoney()));
    }
}
