package com.scrumsim.navigation;

import com.scrumsim.MainApp;
import com.scrumsim.model.User;
import com.scrumsim.service.TeamService;
import com.scrumsim.ui.ScrumSimulationPanel;
import com.scrumsim.ui.TeamManagementPanel;
import com.scrumsim.service.ProgressCalculator;
import com.scrumsim.service.SprintProgressCalculator;

import javax.swing.*;

public class FrameNavigator implements Navigator {

    private final JFrame frame;
    private final ProgressCalculator progressCalculator;
    private final User currentUser;
    private final TeamService teamService;

    public FrameNavigator(JFrame frame, User currentUser, TeamService teamService) {
        if (frame == null) {
            throw new IllegalArgumentException("Frame cannot be null");
        }

        this.frame = frame;
        this.currentUser = currentUser;
        this.teamService = teamService;
        this.progressCalculator = new SprintProgressCalculator();
    }

    @Override
    public void showTeamManagement() {
        switchPanel(new TeamManagementPanel(this, currentUser, teamService));
    }

    @Override
    public void showScrumSimulation(String teamName) {
        switchPanel(new ScrumSimulationPanel(this, teamName, progressCalculator, currentUser));
    }

    @Override
    public void showLogin() {
        MainApp.logout();
    }

    private void switchPanel(JPanel newPanel) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(newPanel);
        frame.revalidate();
        frame.repaint();
    }
}