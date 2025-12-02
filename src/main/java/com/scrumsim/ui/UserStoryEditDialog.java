package com.scrumsim.ui;

import com.scrumsim.model.Story;
import com.scrumsim.model.StoryStatus;

import javax.swing.*;
import java.awt.*;

public class UserStoryEditDialog extends JDialog {

    private final Story story;
    private final boolean isCreateMode;
    private boolean saved = false;

    private JTextField nameField;
    private JTextArea descriptionArea;
    private JComboBox<StoryStatus> statusDropdown;
    private JSpinner pointsSpinner;
    private JTextField assigneesField;


    public UserStoryEditDialog(Frame parent, Story story) {
        super(parent, "Edit User Story", true);
        this.story = story;
        this.isCreateMode = false;
        initializeUI();
        populateFields();
    }


    public UserStoryEditDialog(Frame parent) {
        super(parent, "Create User Story", true);
        this.story = new Story("", "", StoryStatus.TO_DO, 0, "");
        this.isCreateMode = true;
        initializeUI();
    }


    private void initializeUI() {
        setSize(600, 500);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));

        // Main form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        int row = 0;

        // Name field
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        nameField = new JTextField(30);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        formPanel.add(nameField, gbc);

        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        descriptionArea = new JTextArea(8, 30);
        descriptionArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descScrollPane = new JScrollPane(descriptionArea);
        formPanel.add(descScrollPane, gbc);

        row++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0.0;

        // Status dropdown
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(new JLabel("Status:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        statusDropdown = new JComboBox<>(StoryStatus.values());
        statusDropdown.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        formPanel.add(statusDropdown, gbc);

        row++;

        // Points spinner
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Points:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        pointsSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        pointsSpinner.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        formPanel.add(pointsSpinner, gbc);

        row++;

        // Assignees field
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Assignees:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        assigneesField = new JTextField(30);
        assigneesField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        formPanel.add(assigneesField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Button panel at bottom-right
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        saveButton.addActionListener(e -> onSave());
        cancelButton.addActionListener(e -> onCancel());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void populateFields() {
        if (!isCreateMode) {
            nameField.setText(story.getTitle());
            descriptionArea.setText(story.getDescription());
            statusDropdown.setSelectedItem(story.getStatus());
            pointsSpinner.setValue(story.getPoints());
            assigneesField.setText(story.getAssignees());
        }
    }

    private void onSave() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Story name cannot be empty.",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Update story object with form values
        story.setTitle(name);
        story.setDescription(descriptionArea.getText().trim());
        story.setStatus((StoryStatus) statusDropdown.getSelectedItem());
        story.setPoints((Integer) pointsSpinner.getValue());
        story.setAssignees(assigneesField.getText().trim());

        saved = true;
        dispose();
    }

    private void onCancel() {
        saved = false;
        dispose();
    }

    public boolean wasSaved() {
        return saved;
    }

    public Story getStory() {
        return story;
    }
}