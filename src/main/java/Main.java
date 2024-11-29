package main.java;

import main.java.MainPanel;

import javax.swing.*;
import main.java.MainPanel;
import main.java.LoginPanel;
import main.java.RegisterPanel;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create Login Frame
            LoginFrame loginFrame = new LoginFrame();

            loginFrame.setOnLoginSuccess(() -> {
                loginFrame.dispose();
                openMainApplication();
            });

            loginFrame.setVisible(true);
        });
    }

    private static void openMainApplication() {
        JFrame mainFrame = new JFrame("Recipe Genie");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.add(new MainPanel());
        mainFrame.setVisible(true);
    }
}

//class Saved {
//    public static void main(String[] args) {
//        // Create an instance of SaveFile
//        SaveFile saveFile = new SaveFile();
//
//        // Add a recipe using the Nutrition API
//        saveFile.fetchAndAddRecipe("1lb brisket and fries");
//
//        // Print all saved recipes
//        System.out.println("Saved Recipes:");
//        for (String url : saveFile.getRecipeUrl()) {
//            System.out.println(url);
//        }
//
//    }
//}
