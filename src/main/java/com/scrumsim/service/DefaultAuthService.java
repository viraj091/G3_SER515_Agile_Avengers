package com.scrumsim.service;

import com.scrumsim.model.User;
import com.scrumsim.model.UserRole;

import java.util.HashMap;
import java.util.Map;

public class DefaultAuthService implements AuthService {

    private final Map<String, String> passwordDatabase;

    private final Map<String, UserRole> roleDatabase;

    public DefaultAuthService() {

        passwordDatabase = new HashMap<>();
        passwordDatabase.put("sm", "sm123");
        passwordDatabase.put("po", "po123");
        passwordDatabase.put("dev", "dev123");
        passwordDatabase.put("qa", "qa123");

 
        roleDatabase = new HashMap<>();
        roleDatabase.put("sm", UserRole.SCRUM_MASTER);
        roleDatabase.put("po", UserRole.PRODUCT_OWNER);
        roleDatabase.put("dev", UserRole.DEVELOPER);
        roleDatabase.put("qa", UserRole.DEVELOPER); 
    }

    @Override
    public User authenticate(String username, String password) {
        if (username == null || password == null) {
            return null;
        }

        String normalizedUsername = username.trim().toLowerCase();

        if (!passwordDatabase.containsKey(normalizedUsername)) {
            return null;  
        }

        String storedPassword = passwordDatabase.get(normalizedUsername);
        if (!storedPassword.equals(password)) {
            return null;
        }

        UserRole role = roleDatabase.get(normalizedUsername);

        return new User(normalizedUsername, role);
    }

   
    public User login(UserRole role, String username, String password) {
 
        User authenticatedUser = authenticate(username, password);

        return authenticatedUser;
    }
}