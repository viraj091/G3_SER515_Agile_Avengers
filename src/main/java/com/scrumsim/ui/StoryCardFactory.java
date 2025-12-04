package com.scrumsim.ui;

import com.scrumsim.model.Story;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.Consumer;


public class StoryCardFactory {

    private final Consumer<Story> onEditStory;

    public StoryCardFactory(Consumer<Story> onEditStory) {
        this.onEditStory = onEditStory;
    }

    public JPanel createStoryCard(Story story) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(10, 12, 10, 12)
        ));

        // Story title
        JLabel title = new JLabel(story.getTitle());
        title.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Below part is for assignees label ( basically to show who is assigned)
        String assignees = story.getAssignees();
        JLabel assigneesLabel = new JLabel();
        assigneesLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        assigneesLabel.setForeground(new Color(100, 100, 100));

        if (assignees != null && !assignees.isEmpty()) {
            assigneesLabel.setText("Assigned to: " + assignees);
        } else {
            assigneesLabel.setText("Not assigned");
        }

        // Edit button at bottom-right
        JButton editButton = new JButton("Edit User Story");
        editButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        editButton.setFocusPainted(false);
        editButton.addActionListener(e -> onEditStory.accept(story));

        // Button panel aligned to the right
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(editButton);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.add(title);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(assigneesLabel);

        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.add(textPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        card.add(contentPanel, BorderLayout.CENTER);
        return card;
    }
}
