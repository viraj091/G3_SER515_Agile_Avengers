package com.scrumsim.model;

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
