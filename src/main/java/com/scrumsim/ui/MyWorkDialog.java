package com.scrumsim.ui;

import com.scrumsim.model.Story;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MyWorkDialog extends JDialog {

    public MyWorkDialog(Frame parent, String currentUserName, List<Story> allStories) {
        super(parent, "My Work", true);

        setSize(600, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Header label
        JLabel headerLabel = new JLabel("My Assigned Stories - " + currentUserName);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(headerLabel, BorderLayout.NORTH);

        // Stories panel
        JPanel storiesPanel = new JPanel();
        storiesPanel.setLayout(new BoxLayout(storiesPanel, BoxLayout.Y_AXIS));

        // Find stories assigned to current user
        int count = 0;
        for (Story story : allStories) {
            String assignees = story.getAssignees();
            if (assignees != null && assignees.contains(currentUserName)) {
                storiesPanel.add(createStoryCard(story));
                storiesPanel.add(Box.createVerticalStrut(10));
                count++;
            }
        }

        // If no stories found
        if (count == 0) {
            JLabel noWorkLabel = new JLabel("You have no assigned stories.");
            noWorkLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            storiesPanel.add(noWorkLabel);
        }

        JScrollPane scrollPane = new JScrollPane(storiesPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);
    }

    private JPanel createStoryCard(Story story) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel titleLabel = new JLabel("Title: " + story.getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel statusLabel = new JLabel("Status: " + story.getStatus());
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JLabel pointsLabel = new JLabel("Points: " + story.getPoints());
        pointsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        card.add(titleLabel);
        card.add(statusLabel);
        card.add(pointsLabel);

        return card;
    }
}
