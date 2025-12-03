package com.scrumsim.ui;

import com.scrumsim.model.Story;
import com.scrumsim.service.BusinessValueService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ReviewBusinessValueDialog extends JDialog {

    private List<Story> stories;
    private BusinessValueService businessValueService;
    private JComboBox<String> storyDropdown;
    private JTextField valueField;

    public ReviewBusinessValueDialog(Frame parent, List<Story> stories, BusinessValueService businessValueService) {
        super(parent, "Review Business Value", true);
        this.stories = stories;
        this.businessValueService = businessValueService;
        setupDialog();
    }

    private void setupDialog() {
        setSize(450, 200);
        setLocationRelativeTo(getParent());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel storyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        storyPanel.add(new JLabel("Story:"));
        storyDropdown = new JComboBox<>();
        for (Story story : stories) {
            storyDropdown.addItem(story.getTitle());
        }
        storyDropdown.setPreferredSize(new Dimension(300, 25));
        storyDropdown.addActionListener(e -> updateValueField());
        storyPanel.add(storyDropdown);
        mainPanel.add(storyPanel);

        JPanel valuePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        valuePanel.add(new JLabel("Business Value:"));
        valueField = new JTextField(10);
        valuePanel.add(valueField);
        mainPanel.add(valuePanel);

        updateValueField();

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton applyButton = new JButton("Apply");
        JButton cancelButton = new JButton("Cancel");

        applyButton.addActionListener(e -> handleApply());
        cancelButton.addActionListener(e -> handleCancel());

        buttonPanel.add(applyButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel);

        add(mainPanel);
    }

    private void updateValueField() {
        int selectedIndex = storyDropdown.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < stories.size()) {
            Story selectedStory = stories.get(selectedIndex);
            int currentValue = selectedStory.getBusinessValue();
            valueField.setText(String.valueOf(currentValue));
        }
    }

    private void handleApply() {
        int selectedIndex = storyDropdown.getSelectedIndex();
        Story selectedStory = stories.get(selectedIndex);

        String valueText = valueField.getText();
        int value = Integer.parseInt(valueText);

        businessValueService.applyBusinessValue(selectedStory, value);

        JOptionPane.showMessageDialog(this, "Business value updated!");
        dispose();
    }

    private void handleCancel() {
        dispose();
    }
}
