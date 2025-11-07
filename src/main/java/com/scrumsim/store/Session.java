package com.scrumsim.store;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Session {

    private final String sessionId;
    private final String userId;
    private final LocalDateTime createdAt;
    private final LocalDateTime expiresAt;

    public Session(String sessionId, String userId, LocalDateTime createdAt, LocalDateTime expiresAt) {
        if (sessionId == null || sessionId.trim().isEmpty()) {
            throw new IllegalArgumentException("Session ID cannot be null or empty");
        }
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        if (createdAt == null) {
            throw new IllegalArgumentException("Created timestamp cannot be null");
        }
        if (expiresAt == null) {
            throw new IllegalArgumentException("Expiration timestamp cannot be null");
        }
        if (expiresAt.isBefore(createdAt)) {
            throw new IllegalArgumentException("Expiration time cannot be before creation time");
        }

        this.sessionId = sessionId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public static Session create(String userId, int timeoutMinutes) {
        if (timeoutMinutes <= 0) {
            throw new IllegalArgumentException("Timeout must be positive");
        }

        String sessionId = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plusMinutes(timeoutMinutes);

        return new Session(sessionId, userId, now, expiresAt);
    }


    public boolean isValid() {
        return LocalDateTime.now().isBefore(expiresAt);
    }

    public String getSessionId() {
        return sessionId;
    }

    
    public String getUserId() {
        return userId;
    }

    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    
    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(sessionId, session.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId);
    }

    @Override
    public String toString() {
        return "Session{" +
                "sessionId='" + sessionId + '\'' +
                ", userId='" + userId + '\'' +
                ", createdAt=" + createdAt +
                ", expiresAt=" + expiresAt +
                ", isValid=" + isValid() +
                '}';
    }
}
