import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Start Main project
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Recipe Genie");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null); // Center the screen

            // Main Panel
            frame.add(new MainPanel());
            frame.setVisible(true);
        });
    }
}

class Saved {
    public static void main(String[] args) {
        // Create an instance of SaveFile using RecipeRepository interface
        Repository.RecipeRepository recipeRepository = SaveFile.getInstance(); // Get singleton instance of SaveFile

        // Add a recipe using the Nutrition API (name and nutritional information)
        recipeRepository.fetchAndAddRecipe("1lb brisket and fries");

        // Print all saved recipes
        System.out.println("Saved Recipes:");
        List<Recipe> savedRecipes = recipeRepository.getRecipes(); // Get list of recipes

        // Loop through and print recipe details
        for (Recipe recipe : savedRecipes) {
            System.out.println("Recipe: " + recipe.getName());
            System.out.println("Nutritional Info: " + recipe.getNutritionalInfo());
        }
    }
}

