package com.scrumsim;

import com.scrumsim.navigation.FrameNavigator;
import com.scrumsim.navigation.Navigator;

import javax.swing.*;

public class MainApp {

    public static void main(String[] args) {
        // Launch UI on the Event Dispatch Thread for thread safety
        SwingUtilities.invokeLater(MainApp::createAndShowUI);
    }

    /**
     * Initialize and display the main application window.
     * Creates the frame and sets up navigation through dependency injection.
     */
    private static void createAndShowUI() {
        // Create the main application window
        JFrame frame = new JFrame("Scrum Simulation Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 650);
        frame.setLocationRelativeTo(null);

        // Create navigator and inject it as a dependency
        // This follows DIP - MainApp depends on the Navigator abstraction
        Navigator navigator = new FrameNavigator(frame);

        // Show the initial screen (team management)
        navigator.showTeamManagement();

        // Make the window visible
        frame.setVisible(true);
    }
}
