package com.scrumsim.navigation;

import com.scrumsim.ui.ScrumSimulationPanel;
import com.scrumsim.ui.TeamManagementPanel;
import com.scrumsim.service.ProgressCalculator;
import com.scrumsim.service.SprintProgressCalculator;

import javax.swing.*;

/**
 * Implementation of Navigator that manages JFrame-based navigation.
 * Follows SRP by only handling screen transitions.
 * Follows DIP by depending on the Navigator abstraction.
 */
public class FrameNavigator implements Navigator {

    private final JFrame frame;
    private final ProgressCalculator progressCalculator;

    public FrameNavigator(JFrame frame) {
        this.frame = frame;
        this.progressCalculator = new SprintProgressCalculator();
    }

    @Override
    public void showTeamManagement() {
        switchPanel(new TeamManagementPanel(this));
    }

    @Override
    public void showScrumSimulation(String teamName) {
        switchPanel(new ScrumSimulationPanel(this, teamName, progressCalculator));
    }

    /**
     * Helper method to switch the current panel in the frame.
     * Centralizes the logic for panel transitions.
     */
    private void switchPanel(JPanel newPanel) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(newPanel);
        frame.revalidate();
        frame.repaint();
    }
}
