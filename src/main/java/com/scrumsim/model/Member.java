package com.scrumsim.model;

/**
 * Represents a team member in the Scrum team.
 * Follows SRP by only handling member-related data.
 */
public class Member {
    private final String initials;
    private final String name;
    private final String role;
    private final boolean online;

    public Member(String initials, String name, String role, boolean online) {
        this.initials = initials;
        this.name = name;
        this.role = role;
        this.online = online;
    }

    public String getInitials() {
        return initials;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public boolean isOnline() {
        return online;
    }
}
