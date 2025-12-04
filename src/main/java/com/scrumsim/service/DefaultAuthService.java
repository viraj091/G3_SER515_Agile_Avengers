package com.scrumsim.service;

import com.scrumsim.model.User;
import com.scrumsim.model.UserRole;

import java.util.HashMap;
import java.util.Map;

public class DefaultAuthService implements AuthService {

    private final Map<String, String> passwordDatabase;

    private final Map<String, UserRole> roleDatabase;

    private final Map<String, String> nameDatabase;

    public DefaultAuthService() {

        passwordDatabase = new HashMap<>();
        passwordDatabase.put("sm", "sm123");
        passwordDatabase.put("po", "po123");
        passwordDatabase.put("dev", "dev123");
        passwordDatabase.put("qa", "qa123");
        passwordDatabase.put("viraj rathor", "viraj123");
        passwordDatabase.put("gunjan purohit", "gunjan123");
        passwordDatabase.put("sairaj dalvi", "sairaj123");
        passwordDatabase.put("pranav irlapale", "pranav123");
        passwordDatabase.put("shreyas revankar", "shreyas123");


        roleDatabase = new HashMap<>();
        roleDatabase.put("sm", UserRole.SCRUM_MASTER);
        roleDatabase.put("po", UserRole.PRODUCT_OWNER);
        roleDatabase.put("dev", UserRole.DEVELOPER);
        roleDatabase.put("qa", UserRole.DEVELOPER);
        roleDatabase.put("viraj rathor", UserRole.DEVELOPER);
        roleDatabase.put("gunjan purohit", UserRole.DEVELOPER);
        roleDatabase.put("sairaj dalvi", UserRole.DEVELOPER);
        roleDatabase.put("pranav irlapale", UserRole.DEVELOPER);
        roleDatabase.put("shreyas revankar", UserRole.DEVELOPER);

        nameDatabase = new HashMap<>();
        nameDatabase.put("sm", "sm");
        nameDatabase.put("po", "po");
        nameDatabase.put("dev", "dev");
        nameDatabase.put("qa", "qa");
        nameDatabase.put("viraj rathor", "Viraj Rathor");
        nameDatabase.put("gunjan purohit", "Gunjan Purohit");
        nameDatabase.put("sairaj dalvi", "Sairaj Dalvi");
        nameDatabase.put("pranav irlapale", "Pranav Irlapale");
        nameDatabase.put("shreyas revankar", "Shreyas Revankar");
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
        String properName = nameDatabase.get(normalizedUsername);

        return new User(properName, role);
    }

   
    public User login(UserRole role, String username, String password) {
 
        User authenticatedUser = authenticate(username, password);

        return authenticatedUser;
    }
}