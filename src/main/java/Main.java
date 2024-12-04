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
        Repository.RecipeRepository recipeRepository = SaveFile.getInstance();

        recipeRepository.fetchAndAddRecipe("1lb brisket and fries");

        System.out.println("Saved Recipes:");
        List<Recipe> savedRecipes = recipeRepository.getRecipes();

        for (Recipe recipe : savedRecipes) {
            System.out.println("Recipe: " + recipe.getName());
            System.out.println("Nutritional Info: " + recipe.getNutritionalInfo());
        }
    }
}

