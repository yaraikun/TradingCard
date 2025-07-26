package com.tcis.gui;

import com.tcis.gui.MainFrame;
import javax.swing.*;
import java.awt.*;

/**
 * A JPanel that serves as the main menu or "home screen" of the application.
 * It contains navigation buttons to access the primary features of the system.
 * This panel is intended to be the default "card" in the MainFrame's CardLayout.
 */
public class MainMenuPanel extends JPanel {

    /**
     * A reference to the MainFrame container. This is needed so that this panel's
     * buttons can tell the MainFrame to switch to a different panel.
     */
    private final MainFrame mainFrame;

    /**
     * Constructs the MainMenuPanel.
     * @param mainFrame The main application window, which acts as the navigation controller.
     */
    public MainMenuPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- Center Panel for Main Action Buttons ---
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 15, 15));

        JButton manageCollectionBtn = new JButton("Manage Collection");
        manageCollectionBtn.setFont(new Font("Arial", Font.BOLD, 16));

        JButton manageBindersBtn = new JButton("Manage Binders");
        manageBindersBtn.setFont(new Font("Arial", Font.BOLD, 16));

        JButton manageDecksBtn = new JButton("Manage Decks");
        manageDecksBtn.setFont(new Font("Arial", Font.BOLD, 16));

        buttonPanel.add(manageCollectionBtn);
        buttonPanel.add(manageBindersBtn);
        buttonPanel.add(manageDecksBtn);

        add(buttonPanel, BorderLayout.CENTER);

        // --- Action Listeners for Buttons ---

        // When a button is clicked, it tells the MainFrame to show the corresponding panel.
        manageCollectionBtn.addActionListener(e -> mainFrame.showPanel("collectionPanel"));
        manageBindersBtn.addActionListener(e -> mainFrame.showPanel("binderPanel"));
        manageDecksBtn.addActionListener(e -> mainFrame.showPanel("deckPanel"));
    }
}
