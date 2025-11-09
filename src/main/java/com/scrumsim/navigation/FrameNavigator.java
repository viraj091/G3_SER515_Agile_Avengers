package com.scrumsim.navigation;

import com.scrumsim.model.User;
import com.scrumsim.service.TeamService;
import com.scrumsim.ui.ScrumSimulationPanel;
import com.scrumsim.ui.TeamManagementPanel;
import com.scrumsim.service.ProgressCalculator;
import com.scrumsim.service.SprintProgressCalculator;
import com.scrumsim.store.SessionManager;

import javax.swing.*;

public class FrameNavigator implements Navigator {

    private final JFrame frame;
    private final ProgressCalculator progressCalculator;
    private final User currentUser;
    private final TeamService teamService;
    private final SessionManager sessionManager;
    private final String currentSessionId;

    public FrameNavigator(JFrame frame, User currentUser, TeamService teamService,
                          SessionManager sessionManager, String currentSessionId) {
        if (frame == null) {
            throw new IllegalArgumentException("Frame cannot be null");
        }
        if (sessionManager == null) {
            throw new IllegalArgumentException("SessionManager cannot be null");
        }
        if (currentSessionId == null || currentSessionId.trim().isEmpty()) {
            throw new IllegalArgumentException("Current session ID cannot be null or empty");
        }

        this.frame = frame;
        this.currentUser = currentUser;
        this.teamService = teamService;
        this.sessionManager = sessionManager;
        this.currentSessionId = currentSessionId;
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

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public String getCurrentSessionId() {
        return currentSessionId;
    }

    private void switchPanel(JPanel newPanel) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(newPanel);
        frame.revalidate();
        frame.repaint();
    }
}