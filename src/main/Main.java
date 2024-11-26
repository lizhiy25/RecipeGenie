package main;
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