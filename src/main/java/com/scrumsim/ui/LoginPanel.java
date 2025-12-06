package com.scrumsim.ui;

import com.scrumsim.model.User;
import com.scrumsim.model.UserRole;
import com.scrumsim.service.DefaultAuthService;
import com.scrumsim.store.UserSession;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class LoginPanel extends JPanel {

    private final DefaultAuthService authService;
    private final LoginListener loginListener;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleDropdown;
    private JButton loginButton;
    private JLabel errorLabel;

    public LoginPanel(DefaultAuthService authService2, LoginListener loginListener) {
        this.authService = authService2;
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
        add(new JLabel("Username:"), gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Password:"), gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(new JLabel("Role:"), gbc);

        String[] roles = {"Scrum Master", "Product Owner", "Developer"};
        roleDropdown = new JComboBox<>(roles);
        gbc.gridx = 1;
        add(roleDropdown, gbc);

        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(errorLabel, gbc);

        loginButton = new JButton("LOGIN");
        loginButton.setBackground(new Color(0, 102, 204));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.setBorder(new LineBorder(new Color(0, 80, 160), 2, true));
        loginButton.setPreferredSize(new Dimension(200, 40));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 6;
        add(loginButton, gbc);

        loginButton.addActionListener(e -> handleLogin());
        usernameField.addActionListener(e -> handleLogin());
        passwordField.addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String selectedRoleString = (String) roleDropdown.getSelectedItem();

        errorLabel.setText("");

        if (username.isEmpty()) {
            errorLabel.setText("Please enter username");
            return;
        }

        if (password.isEmpty()) {
            errorLabel.setText("Please enter password");
            return;
        }

        UserRole selectedRole = convertToUserRole(selectedRoleString);
        User user = authService.authenticate(username, password, selectedRole);

        if (user != null) {
            UserSession.getInstance().startSession(user);
            loginListener.onLoginSuccess(user);
        } else {
            errorLabel.setText("Invalid credentials. Please try again.");
        }
    }

    private UserRole convertToUserRole(String roleString) {
        if (roleString.equals("Scrum Master")) {
            return UserRole.SCRUM_MASTER;
        }
        if (roleString.equals("Product Owner")) {
            return UserRole.PRODUCT_OWNER;
        }
        return UserRole.DEVELOPER;
    }
}