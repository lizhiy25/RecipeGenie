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
    private SaveFile saveFile;
//    private JPanel actionPanel;

    public MainPanel() {
        setLayout(new BorderLayout());
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        saveFile = new SaveFile();

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
        JButton additionalButton = new JButton("Additional One");

        buttonPanel.add(searchButton);
        buttonPanel.add(filterButton);
        buttonPanel.add(savedButton);
        buttonPanel.add(additionalButton);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        searchButton.addActionListener(e -> showSearchPanel());
        filterButton.addActionListener(e -> showPreferencesPanel());
        savedButton.addActionListener(e -> showSavedRecipesPanel());
        additionalButton.addActionListener(e -> showAdditionalOnePanel());

    }

    // Search Recipes Part by Zhiyu
    private void showSearchPanel() {
        mainPanel.removeAll();

        JButton backButton = createBackButton();
        mainPanel.add(backButton, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));

        JLabel searchLabel = new JLabel("Enter Food Name:");
        JTextField searchField = new JTextField(1);
        JButton searchSubmitButton = new JButton("Search");

        searchSubmitButton.addActionListener(e -> {
            String query = searchField.getText();
            try {
                String result = fetchRecipeData(query);

                // Show results with a save button
                JPanel resultsPanel = new JPanel();
                resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));

                String[] recipes = result.split("--------------------------------------");
                for (String recipe : recipes) {
                    if (!recipe.trim().isEmpty()) {
                        JPanel recipePanel = new JPanel(new BorderLayout());
                        JTextArea recipeText = new JTextArea(recipe);
                        recipeText.setEditable(false);

                        JButton saveButton = new JButton("Save");
                        saveButton.addActionListener(event -> {
                            saveFile.addRecipeUrl(recipe); // Save recipe to SaveFile
                            JOptionPane.showMessageDialog(this, "Recipe saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        });

                        recipePanel.add(new JScrollPane(recipeText), BorderLayout.CENTER);
                        recipePanel.add(saveButton, BorderLayout.EAST);
                        resultsPanel.add(recipePanel);
                    }
                }

                JScrollPane scrollPane = new JScrollPane(resultsPanel);
                JOptionPane.showMessageDialog(this, scrollPane, "Search Results", JOptionPane.PLAIN_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error fetching recipes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchSubmitButton);

        mainPanel.add(searchPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }


    private String fetchRecipeData(String query) throws Exception {
        String apiUrl = "https://api.api-ninjas.com/v1/nutrition?query=" + query.replace(" ", "%20");
        String apiKey = "RTsk4zAYtxwguq9NUOkpAQ==CIDpDJQmd3F2AJWc";

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("X-Api-Key", apiKey);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse JSON and format the response
            JSONArray jsonResponse = new JSONArray(response.toString());
            StringBuilder formattedResponse = new StringBuilder();
            for (int i = 0; i < jsonResponse.length(); i++) {
                JSONObject item = jsonResponse.getJSONObject(i);
                formattedResponse.append("Name: ").append(item.getString("name")).append("\n");
                formattedResponse.append("Sodium: ").append(item.getDouble("sodium_mg")).append("g\n");
                formattedResponse.append("Sugar: ").append(item.getDouble("sugar_g")).append("\n");
                formattedResponse.append("Cholesterol: ").append(item.getDouble("cholesterol_mg")).append("g\n");
                formattedResponse.append("Potassium: ").append(item.getDouble("potassium_mg")).append("g\n");
                formattedResponse.append("Fiber: ").append(item.getDouble("fiber_g")).append("g\n");
                formattedResponse.append("--------------------------------------\n");
            }
            return formattedResponse.toString();
        } else {
            throw new Exception("API Error: " + responseCode);
        }
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

        JLabel label = new JLabel("Saved Recipes:");
        savedPanel.add(label);

        List<String> savedRecipes = saveFile.getRecipeUrl();
        if (saveFile.isEmpty()) {
            savedPanel.add(new JLabel("No saved recipes."));
        } else {
            for (String recipe : savedRecipes) {
                JPanel recipePanel = new JPanel(new BorderLayout());
                JTextArea recipeText = new JTextArea(recipe);
                recipeText.setEditable(false);

                JButton removeButton = new JButton("Remove");
                removeButton.addActionListener(e -> {
                    saveFile.removeRecipeUrl(recipe); // Remove recipe from SaveFile
                    JOptionPane.showMessageDialog(this, "Recipe removed!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    showSavedRecipesPanel(); // Refresh the panel
                });

                recipePanel.add(new JScrollPane(recipeText), BorderLayout.CENTER);
                recipePanel.add(removeButton, BorderLayout.EAST);
                savedPanel.add(recipePanel);
            }
        }

        JScrollPane scrollPane = new JScrollPane(savedPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

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