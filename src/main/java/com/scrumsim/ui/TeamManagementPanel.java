package com.scrumsim.ui;

import com.scrumsim.model.Team;
import com.scrumsim.navigation.Navigator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class TeamManagementPanel extends JPanel {

    private final Navigator navigator;
    private final TeamCardFactory cardFactory;

    public TeamManagementPanel(Navigator navigator) {
        this.navigator = navigator;
        this.cardFactory = new TeamCardFactory();

        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 40, 20, 40));

        add(createHeader(), BorderLayout.NORTH);

        add(createTeamList(), BorderLayout.CENTER);
    }

    private JLabel createHeader() {
        JLabel title = new JLabel("Team Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        return title;
    }

    private JPanel createTeamList() {
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        List<Team> teams = getTeams();

        for (Team team : teams) {
            JPanel card = cardFactory.createTeamCard(team, this::onTeamSelected);
            listPanel.add(card);
            listPanel.add(Box.createVerticalStrut(10));
        }

        return listPanel;
    }

    private void onTeamSelected(String teamName) {
        navigator.showScrumSimulation(teamName);
    }

    private List<Team> getTeams() {
        return Arrays.asList(
                new Team("Team Alpha", "Scrum Master"),
                new Team("Team Beta", "Developer"),
                new Team("Team Gamma", "Product Owner")
        );
    }
}
