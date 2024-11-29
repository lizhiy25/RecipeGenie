package main.java;

import main.data.UserDatabase;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private Runnable onLoginSuccess;

    public LoginPanel(CardLayout cardLayout, JPanel cardPanel, JFrame loginFrame) {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JTextField passwordField = new JTextField();
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Sign Up");

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(loginButton);
        formPanel.add(registerButton);

        add(formPanel, BorderLayout.CENTER);


        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (UserDatabase.authenticateUser(username, password)) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                if (onLoginSuccess != null) {
                    onLoginSuccess.run();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        registerButton.addActionListener(e -> cardLayout.show(cardPanel, "Register"));
    }

    public void setOnLoginSuccess(Runnable onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess;
    }
}
