package com.scrumsim.ui;

import com.scrumsim.model.Team;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class TeamCardFactory {

    public JPanel createTeamCard(Team team, Consumer<String> onTeamClick, Consumer<Team> onDeleteClick) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(10, 10, 10, 10)
        ));

        JLabel name = new JLabel(team.getName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel role = new JLabel(team.getRole());
        role.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        role.setForeground(Color.GRAY);

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);
        info.add(name);
        info.add(role);

        card.add(info, BorderLayout.CENTER);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        deleteButton.addActionListener(e -> onDeleteClick.accept(team));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(deleteButton);
        card.add(buttonPanel, BorderLayout.SOUTH);

        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onTeamClick.accept(team.getName());
            }
        });

        return card;
    }
}
