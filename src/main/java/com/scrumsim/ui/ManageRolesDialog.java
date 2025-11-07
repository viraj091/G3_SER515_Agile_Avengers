package com.scrumsim.ui;

import com.scrumsim.model.Team;
import com.scrumsim.service.TeamService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageRolesDialog extends JDialog {

    private final TeamService teamService;
    private final String[] availableMembers = {
        "Viraj Rathor",
        "Sairaj Dalvi",
        "Shreyas Revankar",
        "Pranav Irlapale",
        "Gunjan Purohit"
    };
    private final String[] availableRoles = {
        "Scrum Master",
        "Product Owner",
        "Developer",
        "Tester",
        "Designer"
    };
    private final Map<String, Map<String, JComboBox<String>>> teamMemberRoles;

    public ManageRolesDialog(Frame parent, TeamService teamService) {
        super(parent, "Manage Team Member Roles", true);

        if (teamService == null) {
            throw new IllegalArgumentException("TeamService cannot be null");
        }

        this.teamService = teamService;
        this.teamMemberRoles = new HashMap<>();

        initializeUI();
        setSize(900, 600);
        setLocationRelativeTo(parent);
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(createTeamsScrollPanel(), BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Manage Team Member Roles");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerPanel.add(title, BorderLayout.NORTH);

        JLabel info = new JLabel("Select members and assign roles");
        info.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        info.setForeground(Color.GRAY);
        headerPanel.add(info, BorderLayout.SOUTH);

        return headerPanel;
    }

    private JScrollPane createTeamsScrollPanel() {
        JPanel teamsContainer = new JPanel();
        teamsContainer.setLayout(new BoxLayout(teamsContainer, BoxLayout.Y_AXIS));
        teamsContainer.setBackground(Color.WHITE);

        List<Team> teams = teamService.getAllTeams();

        for (Team team : teams) {
            teamsContainer.add(createTeamPanel(team));
            teamsContainer.add(Box.createVerticalStrut(20));
        }

        JScrollPane scrollPane = new JScrollPane(teamsContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);

        return scrollPane;
    }

    private JPanel createTeamPanel(Team team) {
        JPanel wrapper = new JPanel(new BorderLayout(10, 10));
        wrapper.setBackground(Color.WHITE);
        wrapper.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Simple Team Title (no blue border)
        JLabel teamLabel = new JLabel(team.getName());
        teamLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        teamLabel.setBorder(new EmptyBorder(0, 0, 10, 0));

        wrapper.add(teamLabel, BorderLayout.NORTH);
        wrapper.add(createMembersGrid(team), BorderLayout.CENTER);

        // Subtle bottom separator for visual clarity
        wrapper.setBorder(BorderFactory.createMatteBorder(
            0, 0, 1, 0, new Color(220, 220, 220))
        );

        return wrapper;
    }

    private JPanel createMembersGrid(Team team) {
        JPanel membersPanel = new JPanel(new GridBagLayout());
        membersPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;

        JLabel headerMember = new JLabel("Team Member");
        headerMember.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        membersPanel.add(headerMember, gbc);

        JLabel headerRole = new JLabel("Role");
        headerRole.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 1;
        membersPanel.add(headerRole, gbc);

        Map<String, JComboBox<String>> memberRoleMap = new HashMap<>();

        for (int i = 0; i < 3; i++) {
            gbc.gridy = i + 1;

            gbc.gridx = 0;
            JComboBox<String> memberCombo = new JComboBox<>(availableMembers);
            memberCombo.setPreferredSize(new Dimension(220, 30));
            memberCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            if (i < availableMembers.length) {
                memberCombo.setSelectedIndex(i);
            }
            membersPanel.add(memberCombo, gbc);

            gbc.gridx = 1;
            JComboBox<String> roleCombo = new JComboBox<>(availableRoles);
            roleCombo.setPreferredSize(new Dimension(220, 30));
            roleCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            roleCombo.setSelectedIndex(2); // default: Developer
            membersPanel.add(roleCombo, gbc);

            String key = team.getName() + "_member_" + i;
            memberRoleMap.put(key, roleCombo);
        }

        teamMemberRoles.put(team.getName(), memberRoleMap);

        return membersPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        closeButton.addActionListener(e -> dispose());

        buttonPanel.add(closeButton);

        return buttonPanel;
    }
}