package com.scrumsim.ui;

import com.scrumsim.model.Team;
import com.scrumsim.repository.TeamRepository;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CreateTeamDialog extends JDialog {

    private final TeamRepository teamRepository;
    private final JTextField teamNameField;
    private Team createdTeam;

    public CreateTeamDialog(Frame parent, TeamRepository teamRepository) {
        super(parent, "Create Team", true);

        if (teamRepository == null) {
            throw new IllegalArgumentException("TeamRepository cannot be null");
        }

        this.teamRepository = teamRepository;
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

        createdTeam = new Team(teamName, "Scrum Master");
        dispose();
    }

    private boolean validateTeamName(String teamName) {
        if (teamName.isEmpty()) {
            showError("Team name cannot be empty.");
            return false;
        }

        if (teamRepository.existsByName(teamName)) {
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
