package com.scrumsim.navigation;

import com.scrumsim.model.User;
import com.scrumsim.repository.TeamRepository;
import com.scrumsim.ui.ScrumSimulationPanel;
import com.scrumsim.ui.TeamManagementPanel;
import com.scrumsim.service.ProgressCalculator;
import com.scrumsim.service.SprintProgressCalculator;

import javax.swing.*;

public class FrameNavigator implements Navigator {

    private final JFrame frame;
    private final ProgressCalculator progressCalculator;
    private final User currentUser;
    private final TeamRepository teamRepository;
    private final Runnable onCreateTeam;

    public FrameNavigator(JFrame frame, User currentUser, TeamRepository teamRepository, Runnable onCreateTeam) {
        this.frame = frame;
        this.currentUser = currentUser;
        this.teamRepository = teamRepository;
        this.onCreateTeam = onCreateTeam;
        this.progressCalculator = new SprintProgressCalculator();
    }

    @Override
    public void showTeamManagement() {
        switchPanel(new TeamManagementPanel(this, currentUser, teamRepository, onCreateTeam));
    }

    @Override
    public void showScrumSimulation(String teamName) {
        switchPanel(new ScrumSimulationPanel(this, teamName, progressCalculator));
    }

    private void switchPanel(JPanel newPanel) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(newPanel);
        frame.revalidate();
        frame.repaint();
    }
}