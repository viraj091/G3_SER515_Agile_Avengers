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

        // Story title in the center-left
        JLabel title = new JLabel(story.getTitle());
        title.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Edit button at bottom-right
        JButton editButton = new JButton("Edit User Story");
        editButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        editButton.setFocusPainted(false);
        editButton.addActionListener(e -> onEditStory.accept(story));

        // Button panel aligned to the right
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(editButton);

        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.add(title, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        card.add(contentPanel, BorderLayout.CENTER);
        return card;
    }
}
