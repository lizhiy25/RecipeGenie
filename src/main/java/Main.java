import javax.swing.*;

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
        // Create an instance of SaveFile
        SaveFile saveFile = new SaveFile();

        // Add a recipe using the Nutrition API
        saveFile.fetchAndAddRecipe("1lb brisket and fries");

        // Print all saved recipes
        System.out.println("Saved Recipes:");
        for (String url : saveFile.getRecipeUrl()) {
            System.out.println(url);
        }

    }
}
