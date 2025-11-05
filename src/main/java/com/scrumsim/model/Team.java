package com.scrumsim.model;

/**
 * Represents a Scrum team.
 * Follows SRP by only managing team-related data.
 */
public class Team {
    private final String name;
    private final String role;

    public Team(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
