package com.tcis.gui.panels;

import com.tcis.gui.main.MainFrame;
import javax.swing.*;
import java.awt.*;

/**
 * A JPanel that serves as the main menu or "home screen" of the application.
 * It contains navigation buttons to access the primary features of the system.
 * This panel is intended to be the default "card" in the MainFrame's CardLayout.
 */
public class MainMenuPanel extends JPanel {
    private final MainFrame mainFrame;

    /**
     * Constructs the MainMenuPanel.
     * @param mainFrame The main application window, which acts as the navigation controller.
     */
    public MainMenuPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Main Menu", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

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

        // Add an inner panel to prevent buttons from stretching to fill the whole space
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.add(buttonPanel);
        add(centerWrapper, BorderLayout.CENTER);

        // --- Action Listeners for Buttons ---
        manageCollectionBtn.addActionListener(e -> mainFrame.showPanel("collectionPanel"));
        manageBindersBtn.addActionListener(e -> mainFrame.showPanel("binderPanel"));
        manageDecksBtn.addActionListener(e -> mainFrame.showPanel("deckPanel"));
    }
}
