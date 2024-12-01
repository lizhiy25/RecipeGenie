//package main.java;
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

//            // Create Login Frame
//            LoginFrame loginFrame = new LoginFrame();
//
//            loginFrame.setOnLoginSuccess(() -> {
//                loginFrame.dispose();
//                openMainApplication();
//            });
//
//            loginFrame.setVisible(true);
//        });
//    }
//
//    private static void openMainApplication() {
//        JFrame mainFrame = new JFrame("Recipe Genie");
//        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        mainFrame.setSize(800, 600);
//        mainFrame.setLocationRelativeTo(null);
//        mainFrame.add(new MainPanel());
//        mainFrame.setVisible(true);
//    }
//}
////Delete below after

            // Main Panel
            frame.add(new MainPanel());
            frame.setVisible(true);
        });
    }
}

class Saved {
    public static void main(String[] args) {
        // Create an instance of SaveFile
        SaveFile saveFile = SaveFile.getInstance(); // Use the singleton instance

        // Add a recipe using the Nutrition API (name and nutritional information)
        saveFile.fetchAndAddRecipe("1lb brisket and fries");

        // Print all saved recipes
        System.out.println("Saved Recipes:");
        List<String[]> savedRecipes = saveFile.getSavedRecipes(); // Get both name and nutritional info
        for (String[] recipe : savedRecipes) {
            String recipeName = recipe[0];
            String nutritionalInfo = recipe[1];
            System.out.println("Recipe: " + recipeName);
            System.out.println("Nutritional Info: " + nutritionalInfo);
        }
    }
}

