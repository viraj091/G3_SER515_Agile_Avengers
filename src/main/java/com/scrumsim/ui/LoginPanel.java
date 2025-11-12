package com.scrumsim.ui;

import com.scrumsim.model.User;
import com.scrumsim.model.UserRole;
import com.scrumsim.service.AuthService;
import com.scrumsim.store.UserSession;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private final AuthService authService;
    private final LoginListener loginListener;

    private JComboBox<String> roleComboBox;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginPanel(AuthService authService, LoginListener loginListener) {
        this.authService = authService;
        this.loginListener = loginListener;

        setupUI();
    }

    private void setupUI() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Scrum Simulation Tool");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        JLabel subtitleLabel = new JLabel("Login");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 1;
        add(subtitleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 2;
        add(new JLabel("Role:"), gbc);

        roleComboBox = new JComboBox<>(new String[]{"Product Owner", "Scrum Master", "Developer"});
        gbc.gridx = 1;
        add(roleComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Username:"), gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Password:"), gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        add(passwordField, gbc);

        loginButton = new JButton("LOGIN");
        loginButton.setBackground(new Color(0, 102, 204));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        loginButton.addActionListener(e -> handleLogin());
        usernameField.addActionListener(e -> handleLogin());
        passwordField.addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String roleString = (String) roleComboBox.getSelectedItem();

        if (username.isEmpty()) {
            showError("Please enter username");
            return;
        }

        if (password.isEmpty()) {
            showError("Please enter password");
            return;
        }

        UserRole role = convertToRole(roleString);
        User user = authService.login(role, username, password);

        if (user != null) {
            UserSession.getInstance().setCurrentUser(user);
            loginListener.onLoginSuccess(user);
        } else {
            showError("Invalid credentials. Please try again.");
        }
    }

    private UserRole convertToRole(String roleString) {
        switch (roleString) {
            case "Product Owner":
                return UserRole.PRODUCT_OWNER;
            case "Scrum Master":
                return UserRole.SCRUM_MASTER;
            case "Developer":
                return UserRole.DEVELOPER;
            default:
                return UserRole.DEVELOPER;
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Login Error", JOptionPane.ERROR_MESSAGE);
    }
}
