package com.scrumsim.service;

import com.scrumsim.model.User;
import com.scrumsim.model.UserRole;

import java.util.HashMap;
import java.util.Map;

public class DefaultAuthService implements AuthService {

    private final Map<String, UserCredentials> userDatabase;

    public DefaultAuthService() {
        // Create an empty HashMap
        this.userDatabase = new HashMap<>();

        // Fill it with our 3 test users
        createHardcodedUsers();
    }

    private void createHardcodedUsers() {
        // Create Product Owner
        UserCredentials productOwner = new UserCredentials(
            "po",           
            "po123",     
            UserRole.PRODUCT_OWNER,  
            "Product Owner" 
        );
        userDatabase.put("po", productOwner);

        // Create Scrum Master
        UserCredentials scrumMaster = new UserCredentials(
            "sm",          
            "sm123",       
            UserRole.SCRUM_MASTER,   
            "Scrum Master" 
        );
        userDatabase.put("sm", scrumMaster);

        // Create Developer
        UserCredentials developer = new UserCredentials(
            "dev",          
            "dev123",       
            UserRole.DEVELOPER,     
            "Developer"     
        );
        userDatabase.put("dev", developer);
    }

    @Override
    public User login(UserRole role, String username, String password) {
        if (!areInputsValid(role, username, password)) {
            return null;  // Login failed
        }

       
        String cleanUsername = username.toLowerCase().trim();

        UserCredentials credentials = userDatabase.get(cleanUsername);

        if (credentials == null) {
            return null;  // User not found - login failed
        }

        if (!credentials.getPassword().equals(password)) {
            return null;  // Wrong password - login failed
        }

        if (credentials.getRole() != role) {
            return null;  // Wrong role - login failed
        }

        User loggedInUser = new User(
            credentials.getDisplayName(),  // "Product Owner", "Scrum Master", etc.
            credentials.getRole()          // PRODUCT_OWNER, SCRUM_MASTER, etc.
        );

        return loggedInUser; 
    }

  
    private boolean areInputsValid(UserRole role, String username, String password) {
        if (role == null || username == null || password == null) {
            return false;
        }

        if (username.trim().isEmpty()) {
            return false;
        }

        if (password.isEmpty()) {
            return false;
        }

        return true;
    }

    
    private static class UserCredentials {
        // Store all the information about one user
        private final String password;
        private final UserRole role;
        private final String displayName;

        public UserCredentials(String username, String password, UserRole role, String displayName) {
            this.password = password;
            this.role = role;
            this.displayName = displayName;
        }

        public String getPassword() {
            return password;
        }

        public UserRole getRole() {
            return role;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
