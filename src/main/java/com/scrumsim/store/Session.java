package com.scrumsim.store;

import com.scrumsim.model.User;
import com.scrumsim.model.UserRole;

public class Session {

    private User currentUser;
    private UserRole role;

    public Session() {
    }

    public Session(User currentUser, UserRole role) {
        this.currentUser = currentUser;
        this.role = role;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}