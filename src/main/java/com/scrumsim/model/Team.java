package com.scrumsim.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Team {
    private final String name;
    private final User scrumMaster;
    private final String role;
    private final List<User> members;
    // Stores member name -> role name mappings
    private final Map<String, String> memberRoles;

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
        this.members = new ArrayList<>();
        this.memberRoles = new HashMap<>();
    }

    @Deprecated
    public Team(String name, String role) {
        this.name = name;
        this.role = role;
        this.scrumMaster = null;
        this.members = new ArrayList<>();
        this.memberRoles = new HashMap<>();
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

    public void addMember(User user) {
        members.add(user);
    }

    public boolean isMember(User user){
        return members.contains(user);
    }

    // Sets the role for a specific member
    public void setMemberRole(String memberName, String roleName) {
        memberRoles.put(memberName, roleName);
    }

    // Gets the role for a specific member
    public String getMemberRole(String memberName) {
        return memberRoles.get(memberName);
    }

    // Gets all member-role mappings
    public Map<String, String> getAllMemberRoles() {
        return new HashMap<>(memberRoles);
    }
}