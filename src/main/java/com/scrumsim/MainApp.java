package com.scrumsim;

import com.scrumsim.model.User;
import com.scrumsim.navigation.FrameNavigator;
import com.scrumsim.navigation.Navigator;
import com.scrumsim.repository.InMemoryTeamRepository;
import com.scrumsim.repository.TeamRepository;
import com.scrumsim.service.DefaultAuthService;
import com.scrumsim.service.SimpleTeamService;
import com.scrumsim.service.TeamService;
import com.scrumsim.store.DataStore;
import com.scrumsim.store.InMemoryDataStore;
import com.scrumsim.store.Session;
import com.scrumsim.store.SessionManager;
import com.scrumsim.store.SimpleSessionManager;
import com.scrumsim.store.UserSession;
import com.scrumsim.ui.LoginListener;
import com.scrumsim.ui.LoginPanel;

import javax.swing.*;


public class MainApp implements LoginListener {

    private static JFrame frame;
    private static Navigator navigator;
    private static TeamService teamService;
    private static SessionManager sessionManager;
    private static String currentSessionId;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowUI();
            }
        });
    }

    private static void createAndShowUI() {
        frame = new JFrame("Scrum Simulation Tool");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(1000, 650);

        frame.setLocationRelativeTo(null);

        DataStore<String, Session> sessionStore = new InMemoryDataStore<>();

        sessionManager = new SimpleSessionManager(sessionStore);

        Session defaultSession = sessionManager.createSession("default-user", 30);
        currentSessionId = defaultSession.getSessionId();

        System.out.println("Application started with session: " + currentSessionId);

        showLoginPanel();

        frame.setVisible(true);
    }

    private static void showLoginPanel() {

        DefaultAuthService authService = new DefaultAuthService();

        MainApp mainAppInstance = new MainApp();

        LoginPanel loginPanel = new LoginPanel(authService, mainAppInstance);

        frame.getContentPane().removeAll();

        frame.getContentPane().add(loginPanel);


        frame.revalidate();
        frame.repaint();
    }

    public static void logout() {
        UserSession.getInstance().clearSession();
        showLoginPanel();
    }

   
    @Override
    public void onLoginSuccess(User user) {
     
        System.out.println("Login successful!");
        System.out.println("  User: " + user.getName());
        System.out.println("  Role: " + user.getRole().getDisplayName());
        TeamRepository teamRepository = new InMemoryTeamRepository(user);

     
        teamService = new SimpleTeamService(teamRepository);

    
        navigator = new FrameNavigator(
            frame,            
            user,              
            teamService,      
            sessionManager,    
            currentSessionId   
        );

        navigator.showTeamManagement();

        System.out.println("Navigated to Team Management Dashboard");
    }
}