package com.scrumsim.ui;

import com.scrumsim.model.Story;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MyWorkDialog extends JDialog {

    public MyWorkDialog(Frame parent, String currentUserName, List<Story> allStories) {
        super(parent, "My Work", true);

        setSize(800, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        JLabel headerLabel = new JLabel("My Assigned Stories");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(headerLabel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Only show current user's section
        JPanel memberSection = createMemberSection(currentUserName, allStories, currentUserName);
        mainPanel.add(memberSection);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        closeButton.addActionListener(e -> dispose());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createMemberSection(String memberName, List<Story> allStories, String currentUserName) {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        section.setBackground(new Color(173, 216, 230));

        List<Story> memberStories = getStoriesForMember(memberName, allStories);

        if (memberStories.isEmpty()) {
            JLabel noStoriesLabel = new JLabel("  No assigned stories");
            noStoriesLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
            noStoriesLabel.setForeground(Color.GRAY);
            section.add(noStoriesLabel);
        } else {
            for (Story story : memberStories) {
                JPanel storyCard = createCompactStoryCard(story);
                section.add(storyCard);
                section.add(Box.createVerticalStrut(5));
            }
        }

        return section;
    }

    private List<Story> getStoriesForMember(String memberName, List<Story> allStories) {
        List<Story> memberStories = new ArrayList<>();
        String firstName = memberName.split(" ")[0];

        for (Story story : allStories) {
            String assignees = story.getAssignees();
            if (assignees != null && !assignees.trim().isEmpty()) {
                if (assignees.contains(memberName) || assignees.contains(firstName)) {
                    memberStories.add(story);
                }
            }
        }

        return memberStories;
    }

    private JPanel createCompactStoryCard(Story story) {
        JPanel card = new JPanel();
        card.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        JLabel bulletLabel = new JLabel("•");
        bulletLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel titleLabel = new JLabel(story.getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JLabel statusLabel = new JLabel("[" + story.getStatus() + "]");
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        statusLabel.setForeground(getStatusColor(story.getStatus()));

        JLabel pointsLabel = new JLabel("(" + story.getPoints() + " pts)");
        pointsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        pointsLabel.setForeground(Color.DARK_GRAY);

        card.add(bulletLabel);
        card.add(titleLabel);
        card.add(statusLabel);
        card.add(pointsLabel);

        return card;
    }

    private Color getStatusColor(Object status) {
        String statusStr = status.toString().toUpperCase();
        if (statusStr.contains("DONE")) {
            return new Color(0, 128, 0);
        } else if (statusStr.contains("PROGRESS")) {
            return new Color(0, 0, 200);
        } else {
            return Color.DARK_GRAY;
        }
    }
}
