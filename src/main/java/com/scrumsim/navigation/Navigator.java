package com.scrumsim.navigation;

/**
 * Interface for application navigation between screens.
 * Follows ISP by defining only navigation-related methods.
 * Follows DIP by allowing panels to depend on this abstraction instead of concrete implementations.
 */
public interface Navigator {

    /**
     * Navigate to the team management screen.
     */
    void showTeamManagement();

    /**
     * Navigate to the scrum simulation screen for a specific team.
     * @param teamName The name of the team to simulate
     */
    void showScrumSimulation(String teamName);
}
