package com.example.mockup;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScrumSimulationPanel extends JPanel {

    private static class Story {
        String title, status, assignees;
        int points;
        Story(String title, String status, int points, String assignees) {
            this.title = title; this.status = status; this.points = points; this.assignees = assignees;
        }
    }

    private static class Member {
        String initials, name, role;
        boolean online;
        Member(String initials, String name, String role, boolean online) {
            this.initials = initials; this.name = name; this.role = role; this.online = online;
        }
    }

    private final JLabel progressLabel;
    private final List<Story> stories;   // backlog stories

    public ScrumSimulationPanel(JFrame parent, String teamName) {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(new Color(245, 248, 250));

        // --- Data setup ---
        stories = new ArrayList<>(Arrays.asList(
                new Story("Implement user authentication system", "In Progress", 8, "Sarah Chen, Mike Johnson"),
                new Story("Design dashboard UI components", "To Do", 5, "Emma Wilson"),
                new Story("Setup CI/CD pipeline", "Done", 13, "Alex Rodriguez, Sarah Chen"),
                new Story("Create API documentation", "New", 3, "Mike Johnson")
        ));

        List<Member> members = Arrays.asList(
                new Member("SC", "Sarah Chen", "Product Owner", true),
                new Member("MJ", "Mike Johnson", "Developer", true),
                new Member("EW", "Emma Wilson", "Designer", false),
                new Member("AR", "Alex Rodriguez", "Developer", true),
                new Member("LA", "Lisa Anderson", "Scrum Master", true)
        );

        // --- Header ---
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel title = new JLabel("Scrum Simulation Tool - " + teamName, SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        progressLabel = new JLabel("", SwingConstants.CENTER);
        progressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        progressLabel.setForeground(Color.DARK_GRAY);

        header.add(title, BorderLayout.NORTH);
        header.add(progressLabel, BorderLayout.SOUTH);

        add(header, BorderLayout.NORTH);

        // --- Content Split ---
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setResizeWeight(0.5);

        // Left: Backlog
        JPanel backlog = new JPanel();
        backlog.setLayout(new BoxLayout(backlog, BoxLayout.Y_AXIS));
        backlog.setBorder(new EmptyBorder(10, 10, 10, 10));
        backlog.setOpaque(false);

        JLabel backlogHeader = new JLabel("Product Backlog  " + stories.size() + " stories");
        backlogHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backlog.add(backlogHeader);
        backlog.add(Box.createVerticalStrut(10));

        for (Story s : stories) {
            backlog.add(createStoryCard(s));
            backlog.add(Box.createVerticalStrut(8));
        }

        // Right: Team Members
        JPanel membersPanel = new JPanel();
        membersPanel.setLayout(new BoxLayout(membersPanel, BoxLayout.Y_AXIS));
        membersPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        membersPanel.setOpaque(false);

        long onlineCount = members.stream().filter(m -> m.online).count();
        JLabel memberHeader = new JLabel("Team Members  " + onlineCount + " online  " + members.size() + " total");
        memberHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));
        membersPanel.add(memberHeader);
        membersPanel.add(Box.createVerticalStrut(10));

        for (Member m : members) {
            membersPanel.add(createMemberCard(m));
            membersPanel.add(Box.createVerticalStrut(6));
        }

        split.setLeftComponent(new JScrollPane(backlog));
        split.setRightComponent(new JScrollPane(membersPanel));

        add(split, BorderLayout.CENTER);

        // --- Back Button ---
        JButton backBtn = new JButton(" Back to Team Management");
        backBtn.addActionListener(e -> MainApp.showTeamManagement());
        add(backBtn, BorderLayout.SOUTH);

        // Update initial progress
        updateProgress();
    }

    private JPanel createStoryCard(Story s) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(8, 10, 8, 10)
        ));

        JLabel title = new JLabel(s.title);
        title.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Dropdown for status
        String[] statuses = {"New", "To Do", "In Progress", "Done"};
        JComboBox<String> statusDropdown = new JComboBox<>(statuses);
        statusDropdown.setSelectedItem(s.status);

        // Color label next to dropdown
        JLabel statusColor = new JLabel("\u25A0"); // square
        updateStatusColor(statusColor, s.status);

        // Listener for dropdown
        statusDropdown.addActionListener(e -> {
            s.status = (String) statusDropdown.getSelectedItem();
            updateStatusColor(statusColor, s.status);
            updateProgress();
        });

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        statusPanel.setOpaque(false);
        statusPanel.add(statusColor);
        statusPanel.add(statusDropdown);

        // Editable points spinner
        JSpinner pointSpinner = new JSpinner(new SpinnerNumberModel(s.points, 0, 100, 1));
        pointSpinner.addChangeListener(e -> {
            s.points = (Integer) pointSpinner.getValue();
            updateProgress();
        });

        JLabel assignees = new JLabel(s.assignees);
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

    private void updateStatusColor(JLabel label, String status) {
        switch (status) {
            case "In Progress":
                label.setForeground(new Color(50, 80, 200));
                break;
            case "Done":
                label.setForeground(new Color(0, 128, 0));
                break;
            case "To Do":
                label.setForeground(Color.DARK_GRAY);
                break;
            case "New":
            default:
                label.setForeground(Color.ORANGE.darker());
        }
    }

    private void updateProgress() {
    int totalPoints = 30; 
    int currentPoints = stories.stream().mapToInt(s -> s.points).sum();
    progressLabel.setText("Sprint Progress: " + currentPoints + "/" + totalPoints + " points");
}

    private JPanel createMemberCard(Member m) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(8, 10, 8, 10)
        ));

        JLabel avatar = new JLabel(m.initials, SwingConstants.CENTER);
        avatar.setOpaque(true);
        avatar.setBackground(new Color(230, 240, 255));
        avatar.setForeground(new Color(50, 80, 200));
        avatar.setPreferredSize(new Dimension(40, 40));
        avatar.setFont(new Font("Segoe UI", Font.BOLD, 12));

        JLabel name = new JLabel(m.name);
        name.setFont(new Font("Segoe UI", Font.BOLD, 13));
        JLabel role = new JLabel(m.role);
        role.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        role.setForeground(Color.GRAY);

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);
        info.add(name);
        info.add(role);

        JLabel statusDot = new JLabel(m.online ? "\u25CF" : "\u25CB");
        statusDot.setForeground(m.online ? Color.GREEN : Color.LIGHT_GRAY);

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);
        left.add(avatar);
        left.add(info);

        card.add(left, BorderLayout.WEST);
        card.add(statusDot, BorderLayout.EAST);
        return card;
    }
}
