package com.scrumsim.ui;

import com.scrumsim.model.Team;
import com.scrumsim.navigation.Navigator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Panel for displaying and managing teams in the Scrum Simulation Tool.
 * Follows SRP by only handling UI layout and delegating card creation to TeamCardFactory.
 * Follows DIP by depending on the Navigator abstraction instead of concrete navigation classes.
 */
public class TeamManagementPanel extends JPanel {

    private final Navigator navigator;
    private final TeamCardFactory cardFactory;

    /**
     * Create a new team management panel.
     * @param navigator Navigation interface for switching to other screens
     */
    public TeamManagementPanel(Navigator navigator) {
        this.navigator = navigator;
        this.cardFactory = new TeamCardFactory();

        initializeUI();
    }

    /**
     * Set up the UI layout and components.
     * Separates initialization logic for better readability.
     */
    private void initializeUI() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 40, 20, 40));

        // Header section
        add(createHeader(), BorderLayout.NORTH);

        // Team list section
        add(createTeamList(), BorderLayout.CENTER);
    }

    /**
     * Create the header component with title.
     */
    private JLabel createHeader() {
        JLabel title = new JLabel("Team Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        return title;
    }

    /**
     * Create the scrollable list of team cards.
     */
    private JPanel createTeamList() {
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        // Get teams from data source
        // In a real application, this would come from a service/repository (following DIP)
        List<Team> teams = getTeams();

        // Create and add a card for each team
        for (Team team : teams) {
            JPanel card = cardFactory.createTeamCard(team, this::onTeamSelected);
            listPanel.add(card);
            listPanel.add(Box.createVerticalStrut(10));
        }

        return listPanel;
    }

    /**
     * Callback when a team is selected.
     * Delegates navigation to the Navigator (following DIP).
     */
    private void onTeamSelected(String teamName) {
        navigator.showScrumSimulation(teamName);
    }

    /**
     * Get the list of available teams.
     * In a production application, this would be injected as a dependency
     * following DIP (e.g., TeamRepository interface).
     */
    private List<Team> getTeams() {
        return Arrays.asList(
                new Team("Team Alpha", "Scrum Master"),
                new Team("Team Beta", "Developer"),
                new Team("Team Gamma", "Product Owner")
        );
    }
}
