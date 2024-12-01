//package main.java;

import main.data.UserDatabase;

import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JPanel {
    public RegisterPanel(CardLayout cardLayout, JPanel cardPanel, JFrame loginFrame) {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Register", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JTextField passwordField = new JTextField();
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        JTextField confirmPasswordField = new JTextField();
        JButton registerButton = new JButton("Register");
        JButton backButton = new JButton("Back");

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(confirmPasswordLabel);
        formPanel.add(confirmPasswordField);
        formPanel.add(registerButton);
        formPanel.add(backButton);

        add(formPanel, BorderLayout.CENTER);


        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();


            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!UserDatabase.registerUser(username, password)) {
                JOptionPane.showMessageDialog(this, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Registration successful!");
                cardLayout.show(cardPanel, "Login");
            }
        });

        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Login"));
    }
}
