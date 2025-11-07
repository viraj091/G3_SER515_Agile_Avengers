package com.scrumsim;

import com.scrumsim.model.User;
import com.scrumsim.model.UserRole;
import com.scrumsim.navigation.FrameNavigator;
import com.scrumsim.navigation.Navigator;
import com.scrumsim.repository.InMemoryTeamRepository;
import com.scrumsim.repository.TeamRepository;

import javax.swing.*;

public class MainApp {

    private static Navigator navigator;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainApp::createAndShowUI);
    }

    private static void createAndShowUI() {
        JFrame frame = new JFrame("Scrum Simulation Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 650);
        frame.setLocationRelativeTo(null);

        User currentUser = new User("John Doe", UserRole.SCRUM_MASTER);
        TeamRepository teamRepository = new InMemoryTeamRepository();

        Runnable onCreateTeam = () -> {
            navigator.showTeamManagement();
        };

        navigator = new FrameNavigator(frame, currentUser, teamRepository, onCreateTeam);

        navigator.showTeamManagement();

        frame.setVisible(true);
    }
}