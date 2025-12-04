package com.example.mockup;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class TeamManagementPanel extends JPanel {

    private static class Team {
        String name;
        String role;
        Team(String name, String role) { this.name = name; this.role = role; }
    }

    public TeamManagementPanel(JFrame parent, Consumer<String> onTeamClick) {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 40, 20, 40));

        // Header
        JLabel title = new JLabel("Team Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        List<Team> teams = Arrays.asList(
                new Team("Team Alpha", "Scrum Master"),
                new Team("Team Beta", "Developer"),
                new Team("Team Gamma", "Product Owner")
        );

        for (Team team : teams) {
            JPanel card = createTeamCard(team);
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    onTeamClick.accept(team.name);
                }
            });
            listPanel.add(card);
            listPanel.add(Box.createVerticalStrut(10));
        }

        add(listPanel, BorderLayout.CENTER);
    }

    private JPanel createTeamCard(Team team) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(10, 10, 10, 10)
        ));
        JLabel name = new JLabel(team.name);
        name.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel role = new JLabel(team.role);
        role.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        role.setForeground(Color.GRAY);

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);
        info.add(name);
        info.add(role);

        card.add(info, BorderLayout.CENTER);
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return card;
    }
}
