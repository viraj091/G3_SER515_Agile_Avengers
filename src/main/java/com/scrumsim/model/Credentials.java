package com.scrumsim.model;

import java.util.Objects;

public class Credentials {
    private final String username;
    private final String password; 

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    
    public String getPassword() {
        return password;
    }

    
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
