package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel {
    private JPanel mainPanel;
//    private JPanel actionPanel;

    public MainPanel() {
        setLayout(new BorderLayout());
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Set Title
        JLabel titleLabel = new JLabel("Welcome to Recipe Genie", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Add 4 buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 10, 10));
        JButton searchButton = new JButton("Search Recipes");
        JButton filterButton = new JButton("Set Preferences");
        JButton savedButton = new JButton("View Saved Recipes");
        JButton accountSettings = new JButton("Account Settings");

        buttonPanel.add(searchButton);
        buttonPanel.add(filterButton);
        buttonPanel.add(savedButton);
        buttonPanel.add(accountSettings);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);


        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSearchPanel();
            }
        });
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPreferencesPanel();
            }
        });
        savedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSavedRecipesPanel();
            }
        });
        accountSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAdditionalOnePanel();
            }
        });
    }

    // Search Recipes Part by Zhiyu
    private void showSearchPanel() {
        mainPanel.removeAll();

        // back button
        JButton backButton = createBackButton();
        mainPanel.add(backButton, BorderLayout.NORTH);

        // Search(The main algorithm can be in a new java file)
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));

        JLabel searchLabel = new JLabel("Enter Recipe Name:");
        JTextField searchField = new JTextField(1);
        // TODO: Layout need to be changed
        JButton searchSubmitButton = new JButton("Search");

        searchSubmitButton.addActionListener(e -> {
            String query = searchField.getText();
            JOptionPane.showMessageDialog(this, "Searching for: " + query);
        });

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchSubmitButton);

        mainPanel.add(searchPanel, BorderLayout.CENTER);

        // Repaint
        mainPanel.revalidate();
        mainPanel.repaint();
    }


    // Show Preferences Panel
    private void showPreferencesPanel() {
        mainPanel.removeAll();

        // back button
        JButton backButton = createBackButton();
        mainPanel.add(backButton, BorderLayout.NORTH);

        //TODO: Preferences Settings(Just a Sample here)
        //A bug here.
        // When I select my preferences and return to the main page and go back to the preferences again,
        // the option I selected disappears
        JPanel preferencesPanel = new JPanel();
        preferencesPanel.setLayout(new BoxLayout(preferencesPanel, BoxLayout.Y_AXIS));

        JLabel preferencesLabel = new JLabel("Set Your Preferences:");
        JCheckBox vegetarianCheckBox = new JCheckBox("Vegetarian");
        JCheckBox glutenFreeCheckBox = new JCheckBox("Gluten Free");
        JCheckBox lowSodiumCheckBox = new JCheckBox("Low Sodium");

        JButton applyButton = new JButton("Apply Preferences");
        applyButton.addActionListener(e -> {
            String preferences = "Preferences: ";
            if (vegetarianCheckBox.isSelected()) preferences += "Vegetarian, ";
            if (glutenFreeCheckBox.isSelected()) preferences += "Gluten Free, ";
            if (lowSodiumCheckBox.isSelected()) preferences += "Low Sodium, ";
            JOptionPane.showMessageDialog(this, preferences + "applied!");
        });

        preferencesPanel.add(preferencesLabel);
        preferencesPanel.add(vegetarianCheckBox);
        preferencesPanel.add(glutenFreeCheckBox);
        preferencesPanel.add(lowSodiumCheckBox);
        preferencesPanel.add(applyButton);



        mainPanel.add(preferencesPanel, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showSavedRecipesPanel() {
        mainPanel.removeAll();

        // back button
        JButton backButton = createBackButton();
        mainPanel.add(backButton, BorderLayout.NORTH);

        JPanel preferencesPanel = new JPanel();
        preferencesPanel.setLayout(new BoxLayout(preferencesPanel, BoxLayout.Y_AXIS));

        //TODO:






        mainPanel.add(preferencesPanel, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();

    }

    private void showAdditionalOnePanel() {
        mainPanel.removeAll();

        // back button
        JButton backButton = createBackButton();
        mainPanel.add(backButton, BorderLayout.NORTH);

        JPanel preferencesPanel = new JPanel();
        preferencesPanel.setLayout(new BoxLayout(preferencesPanel, BoxLayout.Y_AXIS));

        //TODO:






        mainPanel.add(preferencesPanel, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();

    }

    // Create Back Button
    private JButton createBackButton() {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            mainPanel.removeAll();
            mainPanel.add(createMainContent(), BorderLayout.CENTER);
            mainPanel.revalidate();
            mainPanel.repaint();
        });
        return backButton;
    }


    private JPanel createMainContent() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Welcome to Recipe Genie", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        contentPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 10, 10)); // 两行两列布局

        JButton searchButton = new JButton("Search Recipes");
        JButton filterButton = new JButton("Set Preferences");
        JButton savedButton = new JButton("View Saved Recipes");
        JButton additionalButton = new JButton("Additional One");

        buttonPanel.add(searchButton);
        buttonPanel.add(filterButton);
        buttonPanel.add(savedButton);
        buttonPanel.add(additionalButton);

        contentPanel.add(buttonPanel, BorderLayout.CENTER);
        ///////////////////////////////////////////////////////////////////////////////////
        // I have to duplicate the above code here
        // Without the following code, the return key will not work after one click
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSearchPanel();
            }
        });
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPreferencesPanel();
            }
        });
        savedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSavedRecipesPanel();
            }
        });
        additionalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAdditionalOnePanel();
            }
        });

        return contentPanel;
    }
    ///////////////////////////////////////////////////////////////////////////

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Action", JOptionPane.INFORMATION_MESSAGE);
    }
}
