package com.example.mockup;

import javax.swing.*;

public class MainApp {
    private static JFrame frame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainApp::createAndShowUI);
    }

    private static void createAndShowUI() {
        frame = new JFrame("Scrum Simulation Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 650);

        showTeamManagement();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void showTeamManagement() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new TeamManagementPanel(frame, MainApp::showScrumSimulation));
        frame.revalidate();
        frame.repaint();
    }

    public static void showScrumSimulation(String teamName) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new ScrumSimulationPanel(frame, teamName));
        frame.revalidate();
        frame.repaint();
    }
}
