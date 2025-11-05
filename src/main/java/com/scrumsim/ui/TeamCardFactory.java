package com.scrumsim.ui;

import com.scrumsim.model.Team;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

/**
 * Factory for creating team card UI components.
 * Follows SRP by only handling team card creation and click interactions.
 */
public class TeamCardFactory {

    /**
     * Create a clickable card for a team.
     * @param team The team to create a card for
     * @param onTeamClick Callback invoked when the team card is clicked
     * @return A JPanel representing the team card
     */
    public JPanel createTeamCard(Team team, Consumer<String> onTeamClick) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(10, 10, 10, 10)
        ));

        // Team name and role labels
        JLabel name = new JLabel(team.getName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel role = new JLabel(team.getRole());
        role.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        role.setForeground(Color.GRAY);

        // Info panel
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);
        info.add(name);
        info.add(role);

        card.add(info, BorderLayout.CENTER);
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add click listener to trigger navigation
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onTeamClick.accept(team.getName());
            }
        });

        return card;
    }
}
