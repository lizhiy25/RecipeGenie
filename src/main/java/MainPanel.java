import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainPanel extends JPanel {
    private JPanel mainPanel;

    public MainPanel() {
        // Set up the layout for the main panel
        setLayout(new BorderLayout());
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Singleton SaveFile instance
        SaveFile saveFile = (SaveFile) SaveFile.getInstance(); // Cast to SaveFile

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
        JButton additionalButton = new JButton("Additional One");

        buttonPanel.add(searchButton);
        buttonPanel.add(filterButton);
        buttonPanel.add(savedButton);
        buttonPanel.add(additionalButton);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // Action listeners for buttons
        searchButton.addActionListener(e -> showSearchPanel());
        filterButton.addActionListener(e -> showPreferencesPanel());
        savedButton.addActionListener(e -> showSavedRecipesPanel());
        additionalButton.addActionListener(e -> showAdditionalOnePanel());
    }

    private void showSearchPanel() {
        mainPanel.removeAll();

        // Back button
        JButton backButton = createBackButton();
        mainPanel.add(backButton, BorderLayout.NORTH);

        // Search panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));

        JLabel searchLabel = new JLabel("Enter Recipe Name:");
        JTextField searchField = new JTextField(1);
        JButton searchSubmitButton = new JButton("Search");
        JButton saveButton = new JButton("Save Recipe");

        // Action for the Search button
        searchSubmitButton.addActionListener(e -> {
            String query = searchField.getText();
            try {
                String jsonResponse = NutritionAPI.fetchNutritionData(query);
                String formattedResponse = NutritionAPI.formatNutritionData(jsonResponse);
                JOptionPane.showMessageDialog(this, "Results:\n" + formattedResponse, "Search Results", JOptionPane.INFORMATION_MESSAGE);

                // Enable the save button once a search is successful
                saveButton.setEnabled(true);

                // Action for the Save button
                saveButton.addActionListener(saveEvent -> {
                    Repository.RecipeRepository recipeRepository = SaveFile.getInstance(); // Use RecipeRepository
                    recipeRepository.addRecipe(new Recipe(query, formattedResponse)); // Save both the query (recipe name) and formatted nutritional info
                    JOptionPane.showMessageDialog(this, "Recipe saved successfully!", "Saved", JOptionPane.INFORMATION_MESSAGE);
                });

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error fetching recipes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        saveButton.setEnabled(false); // Initially disable the save button
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchSubmitButton);
        searchPanel.add(saveButton);

        mainPanel.add(searchPanel, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }



    // Show Preferences Panel
    private void showPreferencesPanel() {
        mainPanel.removeAll();

        JButton backButton = createBackButton();
        mainPanel.add(backButton, BorderLayout.NORTH);

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

        JButton backButton = createBackButton();
        mainPanel.add(backButton, BorderLayout.NORTH);

        JPanel savedPanel = new JPanel();
        savedPanel.setLayout(new BoxLayout(savedPanel, BoxLayout.Y_AXIS));

        Repository.RecipeRepository recipeRepository = SaveFile.getInstance(); // Use the singleton instance
        List<Recipe> savedRecipes = recipeRepository.getRecipes(); // Use getRecipes() to fetch saved recipes

        if (savedRecipes.isEmpty()) {
            savedPanel.add(new JLabel("No saved recipes."));
        } else {
            for (Recipe recipe : savedRecipes) {
                String recipeName = recipe.getName();
                String nutritionalInfo = recipe.getNutritionalInfo();

                JPanel recipePanel = new JPanel(new BorderLayout());
                JTextArea recipeText = new JTextArea("Recipe: " + recipeName + "\n" + nutritionalInfo);
                recipeText.setEditable(false);

                // Add delete button for each recipe
                JButton deleteButton = new JButton("Delete");
                deleteButton.addActionListener(e -> {
                    recipeRepository.removeRecipe(recipeName); // Remove the recipe based on its name
                    showSavedRecipesPanel(); // Refresh the panel
                });

                recipePanel.add(recipeText, BorderLayout.CENTER);
                recipePanel.add(deleteButton, BorderLayout.EAST);
                savedPanel.add(recipePanel);
            }
        }

        mainPanel.add(savedPanel, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }


    private void showAdditionalOnePanel() {
        mainPanel.removeAll();

        JButton backButton = createBackButton();
        mainPanel.add(backButton, BorderLayout.NORTH);

        JPanel additionalPanel = new JPanel();
        additionalPanel.setLayout(new BoxLayout(additionalPanel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Additional Feature: (Coming soon)");
        additionalPanel.add(label);

        mainPanel.add(additionalPanel, BorderLayout.CENTER);

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
        buttonPanel.setLayout(new GridLayout(2, 2, 10, 10));

        JButton searchButton = new JButton("Search Recipes");
        JButton filterButton = new JButton("Set Preferences");
        JButton savedButton = new JButton("View Saved Recipes");
        JButton additionalButton = new JButton("Additional One");

        buttonPanel.add(searchButton);
        buttonPanel.add(filterButton);
        buttonPanel.add(savedButton);
        buttonPanel.add(additionalButton);

        contentPanel.add(buttonPanel, BorderLayout.CENTER);

        searchButton.addActionListener(e -> showSearchPanel());
        filterButton.addActionListener(e -> showPreferencesPanel());
        savedButton.addActionListener(e -> showSavedRecipesPanel());
        additionalButton.addActionListener(e -> showAdditionalOnePanel());

        return contentPanel;
    }
}