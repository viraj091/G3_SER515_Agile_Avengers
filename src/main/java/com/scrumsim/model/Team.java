package com.scrumsim.model;

public class Team {
    private final String name;
    private final User scrumMaster;
    private final String role;

    public Team(String name, User scrumMaster) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Team name cannot be null or empty");
        }
        if (scrumMaster == null) {
            throw new IllegalArgumentException("Scrum Master cannot be null");
        }
        this.name = name;
        this.scrumMaster = scrumMaster;
        this.role = scrumMaster.getRole().getDisplayName();
    }

    @Deprecated
    public Team(String name, String role) {
        this.name = name;
        this.role = role;
        this.scrumMaster = null;
    }

    public String getName() {
        return name;
    }

    public User getScrumMaster() {
        return scrumMaster;
    }

    public String getRole() {
        return role;
    }
}