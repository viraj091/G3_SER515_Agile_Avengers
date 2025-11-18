package com.scrumsim.ui;

import com.scrumsim.model.Story;
import com.scrumsim.service.BacklogService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class BacklogDialog extends JDialog {

    private List<Story> backlogStories;
    private final BacklogService backlogService;
    private final List<Story> sprintStories;
    private JPanel storiesPanel;

    public BacklogDialog(Frame parent, List<Story> backlogStories, BacklogService backlogService, List<Story> sprintStories) {
        super(parent, "Product Backlog", true);
        this.backlogStories = backlogStories;
        this.backlogService = backlogService;
        this.sprintStories = sprintStories;

        setupUI();
        setSize(600, 400);
        setLocationRelativeTo(parent);
    }

    private void setupUI() {
        setLayout(new BorderLayout(10, 10));

        storiesPanel = new JPanel();
        storiesPanel.setLayout(new BoxLayout(storiesPanel, BoxLayout.Y_AXIS));
        storiesPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        refreshStoriesPanel();

        JScrollPane scrollPane = new JScrollPane(storiesPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton addButton = new JButton("Add");
        addButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addButton.addActionListener(e -> onAddStory());
        buttonPanel.add(addButton);

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void refreshStoriesPanel() {
        storiesPanel.removeAll();

        if (backlogStories.isEmpty()) {
            JLabel emptyLabel = new JLabel("No stories in backlog");
            emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            emptyLabel.setForeground(Color.GRAY);
            storiesPanel.add(emptyLabel);
        } else {
            for (Story story : backlogStories) {
                JPanel storyCard = createStoryCard(story);
                storiesPanel.add(storyCard);
                storiesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        storiesPanel.revalidate();
        storiesPanel.repaint();
    }

    private void onAddStory() {
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
        UserStoryEditDialog dialog = new UserStoryEditDialog(parentFrame);
        dialog.setVisible(true);

        if (dialog.wasSaved()) {
            Story newStory = dialog.getStory();
            Story savedStory = backlogService.createStory(
                newStory.getTitle(),
                newStory.getDescription(),
                newStory.getPoints()
            );

            if (savedStory != null) {
                backlogStories = backlogService.getBacklogStories(sprintStories);
                refreshStoriesPanel();
                JOptionPane.showMessageDialog(
                    this,
                    "Story added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Failed to add story. Title may already exist or input is invalid.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private JPanel createStoryCard(Story story) {
        JPanel card = new JPanel(new BorderLayout(10, 5));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(Color.WHITE);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(story.getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        infoPanel.add(titleLabel);

        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        JLabel pointsLabel = new JLabel("Points: " + story.getPoints());
        pointsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        pointsLabel.setForeground(new Color(100, 100, 100));
        infoPanel.add(pointsLabel);

        if (story.getDescription() != null && !story.getDescription().isEmpty()) {
            infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            JLabel descLabel = new JLabel(story.getDescription());
            descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            descLabel.setForeground(new Color(80, 80, 80));
            infoPanel.add(descLabel);
        }

        card.add(infoPanel, BorderLayout.CENTER);

        return card;
    }
}