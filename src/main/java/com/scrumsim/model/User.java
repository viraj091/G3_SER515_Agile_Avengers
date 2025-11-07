package com.scrumsim.model;

public class User {
    private final String name;
    private final UserRole role;

    public User(String name, UserRole role) {
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public UserRole getRole() {
        return role;
    }

    public boolean isScrumMaster() {
        return role == UserRole.SCRUM_MASTER;
    }
}