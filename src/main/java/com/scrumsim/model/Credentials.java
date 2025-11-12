package com.scrumsim.model;

import java.util.Objects;

/**
 * Credentials stores authentication-related information.
 * Single Responsibility: this class only deals with username/password logic.
 *
 * NOTE (beginner): In production never store plain text passwords. Use a password hash.
 */
public class Credentials {
    private final String username;
    private final String password; // demo only: plain text

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    /**
     * Returns the stored password (demo only). In real systems this would be a hash.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Simple check used by AuthService or elsewhere to verify a provided password.
     * Keeps password-comparison logic inside Credentials (SRP).
     */
    public boolean checkPassword(String candidate) {
        if (candidate == null) return false;
        return password.equals(candidate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Credentials)) return false;
        Credentials that = (Credentials) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "Credentials{username='" + username + "'}";
    }
}
