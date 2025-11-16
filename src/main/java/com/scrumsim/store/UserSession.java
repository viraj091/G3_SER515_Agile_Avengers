package com.scrumsim.store;

import com.scrumsim.model.User;
import com.scrumsim.model.UserRole;

public class UserSession {

    private static UserSession instance;
    private final SessionManager sessionManager;

    private UserSession() {
        this.sessionManager = new SimpleSessionManager();
    }

    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void startSession(User user) {
        sessionManager.startSession(user);
    }

    public User getCurrentUser() {
        return sessionManager.getCurrentUser();
    }

    public UserRole getCurrentUserRole() {
        return sessionManager.getCurrentUserRole();
    }

    public void clearSession() {
        sessionManager.clearSession();
    }

    public boolean isLoggedIn() {
        return sessionManager.isLoggedIn();
    }
}