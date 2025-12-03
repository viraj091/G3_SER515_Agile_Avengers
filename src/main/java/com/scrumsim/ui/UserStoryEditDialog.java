package com.scrumsim.ui;

import com.scrumsim.model.Story;
import com.scrumsim.model.StoryStatus;
import com.scrumsim.model.TeamMembers;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UserStoryEditDialog extends JDialog {

    private final Story story;
    private final boolean isCreateMode;
    private boolean saved = false;

    private JTextField nameField;
    private JTextArea descriptionArea;
    private JComboBox<StoryStatus> statusDropdown;
    private JSpinner pointsSpinner;
    private JCheckBox[] assigneeCheckboxes;


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

        // Assignees checkboxes
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("Assignees:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));

        assigneeCheckboxes = new JCheckBox[TeamMembers.ALLOWED_MEMBERS.size()];
        for (int i = 0; i < TeamMembers.ALLOWED_MEMBERS.size(); i++) {
            assigneeCheckboxes[i] = new JCheckBox(TeamMembers.ALLOWED_MEMBERS.get(i));
            assigneeCheckboxes[i].setFont(new Font("Segoe UI", Font.PLAIN, 13));
            checkboxPanel.add(assigneeCheckboxes[i]);
        }
        formPanel.add(checkboxPanel, gbc);

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

            String currentAssignees = story.getAssignees();
            if (currentAssignees != null && !currentAssignees.trim().isEmpty()) {
                String[] assignedNames = currentAssignees.split(",");
                for (String assignedName : assignedNames) {
                    String trimmedName = assignedName.trim();
                    for (int i = 0; i < assigneeCheckboxes.length; i++) {
                        if (assigneeCheckboxes[i].getText().equals(trimmedName)) {
                            assigneeCheckboxes[i].setSelected(true);
                            break;
                        }
                    }
                }
            }
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

        List<String> selectedAssignees = new ArrayList<>();
        for (JCheckBox checkbox : assigneeCheckboxes) {
            if (checkbox.isSelected()) {
                selectedAssignees.add(checkbox.getText());
            }
        }
        String assigneesString = String.join(", ", selectedAssignees);

        story.setTitle(name);
        story.setDescription(descriptionArea.getText().trim());
        story.setStatus((StoryStatus) statusDropdown.getSelectedItem());
        story.setPoints((Integer) pointsSpinner.getValue());
        story.setAssignees(assigneesString);

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