package com.scrumsim.ui;

import com.scrumsim.model.User;
import com.scrumsim.model.UserRole;
import com.scrumsim.store.UserSession;

import javax.swing.*;
import java.awt.*;

public class SessionStatusPanel extends JPanel {

    private JLabel userNameLabel;
    private JLabel userRoleLabel;
    private JLabel statusLabel;

    public SessionStatusPanel() {
        userNameLabel = new JLabel();
        userRoleLabel = new JLabel();
        statusLabel = new JLabel();

        setupUI();
        updateSessionInfo();
    }

    private void setupUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Current Session"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        infoPanel.add(new JLabel("User:"));
        infoPanel.add(userNameLabel);

        infoPanel.add(new JLabel("Role:"));
        infoPanel.add(userRoleLabel);

        infoPanel.add(new JLabel("Status:"));
        infoPanel.add(statusLabel);

        add(infoPanel, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> updateSessionInfo());
        add(refreshButton, BorderLayout.SOUTH);
    }

    public void updateSessionInfo() {
        UserSession session = UserSession.getInstance();

        if (session.isLoggedIn()) {
            User currentUser = session.getCurrentUser();
            UserRole currentRole = session.getCurrentUserRole();

            userNameLabel.setText(currentUser.getName());
            userRoleLabel.setText(currentRole.getDisplayName());
            statusLabel.setText("Active");
            statusLabel.setForeground(Color.GREEN);
        } else {
            userNameLabel.setText("None");
            userRoleLabel.setText("None");
            statusLabel.setText("Not Logged In");
            statusLabel.setForeground(Color.RED);
        }
    }
}