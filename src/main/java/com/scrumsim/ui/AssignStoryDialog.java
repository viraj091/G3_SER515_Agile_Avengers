package com.scrumsim.ui;

import com.scrumsim.model.Story;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AssignStoryDialog extends JDialog {

    private JComboBox<String> storyComboBox;
    private JComboBox<String> developerComboBox;
    private List<Story> stories;

    public AssignStoryDialog(Frame parent, List<Story> stories) {
        super(parent, "Assign Story to Developer", true);
        this.stories = stories;

        setLayout(new GridLayout(3, 2, 10, 10));
        setSize(500, 200);
        setLocationRelativeTo(parent);

        add(new JLabel("Select Story:"));
        storyComboBox = new JComboBox<>();
        for (Story story : stories) {
            storyComboBox.addItem(story.getTitle());
        }
        add(storyComboBox);

        add(new JLabel("Select Developer:"));
        String[] developers = {
            "Viraj Rathor",
            "Sairaj Dalvi",
            "Shreyas Revankar",
            "Pranav Irlapale",
            "Gunjan Purohit"
        };
        developerComboBox = new JComboBox<>(developers);
        add(developerComboBox);

        JButton assignButton = new JButton("Assign");
        assignButton.addActionListener(e -> assignStory());
        add(assignButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
    }

    private void assignStory() {
        int storyIndex = storyComboBox.getSelectedIndex();
        String developer = (String) developerComboBox.getSelectedItem();

        if (storyIndex >= 0 && developer != null) {
            Story story = stories.get(storyIndex);

            // Add developer to assignees
            String currentAssignees = story.getAssignees();
            if (currentAssignees == null || currentAssignees.isEmpty()) {
                story.setAssignees(developer);
            } else if (!currentAssignees.contains(developer)) {
                story.setAssignees(currentAssignees + ", " + developer);
            }

            JOptionPane.showMessageDialog(this,
                "Assigned '" + story.getTitle() + "' to " + developer);
            dispose();
        }
    }
}
