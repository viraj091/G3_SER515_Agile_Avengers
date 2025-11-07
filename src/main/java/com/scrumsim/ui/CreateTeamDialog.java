package com.scrumsim.ui;

import com.scrumsim.model.Team;
import com.scrumsim.model.User;
import com.scrumsim.service.TeamService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CreateTeamDialog extends JDialog {

    private final TeamService teamService;
    private final User currentUser;
    private final JTextField teamNameField;
    private Team createdTeam;

    public CreateTeamDialog(Frame parent, TeamService teamService, User currentUser) {
        super(parent, "Create Team", true);

        if (teamService == null) {
            throw new IllegalArgumentException("TeamService cannot be null");
        }
        if (currentUser == null) {
            throw new IllegalArgumentException("Current user cannot be null");
        }

        this.teamService = teamService;
        this.currentUser = currentUser;
        this.teamNameField = new JTextField(20);
        this.createdTeam = null;

        initializeUI();
        pack();
        setLocationRelativeTo(parent);
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        mainPanel.add(createFormPanel(), BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Team Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        teamNameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(teamNameField, gbc);

        return formPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cancelButton.addActionListener(e -> handleCancel());

        JButton createButton = new JButton("Create Team");
        createButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        createButton.addActionListener(e -> handleCreateTeam());

        buttonPanel.add(cancelButton);
        buttonPanel.add(createButton);

        return buttonPanel;
    }

    private void handleCreateTeam() {
        String teamName = teamNameField.getText().trim();

        if (!validateTeamName(teamName)) {
            return;
        }

        try {
            createdTeam = teamService.createTeam(teamName, currentUser);
            dispose();
        } catch (IllegalStateException e) {
            showError(e.getMessage());
        }
    }

    private boolean validateTeamName(String teamName) {
        if (teamName.isEmpty()) {
            showError("Team name cannot be empty.");
            return false;
        }

        if (teamService.teamExists(teamName)) {
            showError("A team with this name already exists.\nPlease choose a different name.");
            return false;
        }

        return true;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Validation Error",
            JOptionPane.ERROR_MESSAGE
        );
    }

    private void handleCancel() {
        createdTeam = null;
        dispose();
    }

    public Team getCreatedTeam() {
        return createdTeam;
    }

    public boolean wasTeamCreated() {
        return createdTeam != null;
    }
}
