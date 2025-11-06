package com.scrumsim.ui;

import com.scrumsim.model.Story;
import com.scrumsim.model.StoryStatus;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StoryCardFactory {

    private final Runnable onStoryChanged;

    public StoryCardFactory(Runnable onStoryChanged) {
        this.onStoryChanged = onStoryChanged;
    }

    public JPanel createStoryCard(Story story) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(8, 10, 8, 10)
        ));

        JLabel title = new JLabel(story.getTitle());
        title.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel statusPanel = createStatusPanel(story);

        JSpinner pointSpinner = createPointsSpinner(story);

        JLabel assignees = new JLabel(story.getAssignees());
        assignees.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        assignees.setForeground(Color.GRAY);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        top.setOpaque(false);
        top.add(title);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        bottom.setOpaque(false);
        bottom.add(statusPanel);
        bottom.add(new JLabel("Points:"));
        bottom.add(pointSpinner);
        bottom.add(assignees);

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);
        wrapper.add(top);
        wrapper.add(bottom);

        card.add(wrapper, BorderLayout.CENTER);
        return card;
    }

    private JPanel createStatusPanel(Story story) {
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        statusPanel.setOpaque(false);

        JLabel statusColor = new JLabel("\u25A0");
        statusColor.setForeground(story.getStatus().getColor());

        String[] statuses = new String[StoryStatus.values().length];
        for (int i = 0; i < StoryStatus.values().length; i++) {
            statuses[i] = StoryStatus.values()[i].getDisplayName();
        }
        JComboBox<String> statusDropdown = new JComboBox<>(statuses);
        statusDropdown.setSelectedItem(story.getStatus().getDisplayName());

        statusDropdown.addActionListener(e -> {
            String selected = (String) statusDropdown.getSelectedItem();
            story.setStatus(StoryStatus.fromDisplayName(selected));
            statusColor.setForeground(story.getStatus().getColor());
            onStoryChanged.run();
        });

        statusPanel.add(statusColor);
        statusPanel.add(statusDropdown);
        return statusPanel;
    }

    private JSpinner createPointsSpinner(Story story) {
        JSpinner pointSpinner = new JSpinner(new SpinnerNumberModel(story.getPoints(), 0, 100, 1));
        pointSpinner.addChangeListener(e -> {
            story.setPoints((Integer) pointSpinner.getValue());
            onStoryChanged.run();
        });
        return pointSpinner;
    }
}
