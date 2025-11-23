package com.scrumsim.ui;

import com.scrumsim.model.Story;
import com.scrumsim.service.BacklogService;
import com.scrumsim.ui.backlog.BacklogDialogController;
import com.scrumsim.ui.backlog.StoryPriorityControl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class BacklogDialog extends JDialog {

    private final BacklogService backlogService;
    private final BacklogDialogController controller;
    private final List<Story> sprintStories;
    private List<Story> backlogStories;
    private JPanel storiesPanel;
    private JScrollPane scrollPane;

    public BacklogDialog(Frame parent, BacklogService backlogService, List<Story> sprintStories) {
        super(parent, "Product Backlog", true);
        this.backlogService = backlogService;
        this.sprintStories = sprintStories;
        this.backlogStories = backlogService.getBacklogStories(sprintStories);

        this.controller = new BacklogDialogController(backlogService, this::refreshBacklogList);

        setupUI();
        setSize(600, 400);
        setLocationRelativeTo(parent);
    }

    private void setupUI() {
        setLayout(new BorderLayout(10, 10));

        storiesPanel = new JPanel();
        storiesPanel.setLayout(new BoxLayout(storiesPanel, BoxLayout.Y_AXIS));
        storiesPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        renderStories();

        scrollPane = new JScrollPane(storiesPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton addStoryButton = new JButton("Add Story");
        addStoryButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addStoryButton.addActionListener(e -> handleAddStory());

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        closeButton.addActionListener(e -> dispose());

        buttonPanel.add(addStoryButton);
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
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

        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        StoryPriorityControl priorityControl = new StoryPriorityControl(story.getPriority());
        priorityControl.addUpButtonListener(e -> controller.handleIncreasePriority(story));
        priorityControl.addDownButtonListener(e -> controller.handleDecreasePriority(story));
        infoPanel.add(priorityControl);

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

    private void renderStories() {
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

    private void handleAddStory() {
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);

        JTextField titleField = new JTextField(20);
        JTextField descriptionField = new JTextField(20);
        JSpinner pointsSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 100, 1));

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Points:"));
        panel.add(pointsSpinner);

        int result = JOptionPane.showConfirmDialog(
            parentFrame,
            panel,
            "Create New Story",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String title = titleField.getText().trim();
            String description = descriptionField.getText().trim();
            int points = (Integer) pointsSpinner.getValue();

            if (title.isEmpty() || description.isEmpty()) {
                JOptionPane.showMessageDialog(
                    parentFrame,
                    "Title and description cannot be empty",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Story newStory = backlogService.createStory(title, description, points);

            if (newStory == null) {
                JOptionPane.showMessageDialog(
                    parentFrame,
                    "Failed to create story. Title may already exist.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            refreshBacklogList();

            JOptionPane.showMessageDialog(
                parentFrame,
                "Story created successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void refreshBacklogList() {
        backlogStories = backlogService.getBacklogStories(sprintStories);
        renderStories();
    }
}
