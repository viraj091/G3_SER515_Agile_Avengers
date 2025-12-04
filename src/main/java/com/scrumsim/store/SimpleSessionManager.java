package com.scrumsim.store;

import com.scrumsim.model.User;
import com.scrumsim.model.UserRole;

public class SimpleSessionManager implements SessionManager {

    private Session session;

    public SimpleSessionManager() {
        this.session = null;
    }

    @Override
    public void startSession(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.session = new Session(user, user.getRole());
    }

    @Override
    public User getCurrentUser() {
        if (session == null) {
            return null;
        }
        return session.getCurrentUser();
    }

    @Override
    public UserRole getCurrentUserRole() {
        if (session == null) {
            return null;
        }
        return session.getRole();
    }

    @Override
    public void clearSession() {
        this.session = null;
    }

    @Override
    public boolean isLoggedIn() {
        return session != null && session.getCurrentUser() != null;
    }
}