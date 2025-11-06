package com.scrumsim;

import com.scrumsim.navigation.FrameNavigator;
import com.scrumsim.navigation.Navigator;

import javax.swing.*;

public class MainApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainApp::createAndShowUI);
    }

    private static void createAndShowUI() {
        JFrame frame = new JFrame("Scrum Simulation Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 650);
        frame.setLocationRelativeTo(null);

        Navigator navigator = new FrameNavigator(frame);

        navigator.showTeamManagement();

        frame.setVisible(true);
    }
}
