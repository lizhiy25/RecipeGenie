//package main.java;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class LoginFrame extends JFrame {
    private Runnable onLoginSuccess;

    public LoginFrame() {
        setTitle("Welcome");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        LoginPanel loginPanel = new LoginPanel(cardLayout, cardPanel, this);
        RegisterPanel registerPanel = new RegisterPanel(cardLayout, cardPanel, this);

        loginPanel.setOnLoginSuccess(() -> {
            if (onLoginSuccess != null) {
                onLoginSuccess.run();
            }
        });

        cardPanel.add(loginPanel, "Login");
        cardPanel.add(registerPanel, "Register");

        add(cardPanel);

        cardLayout.show(cardPanel, "Login");
    }

    public void setOnLoginSuccess(Runnable onLoginSuccess) {

        this.onLoginSuccess = onLoginSuccess;
    }
}

