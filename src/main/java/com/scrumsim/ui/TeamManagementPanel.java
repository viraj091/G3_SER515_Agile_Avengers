package com.scrumsim.ui;

import com.scrumsim.model.Team;
import com.scrumsim.model.User;
import com.scrumsim.navigation.Navigator;
import com.scrumsim.service.TeamService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class TeamManagementPanel extends JPanel {

    private final Navigator navigator;
    private final TeamCardFactory cardFactory;
    private final User currentUser;
    private final TeamService teamService;

    public TeamManagementPanel(Navigator navigator, User currentUser, TeamService teamService) {
        this.navigator = navigator;
        this.currentUser = currentUser;
        this.teamService = teamService;
        this.cardFactory = new TeamCardFactory();

        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 40, 20, 40));

        add(createHeader(), BorderLayout.NORTH);

        add(createTeamList(), BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel title = new JLabel("Team Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        headerPanel.add(title, BorderLayout.WEST);

        if (currentUser.isScrumMaster()) {
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            buttonPanel.setOpaque(false);

            JButton manageRolesButton = new JButton("Manage Roles");
            manageRolesButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            manageRolesButton.addActionListener(e -> onManageRoles());

            JButton createTeamButton = new JButton("Create Team");
            createTeamButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            createTeamButton.addActionListener(e -> onCreateTeam());

            buttonPanel.add(manageRolesButton);
            buttonPanel.add(createTeamButton);

            headerPanel.add(buttonPanel, BorderLayout.EAST);
        }

        return headerPanel;
    }

    private JPanel createTeamList() {
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        List<Team> teams = teamService.getAllTeams();

        for (Team team : teams) {
            JPanel card = cardFactory.createTeamCard(team, this::onTeamSelected, this::onJoinTeam);
            listPanel.add(card);
            listPanel.add(Box.createVerticalStrut(10));
        }

        return listPanel;
    }

    private void onTeamSelected(String teamName) {
        navigator.showScrumSimulation(teamName);
    }

    private void onManageRoles() {
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
        ManageRolesDialog dialog = new ManageRolesDialog(parentFrame, teamService);
        dialog.setVisible(true);
    }

    private void onJoinTeam(Team team) {
        teamService.joinTeam(currentUser, team);
        refreshUI();
    }

    private void onCreateTeam() {
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
        CreateTeamDialog dialog = new CreateTeamDialog(parentFrame, teamService, currentUser);
        dialog.setVisible(true);

        if (dialog.wasTeamCreated()) {
            refreshUI();
        }
    }

    private void refreshUI() {
        removeAll();
        initializeUI();
        revalidate();
        repaint();
    }
}
