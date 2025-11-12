package com.scrumsim.service;

import com.scrumsim.model.Credentials;
import com.scrumsim.model.User;
import com.scrumsim.model.UserRole;

import java.util.HashMap;
import java.util.Map;

/**
 * DefaultAuthService: provides simple in-memory authentication.
 *
 * ✅ Follows SOLID:
 * - SRP: Only manages user data and login logic.
 * - OCP: Can be extended to use a DB later without changing AuthService.
 * - DIP: Works with abstractions (User and Credentials).
 */
public class DefaultAuthService {

    // In-memory "database" of users
    private final Map<String, User> userDatabase = new HashMap<>();

    // Constructor: create some demo users
    public DefaultAuthService() {
        // Scrum Master
        Credentials scrumCreds = new Credentials("sm", "sm123");
        User scrumMaster = new User("Scrum Master", scrumCreds, UserRole.SCRUM_MASTER);
        userDatabase.put("sm", scrumMaster);

        // Developer
        Credentials devCreds = new Credentials("dev", "dev123");
        User developer = new User("Developer", devCreds, UserRole.DEVELOPER);
        userDatabase.put("dev", developer);

        // Product Owner
        Credentials poCreds = new Credentials("po", "po123");
        User productOwner = new User("Product Owner", poCreds, UserRole.PRODUCT_OWNER);
        userDatabase.put("po", productOwner);
    }

    /**
     * Logs in a user by verifying username, password, and role.
     *
     * @param role     expected user role
     * @param username entered username
     * @param password entered password
     * @return the logged-in User if successful; null otherwise
     */
    public User login(UserRole role, String username, String password) {
        if (role == null || username == null || password == null) {
            return null; // invalid inputs
        }

        // Fetch user from "database"
        User user = userDatabase.get(username.trim().toLowerCase());

        if (user == null) {
            System.out.println("User not found!");
            return null;
        }

        // Validate role and password
        if (user.getRole() != role) {
            System.out.println("Wrong role for user!");
            return null;
        }

        if (!user.checkPassword(password)) {
            System.out.println("Wrong password!");
            return null;
        }

        System.out.println("Login successful for " + user.getName() + " (" + role + ")");
        return user;
    }
}
