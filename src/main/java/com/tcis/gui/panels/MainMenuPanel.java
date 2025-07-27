package com.tcis.gui.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.tcis.gui.main.MainFrame;

/**
 * A JPanel that serves as the main menu or "home screen" of the application.
 *
 * <p>
 * It contains navigation buttons to access the primary features of the
 * system. This panel is intended to be the default "card" in the MainFrame's
 * CardLayout.
 * </p>
 */
public class MainMenuPanel extends JPanel {
    /**
     * A reference to the MainFrame container. This is needed so that this
     * panel's buttons can tell the MainFrame to switch to a different panel.
     */
    private final MainFrame mainFrame;

    /**
     * Constructs the MainMenuPanel.
     *
     * @param mainFrame The main application window, which acts as the
     *                  navigation controller.
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

        // A wrapper panel is used to center the buttons vertically and
        // horizontally within the BorderLayout.CENTER area.
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.add(buttonPanel);
        add(centerWrapper, BorderLayout.CENTER);

        manageCollectionBtn.addActionListener(
                e -> mainFrame.showPanel("collectionPanel"));
        manageBindersBtn.addActionListener(
                e -> mainFrame.showPanel("binderPanel"));
        manageDecksBtn.addActionListener(
                e -> mainFrame.showPanel("deckPanel"));
    }
}
