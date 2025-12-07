package com.scrumsim.ui;

import com.scrumsim.model.Story;
import com.scrumsim.repository.StoryRepository;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MyWorkDialog extends JDialog {

    public MyWorkDialog(Frame parent, StoryRepository repository, String userName) {
        super(parent, "My Work", true);
        setSize(600, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel storiesPanel = new JPanel();
        storiesPanel.setLayout(new BoxLayout(storiesPanel, BoxLayout.Y_AXIS));

        List<Story> myStories = repository.findByAssignee(userName);

        for (Story story : myStories) {
            JLabel storyLabel = new JLabel(story.getTitle() + " [" + story.getStatus() + "] (" + story.getPoints() + " pts)");
            storyLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            storiesPanel.add(storyLabel);
        }

        JScrollPane scrollPane = new JScrollPane(storiesPanel);
        add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}