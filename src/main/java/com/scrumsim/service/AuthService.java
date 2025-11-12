package com.scrumsim.service;

import com.scrumsim.model.User;
import com.scrumsim.model.UserRole;

/**
 * Lightweight AuthService that exposes a login method returning a User.
 *
 * - Beginner-friendly.
 * - Keeps SRP: AuthService only handles authentication concerns.
 * - Delegates to DefaultAuthService (in-memory user store).
 *
 * Note: For a larger app you'd inject DefaultAuthService (DIP). For now this simple
 * composition keeps things easy to use and straightforward to understand.
 */
public class AuthService {

    // Simple delegation to an in-memory auth provider
    private final DefaultAuthService defaultAuthService;

    public AuthService() {
        this.defaultAuthService = new DefaultAuthService();
    }

    /**
     * Attempt to log in a user with the expected role, username and password.
     *
     * @param role     expected role (SCRUM_MASTER, PRODUCT_OWNER, DEVELOPER)
     * @param username username provided by user
     * @param password password provided by user
     * @return the logged-in {@link User} if successful, or null if login fails
     */
    public User login(UserRole role, String username, String password) {
        return defaultAuthService.login(role, username, password);
    }

    /**
     * Convenience: print a welcome message after successful login.
     * @param user the logged-in user
     */
    public void welcomeUser(User user) {
        if (user == null) return;
        System.out.println("Welcome, " + user.getName() + "! Your role is " + user.getRole());
    }
}
