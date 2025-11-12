package com.scrumsim.service;

import com.scrumsim.model.Credentials;
import com.scrumsim.model.User;
import com.scrumsim.model.UserRole;

import java.util.HashMap;
import java.util.Map;

public class DefaultAuthService {

    
    private final Map<String, User> userDatabase = new HashMap<>();

    
    public DefaultAuthService() {
       
        Credentials scrumCreds = new Credentials("sm", "sm123");
        User scrumMaster = new User("Scrum Master", scrumCreds, UserRole.SCRUM_MASTER);
        userDatabase.put("sm", scrumMaster);

       
        Credentials devCreds = new Credentials("dev", "dev123");
        User developer = new User("Developer", devCreds, UserRole.DEVELOPER);
        userDatabase.put("dev", developer);

        
        Credentials poCreds = new Credentials("po", "po123");
        User productOwner = new User("Product Owner", poCreds, UserRole.PRODUCT_OWNER);
        userDatabase.put("po", productOwner);
    }

    public User login(UserRole role, String username, String password) {
        if (role == null || username == null || password == null) {
            return null; // invalid inputs
        }

        
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
