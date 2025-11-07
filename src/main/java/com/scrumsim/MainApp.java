package com.scrumsim;

import com.scrumsim.navigation.FrameNavigator;
import com.scrumsim.navigation.Navigator;
import com.scrumsim.store.DataStore;
import com.scrumsim.store.InMemoryDataStore;
import com.scrumsim.store.Session;
import com.scrumsim.store.SessionManager;
import com.scrumsim.store.SimpleSessionManager;

import javax.swing.*;

//Main entry point for the Scrum Simulation Tool.
public class MainApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainApp::createAndShowUI);
    }

    private static void createAndShowUI() {
        JFrame frame = new JFrame("Scrum Simulation Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 650);
        frame.setLocationRelativeTo(null);

        DataStore<String, Session> sessionStore = new InMemoryDataStore<>();

        // This is gonna create the session manager with the data store
        SessionManager sessionManager = new SimpleSessionManager(sessionStore);

        // And this will create a default session for the application
        Session defaultSession = sessionManager.createSession("default-user", 30);
        System.out.println("Application started with session: " + defaultSession.getSessionId());

        Navigator navigator = new FrameNavigator(frame, sessionManager, defaultSession.getSessionId());

        navigator.showTeamManagement();

        frame.setVisible(true);
    }
}