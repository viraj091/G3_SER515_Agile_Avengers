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
        passwordDatabase.put("viraj_rathor", "viraj123");
        passwordDatabase.put("gunjan_purohit", "gunjan123");
        passwordDatabase.put("sairaj_dalvi", "sairaj123");
        passwordDatabase.put("pranav_irlapale", "pranav123");
        passwordDatabase.put("shreyas_revankar", "shreyas123");


        roleDatabase = new HashMap<>();
        roleDatabase.put("sm", UserRole.SCRUM_MASTER);
        roleDatabase.put("po", UserRole.PRODUCT_OWNER);
        roleDatabase.put("dev", UserRole.DEVELOPER);
        roleDatabase.put("qa", UserRole.DEVELOPER);
        roleDatabase.put("viraj_rathor", UserRole.DEVELOPER);
        roleDatabase.put("gunjan_purohit", UserRole.DEVELOPER);
        roleDatabase.put("sairaj_dalvi", UserRole.DEVELOPER);
        roleDatabase.put("pranav_irlapale", UserRole.DEVELOPER);
        roleDatabase.put("shreyas_revankar", UserRole.DEVELOPER);

        nameDatabase = new HashMap<>();
        nameDatabase.put("sm", "sm");
        nameDatabase.put("po", "po");
        nameDatabase.put("dev", "dev");
        nameDatabase.put("qa", "qa");
        nameDatabase.put("viraj_rathor", "Viraj Rathor");
        nameDatabase.put("gunjan_purohit", "Gunjan Purohit");
        nameDatabase.put("sairaj_dalvi", "Sairaj Dalvi");
        nameDatabase.put("pranav_irlapale", "Pranav Irlapale");
        nameDatabase.put("shreyas_revankar", "Shreyas Revankar");
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

    @Override
    public User authenticate(String username, String password, UserRole selectedRole) {
        if (username == null || password == null || selectedRole == null) {
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

        String properName = nameDatabase.get(normalizedUsername);

        return new User(properName, selectedRole);
    }


    public User login(UserRole role, String username, String password) {

        User authenticatedUser = authenticate(username, password);

        return authenticatedUser;
    }
}