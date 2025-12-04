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
    private final RolePermissionManager rolePermissionManager;

    public TeamManagementPanel(Navigator navigator, User currentUser, TeamService teamService) {
        this.navigator = navigator;
        this.currentUser = currentUser;
        this.teamService = teamService;
        this.cardFactory = new TeamCardFactory();
        this.rolePermissionManager = new RolePermissionManager();

        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 40, 20, 40));

        add(createHeader(), BorderLayout.NORTH);

        add(createTeamList(), BorderLayout.CENTER);

        add(createFooter(), BorderLayout.SOUTH);
    }

    private JPanel createHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);

        JLabel title = new JLabel("Team Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        leftPanel.add(title);

        JLabel userInfo = new JLabel("Logged in as: " + currentUser.getName() + " (" + currentUser.getRole().getDisplayName() + ")");
        userInfo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        userInfo.setForeground(new Color(100, 100, 100));
        leftPanel.add(userInfo);

        headerPanel.add(leftPanel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);

        if (rolePermissionManager.shouldShowButton(currentUser, "Manage Roles")) {
            JButton manageRolesButton = new JButton("Manage Roles");
            manageRolesButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            manageRolesButton.addActionListener(e -> onManageRoles());
            rolePermissionManager.applyButtonPermission(manageRolesButton, currentUser, "Manage Roles");
            buttonPanel.add(manageRolesButton);
        }

        if (rolePermissionManager.shouldShowButton(currentUser, "Create Team")) {
            JButton createTeamButton = new JButton("Create Team");
            createTeamButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            createTeamButton.addActionListener(e -> onCreateTeam());
            rolePermissionManager.applyButtonPermission(createTeamButton, currentUser, "Create Team");
            buttonPanel.add(createTeamButton);
        }

        if (buttonPanel.getComponentCount() > 0) {
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
        ManageRolesDialog dialog = new ManageRolesDialog(parentFrame, teamService, this::refreshUI);
        dialog.setVisible(true);
    }

    private void onJoinTeam(Team team) {
        // Step 1: Check if user is already a member
        if (teamService.isUserInTeam(currentUser, team)) {
            JOptionPane.showMessageDialog(
                this,
                "You are already part of this team.",
                "Already a Member",
                JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        // Step 2: Show confirmation dialog
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Do you want to join this team?",
            "Confirm Join",
            JOptionPane.YES_NO_OPTION
        );

        // Step 3: If YES, join the team
        if (confirm == JOptionPane.YES_OPTION) {
            teamService.joinTeam(currentUser, team);
            refreshUI();
        }
    }

    private void onCreateTeam() {
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
        CreateTeamDialog dialog = new CreateTeamDialog(parentFrame, teamService, currentUser);
        dialog.setVisible(true);

        if (dialog.wasTeamCreated()) {
            refreshUI();
        }
    }

    private JPanel createFooter() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setOpaque(false);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        logoutButton.setForeground(Color.BLACK);
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(e -> onLogout());

        footerPanel.add(logoutButton);

        return footerPanel;
    }

    private void onLogout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            navigator.showLogin();
        }
    }

    private void refreshUI() {
        removeAll();
        initializeUI();
        revalidate();
        repaint();
    }
}