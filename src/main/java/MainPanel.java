//package main.java;

import main.data.UserDatabase;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel {
    private final JPanel mainPanel;
    private SaveFile saveFile;
    //    private JPanel actionPanel;

    public MainPanel() {
        // Set up the layout for the main panel
        setLayout(new BorderLayout());
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout()); // No need to call this again

        // Singleton SaveFile instance
        saveFile = SaveFile.getInstance(); // Use the singleton instance

        // Set Title
        JLabel titleLabel = new JLabel("Welcome to Recipe Genie", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Add 4 buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 10, 10));
        JButton searchButton = new JButton("Search Recipes");
        JButton accountSettingsButton = new JButton("Account Settings");
        JButton savedButton = new JButton("View Saved Recipes");
        JButton weightLossCalculatorButton = new JButton("Weight Loss Calculator");

        buttonPanel.add(searchButton);
        buttonPanel.add(accountSettingsButton);
        buttonPanel.add(savedButton);
        buttonPanel.add(weightLossCalculatorButton);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // Action listeners for buttons
        searchButton.addActionListener(e -> showSearchPanel());
        accountSettingsButton.addActionListener(e -> showAccountSettingsPanel());
        savedButton.addActionListener(e -> showSavedRecipesPanel());
        weightLossCalculatorButton.addActionListener(e -> showWeightLossCalculatorPanel());
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

        // Action for the Search button
        searchSubmitButton.addActionListener(e -> {
            String query = searchField.getText();
            try {
                String recipeResponse = RecipeAPI.fetchRecipeData(query);
                JSONArray recipes = new JSONArray(recipeResponse);

                if (recipes.length() > 0) {
                    // Create and display the list of recipe titles
                    String[] titles = new String[recipes.length()];
                    for (int i = 0; i < titles.length; i++) {
                        titles[i] = recipes.getJSONObject(i).getString("title");
                    }

                    JList<String> recipeList = new JList<>(titles);
                    recipeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

                    // Detail Panel for recipe details
                    JPanel detailPanel = new JPanel();
                    detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));

                    // Buttons
                    JButton showNutritionButton = new JButton("Show Nutrition");
                    JButton saveRecipeButton = new JButton("Save Recipe");

                    showNutritionButton.setEnabled(false);
                    saveRecipeButton.setEnabled(false);

                    // Update detail panel when a recipe is selected
                    recipeList.addListSelectionListener(event -> {
                        if (!event.getValueIsAdjusting()) {
                            int selectedIndex = recipeList.getSelectedIndex();
                            if (selectedIndex >= 0) {
                                JSONObject selectedRecipe = recipes.getJSONObject(selectedIndex);

                                String formattedRecipeDetails = displayFormattedRecipe(selectedRecipe);

                                JTextArea recipeArea = new JTextArea(formattedRecipeDetails);
                                recipeArea.setEditable(false);
                                recipeArea.setLineWrap(true);
                                recipeArea.setWrapStyleWord(true);

                                // Clear the detail panel and add the new details
                                detailPanel.removeAll();
                                detailPanel.add(new JScrollPane(recipeArea));

                                detailPanel.add(showNutritionButton);
                                detailPanel.add(saveRecipeButton);

                                // Enable buttons after selection
                                showNutritionButton.setEnabled(true);
                                saveRecipeButton.setEnabled(true);

                                // Refresh the panel
                                detailPanel.revalidate();
                                detailPanel.repaint();

                                for (ActionListener listener : saveRecipeButton.getActionListeners()) {
                                    saveRecipeButton.removeActionListener(listener);
                                }

                                // Action for Save Recipe
                                saveRecipeButton.addActionListener(saveEvent -> {
                                    SaveFile saveFile = SaveFile.getInstance();
                                    String recipeTitle = selectedRecipe.getString("title");
                                    // Use the selectedRecipe from the previous selection logic
                                    if (saveFile.isRecipeSaved(recipeTitle)) {
                                        JOptionPane.showMessageDialog(this,
                                                "This recipe has already been saved.",
                                                "Information", JOptionPane.INFORMATION_MESSAGE);
                                    } else {
                                        // Add recipe if not already saved
                                        saveFile.addRecipe(recipeTitle, formattedRecipeDetails);

                                        // Inform the user that the recipe was saved
                                        JOptionPane.showMessageDialog(this,
                                                "Recipe saved successfully!",
                                                "Saved", JOptionPane.INFORMATION_MESSAGE);

                                        // Disable the save button after saving
                                        saveRecipeButton.setEnabled(false);
                                    }
                                });
                            }
                        }
                    });

                    // Action for Show Nutrition
                    showNutritionButton.addActionListener(nutritionEvent -> {
                        int selectedIndex = recipeList.getSelectedIndex();
                        if (selectedIndex >= 0) {
                            JSONObject selectedRecipe = recipes.getJSONObject(selectedIndex);
                            String recipeTitle = selectedRecipe.getString("title");
                            try {
                                String nutritionData = NutritionAPI.fetchNutritionData(recipeTitle);
                                String formattedNutrition = NutritionAPI.formatNutritionData(nutritionData);

                                // Display nutrition data in the detail panel
                                JTextArea nutritionArea = new JTextArea(formattedNutrition);
                                nutritionArea.setEditable(false);
                                nutritionArea.setLineWrap(true);
                                nutritionArea.setWrapStyleWord(true);

                                detailPanel.removeAll();
                                detailPanel.add(new JLabel("Nutrition Information:"));
                                detailPanel.add(new JScrollPane(nutritionArea));

                                detailPanel.revalidate();
                                detailPanel.repaint();
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(this,
                                        "Error fetching nutrition data: " + ex.getMessage(),
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });

                    // Layout: List on the left, details on the right
                    JPanel searchResultPanel = new JPanel(new BorderLayout());
                    searchResultPanel.add(new JScrollPane(recipeList), BorderLayout.WEST);
                    searchResultPanel.add(new JScrollPane(detailPanel), BorderLayout.CENTER);

                    // Add to main panel
                    mainPanel.removeAll();
                    mainPanel.add(searchResultPanel, BorderLayout.CENTER);
                    mainPanel.add(backButton, BorderLayout.NORTH);

                    mainPanel.revalidate();
                    mainPanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "No recipes found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error fetching recipes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchSubmitButton);

        mainPanel.add(searchPanel, BorderLayout.SOUTH);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private String displayFormattedRecipe(JSONObject selectedRecipe) {
        // Retrieve recipe data
        String title = selectedRecipe.getString("title");
        String ingredients = selectedRecipe.getString("ingredients");
        String instructions = selectedRecipe.getString("instructions");
        String servings = selectedRecipe.getString("servings");

        // Format the recipe in a clean, readable format
        StringBuilder formattedRecipe = new StringBuilder();
        formattedRecipe.append("Title: ").append(title).append("\n\n");

        // Display Ingredients
        formattedRecipe.append("Ingredients:\n");
        String[] ingredientList = ingredients.split("\\|");
        for (String ingredient : ingredientList) {
            formattedRecipe.append("- ").append(ingredient.trim()).append("\n");
        }

        // Display Instructions
        formattedRecipe.append("\nInstructions:\n");
        String[] instructionsList = instructions.split("\\*");
        for (int i = 0; i < instructionsList.length; i++) {
            if (!instructionsList[i].trim().isEmpty()) {
                formattedRecipe.append(i + 1).append(". ").append(instructionsList[i].trim()).append("\n");
            }
        }

        formattedRecipe.append("\nServings: ").append(servings).append("\n");

        return formattedRecipe.toString();
    }

    // Account Settings
    private void showAccountSettingsPanel() {
        mainPanel.removeAll();

        JButton backButton = createBackButton();
        mainPanel.add(backButton, BorderLayout.NORTH);

        JPanel accountSettingsPanel = new JPanel();
        accountSettingsPanel.setLayout(new BoxLayout(accountSettingsPanel, BoxLayout.Y_AXIS));

        String currentUsername = UserDatabase.getCurrentUsername();
        String currentPassword = UserDatabase.getPassword(currentUsername);

        JLabel usernameLabel = new JLabel("Username: " + currentUsername);
        accountSettingsPanel.add(usernameLabel);

        JLabel passwordLabel = new JLabel("Password: " + currentPassword);
        accountSettingsPanel.add(passwordLabel);

        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.addActionListener(e -> {
            String newPassword = JOptionPane.showInputDialog(this, "Enter new password:");
            if (newPassword != null && !newPassword.isEmpty()) {
                UserDatabase.updatePassword(currentUsername, newPassword);
                JOptionPane.showMessageDialog(this, "Password updated successfully!");
            }
        });
        accountSettingsPanel.add(changePasswordButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to exit?",
                    "Exit",
                    JOptionPane.YES_NO_OPTION
            );
            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        accountSettingsPanel.add(exitButton);




        mainPanel.add(accountSettingsPanel, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();

    }

    private void showSavedRecipesPanel() {
        mainPanel.removeAll();

        JButton backButton = createBackButton();
        mainPanel.add(backButton, BorderLayout.NORTH);

        JPanel savedPanel = new JPanel();
        savedPanel.setLayout(new BoxLayout(savedPanel, BoxLayout.Y_AXIS));

        SaveFile saveFile = SaveFile.getInstance(); // Use the singleton instance
        List<String[]> savedRecipes = saveFile.getSavedRecipes(); // Now this will return both name and nutritional info
        if (savedRecipes.isEmpty()) {
            savedPanel.add(new JLabel("No saved recipes."));
        } else {
            for (String[] recipeData : savedRecipes) {
                String recipeName = recipeData[0];
                String nutritionalInfo = recipeData[1];

                JPanel recipePanel = new JPanel(new BorderLayout());
                JTextArea recipeText = new JTextArea("Recipe: " + recipeName + "\n" + nutritionalInfo);
                recipeText.setEditable(false);

                // Add delete button for each recipe
                JButton deleteButton = new JButton("Delete");
                deleteButton.addActionListener(e -> {
                    saveFile.removeRecipe(recipeName); // Remove the recipe based on its name
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


    private void showWeightLossCalculatorPanel() {
        mainPanel.removeAll();

        JButton backButton = createBackButton();
        mainPanel.add(backButton, BorderLayout.NORTH);

        JPanel weightLossPanel = new JPanel();
        weightLossPanel.setLayout(new BoxLayout(weightLossPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Enter Your Details for Weight Loss Calculation:");
        weightLossPanel.add(titleLabel);

        // Inputs for weight loss calculation
        JTextField weightField = new JTextField(10);
        JTextField heightField = new JTextField(10);
        JTextField ageField = new JTextField(10);
        String[] genders = {"Male", "Female"};
        JComboBox<String> genderComboBox = new JComboBox<>(genders);

        JLabel weightLabel = new JLabel("Weight (kg):");
        JLabel heightLabel = new JLabel("Height (cm):");
        JLabel ageLabel = new JLabel("Age (years):");
        JLabel genderLabel = new JLabel("Gender:");

        JButton calculateButton = new JButton("Calculate Daily Calorie Requirement");

        // Label to show the daily calorie requirement
        JLabel calorieRequirementLabel = new JLabel("Your daily calorie requirement will appear here.");

        calculateButton.addActionListener(e -> {
            try {

                double weight = Double.parseDouble(weightField.getText());
                double height = Double.parseDouble(heightField.getText());
                int age = Integer.parseInt(ageField.getText());
                String gender = (String) genderComboBox.getSelectedItem();

                // Calculate BMR using the Mifflin-St Jeor equation
                double bmr;
                if (gender.equals("Male")) {
                    bmr = 10 * weight + 6.25 * height - 5 * age + 5; // Male BMR formula
                } else {
                    bmr = 10 * weight + 6.25 * height - 5 * age - 161; // Female BMR formula
                }

                // Assume sedentary activity level for simplicity (could add more activity options)
                double tdee = bmr * 1.2; // Sedentary activity multiplier

                // To lose 1lb per week, subtract 500 calories/day
                double calorieDeficit = tdee - 500;

                calorieRequirementLabel.setText("To lose 1 lb/week, you need " + calorieDeficit + " kcal/day.");

                // Now transition to the calorie tracking panel, passing the required calories
                showCalorieTrackingPanel(calorieDeficit);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter valid numbers for all fields.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        weightLossPanel.add(weightLabel);
        weightLossPanel.add(weightField);
        weightLossPanel.add(heightLabel);
        weightLossPanel.add(heightField);
        weightLossPanel.add(ageLabel);
        weightLossPanel.add(ageField);
        weightLossPanel.add(genderLabel);
        weightLossPanel.add(genderComboBox);
        weightLossPanel.add(calculateButton);
        weightLossPanel.add(calorieRequirementLabel);

        mainPanel.add(weightLossPanel, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showCalorieTrackingPanel(double dailyCalorieRequirement) {
        mainPanel.removeAll();

        JButton backButton = createBackButton();
        mainPanel.add(backButton, BorderLayout.NORTH);

        JPanel trackingPanel = new JPanel();
        trackingPanel.setLayout(new BoxLayout(trackingPanel, BoxLayout.Y_AXIS));

        JLabel calorieInputLabel = new JLabel("Enter Calories Consumed for Today:");
        JTextField calorieInputField = new JTextField(10);

        JButton submitCaloriesButton = new JButton("Submit Calories");

        JLabel totalCaloriesLabel = new JLabel("Total Calories Consumed: 0");

        JLabel dailyCalorieRequirementLabel = new JLabel("Your daily calorie requirement: " +
                dailyCalorieRequirement + " kcal");

        final int[] totalCaloriesConsumed = {0};

        submitCaloriesButton.addActionListener(e -> {
            try {
                int calories = Integer.parseInt(calorieInputField.getText());
                totalCaloriesConsumed[0] += calories; // Add to the total

                totalCaloriesLabel.setText("Total Calories Consumed: " + totalCaloriesConsumed[0]);

                if (totalCaloriesConsumed[0] > dailyCalorieRequirement) {
                    JOptionPane.showMessageDialog(this,
                            "You have exceeded your calorie limit for weight loss!", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                } else if (totalCaloriesConsumed[0] < dailyCalorieRequirement) {
                    JOptionPane.showMessageDialog(this,
                            "You're under your calorie limit. Stay on track for weight loss.",
                            "Keep Going!", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "You are exactly on track with your calorie goal!",
                            "Great Job!", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid number of calories.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        trackingPanel.add(dailyCalorieRequirementLabel);
        trackingPanel.add(calorieInputLabel);
        trackingPanel.add(calorieInputField);
        trackingPanel.add(submitCaloriesButton);
        trackingPanel.add(totalCaloriesLabel);

        mainPanel.add(trackingPanel, BorderLayout.CENTER);

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
        JButton accountSettingsButton = new JButton("Account Settings");
        JButton savedButton = new JButton("View Saved Recipes");
        JButton weightLossCalculatorButton = new JButton("Weight Loss Calculator");

        buttonPanel.add(searchButton);
        buttonPanel.add(accountSettingsButton);
        buttonPanel.add(savedButton);
        buttonPanel.add(weightLossCalculatorButton);

        contentPanel.add(buttonPanel, BorderLayout.CENTER);

        searchButton.addActionListener(e -> showSearchPanel());
        accountSettingsButton.addActionListener(e -> showAccountSettingsPanel());
        savedButton.addActionListener(e -> showSavedRecipesPanel());
        weightLossCalculatorButton.addActionListener(e -> showWeightLossCalculatorPanel());

        return contentPanel;
    }
}