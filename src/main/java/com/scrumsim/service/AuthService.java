package com.scrumsim.service;

import com.scrumsim.model.User;
import com.scrumsim.model.UserRole;

public class AuthService {

    // Simple delegation to an in-memory auth provider
    private final DefaultAuthService defaultAuthService;

    public AuthService() {
        this.defaultAuthService = new DefaultAuthService();
    }

    public User login(UserRole role, String username, String password) {
        return defaultAuthService.login(role, username, password);
    }

    public void welcomeUser(User user) {
        if (user == null) return;
        System.out.println("Welcome, " + user.getName() + "! Your role is " + user.getRole());
    }
}
