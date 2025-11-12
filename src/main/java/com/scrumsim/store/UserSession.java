package com.scrumsim.store;

import com.scrumsim.model.User;
import com.scrumsim.model.UserRole;


public class UserSession {

    private static UserSession instance;

    private User currentUser;

    private UserSession() {
    }

    public static synchronized UserSession getInstance() {
        // If instance doesn't exist yet, create it
        if (instance == null) {
            instance = new UserSession();
        }
        // Return the single instance
        return instance;
    }

    
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public UserRole getCurrentRole() {
        // Check if someone is logged in first
        if (currentUser != null) {
            return currentUser.getRole();
        } else {
            return null;
        }
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public void clearSession() {
        this.currentUser = null;
    }
}
