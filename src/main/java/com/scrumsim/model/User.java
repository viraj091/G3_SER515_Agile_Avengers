package com.scrumsim.model;

import java.util.Objects;

public class User {
    private final String name;
    private final Credentials credentials; // contains username + password
    private final UserRole role;

    public User(String name, Credentials credentials, UserRole role) {
        this.name = name;
        this.credentials = credentials;
        this.role = role;
    }

    public User(String name, UserRole role) {
        this(name, null, role);
    }

    public String getName() {
        return name;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public String getUsername() {
        return credentials == null ? null : credentials.getUsername();
    }

    public boolean checkPassword(String candidate) {
        if (credentials == null) return false;
        return credentials.checkPassword(candidate);
    }

    public UserRole getRole() {
        return role;
    }

    public boolean isScrumMaster() {
        return role == UserRole.SCRUM_MASTER;
    }

    public boolean isProductOwner() {
        return role == UserRole.PRODUCT_OWNER;
    }

    public boolean isDeveloper() {
        return role == UserRole.DEVELOPER;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User that = (User) o;
        if (this.credentials != null && that.credentials != null) {
            return Objects.equals(this.credentials.getUsername(), that.credentials.getUsername());
        }
        return Objects.equals(name, that.name) && role == that.role;
    }

    @Override
    public int hashCode() {
        if (credentials != null) {
            return Objects.hash(credentials.getUsername());
        }
        return Objects.hash(name, role);
    }

    @Override
    public String toString() {
        return "User{name='" + name + "', username='" + getUsername() + "', role=" + role + "}";
    }
}
