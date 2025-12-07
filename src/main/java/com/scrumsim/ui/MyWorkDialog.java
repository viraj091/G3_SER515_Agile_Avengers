package com.scrumsim.ui;

import com.scrumsim.model.Story;
import com.scrumsim.model.StoryStatus;
import com.scrumsim.model.User;
import com.scrumsim.repository.StoryRepository;
import com.scrumsim.service.StoryService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MyWorkDialog extends JDialog {

    private final StoryRepository repository;
    private final StoryService storyService;
    private final User currentUser;
    private JPanel storiesPanel;

    public MyWorkDialog(Frame parent, StoryRepository repository, StoryService storyService, User currentUser) {
        super(parent, "My Work", true);
        this.repository = repository;
        this.storyService = storyService;
        this.currentUser = currentUser;
        setSize(700, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        buildUI();
    }

    private void buildUI() {
        storiesPanel = new JPanel();
        storiesPanel.setLayout(new BoxLayout(storiesPanel, BoxLayout.Y_AXIS));
        loadStories();

        JScrollPane scrollPane = new JScrollPane(storiesPanel);
        add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadStories() {
        storiesPanel.removeAll();
        List<Story> myStories = repository.findByAssignee(currentUser.getName());

        for (Story story : myStories) {
            JPanel storyRow = new JPanel(new FlowLayout(FlowLayout.LEFT));

            JLabel storyLabel = new JLabel(story.getTitle() + " (" + story.getPoints() + " pts)");
            storyLabel.setPreferredSize(new Dimension(400, 25));
            storyRow.add(storyLabel);

            JComboBox<StoryStatus> statusDropdown = new JComboBox<>(StoryStatus.values());
            statusDropdown.setSelectedItem(story.getStatus());
            statusDropdown.addActionListener(e -> {
                StoryStatus newStatus = (StoryStatus) statusDropdown.getSelectedItem();
                if (storyService.updateStoryStatus(story.getId(), newStatus, currentUser)) {
                    loadStories();
                    storiesPanel.revalidate();
                    storiesPanel.repaint();
                }
            });
            storyRow.add(statusDropdown);
            storiesPanel.add(storyRow);
        }
    }
}