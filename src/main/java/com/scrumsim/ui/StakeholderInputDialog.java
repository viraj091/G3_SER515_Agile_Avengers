package com.scrumsim.ui;

import com.scrumsim.model.Story;
import com.scrumsim.service.StakeholderInputService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StakeholderInputDialog extends JDialog {

    private final StakeholderInputService inputService;
    private final String stakeholderName;
    private final List<Story> stories;

    private JComboBox<String> storyDropdown;
    private JTextArea messageArea;

    public StakeholderInputDialog(Frame parent, StakeholderInputService inputService, String stakeholderName, List<Story> stories) {
        super(parent, "Give Input on User Story", true);
        this.inputService = inputService;
        this.stakeholderName = stakeholderName;
        this.stories = stories;
        initializeUI();
    }

    private void initializeUI() {
        setSize(500, 400);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(new JLabel("User Story:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        String[] storyTitles = new String[stories.size()];
        for (int i = 0; i < stories.size(); i++) {
            storyTitles[i] = stories.get(i).getTitle();
        }
        storyDropdown = new JComboBox<>(storyTitles);
        storyDropdown.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        formPanel.add(storyDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("Your Input:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        messageArea = new JTextArea(10, 30);
        messageArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        formPanel.add(scrollPane, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        submitButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        submitButton.addActionListener(e -> onSubmit());
        cancelButton.addActionListener(e -> onCancel());

        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void onSubmit() {
        String message = messageArea.getText().trim();
        if (message.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter your input.",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (storyDropdown.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this,
                "Please select a user story.",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        Story selectedStory = stories.get(storyDropdown.getSelectedIndex());
        inputService.submitInput(selectedStory.getId(), stakeholderName, message);

        JOptionPane.showMessageDialog(this,
            "Your input has been submitted successfully!",
            "Success",
            JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }

    private void onCancel() {
        dispose();
    }
}