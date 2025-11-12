package com.scrumsim.model;

import java.util.Objects;

/**
 * User profile model.
 *
 * Follows SOLID:
 * - Single Responsibility: stores profile info and role; authentication details are in Credentials.
 * - Dependency/Composition: User has Credentials (composition), not authentication logic directly.
 *
 * This class still satisfies your Task #53 requirement: each user has a name, username, password (via Credentials), and role.
 */
public class User {
    private final String name;
    private final Credentials credentials; // contains username + password
    private final UserRole role;

    /**
     * Preferred constructor for creating a User with credentials.
     *
     * @param name        display name
     * @param credentials authentication data (username + password)
     * @param role        user's role
     */
    public User(String name, Credentials credentials, UserRole role) {
        this.name = name;
        this.credentials = credentials;
        this.role = role;
    }

    /**
     * Convenience constructor for backward compatibility where only name and role were used.
     * Creates a user without credentials (credentials will be null).
     *
     * @param name display name
     * @param role user's role
     */
    public User(String name, UserRole role) {
        this(name, null, role);
    }

    public String getName() {
        return name;
    }

    /**
     * Returns the credentials object (may be null if user was created without credentials).
     */
    public Credentials getCredentials() {
        return credentials;
    }

    /**
     * Convenience getter for username (returns null if credentials absent).
     */
    public String getUsername() {
        return credentials == null ? null : credentials.getUsername();
    }

    /**
     * Convenience method to check password; delegates to Credentials if present.
     */
    public boolean checkPassword(String candidate) {
        if (credentials == null) return false;
        return credentials.checkPassword(candidate);
    }

    public UserRole getRole() {
        return role;
    }

    // Small helpers for role checks (easy for UI/service code)
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
        // If credentials exist, compare by username (unique identity). Otherwise compare name+role.
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
