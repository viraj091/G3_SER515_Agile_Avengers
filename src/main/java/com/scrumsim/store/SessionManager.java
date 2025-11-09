package com.scrumsim.store;

import java.util.Optional;

public interface SessionManager {

    Session createSession(String userId, int timeoutMinutes);

    Optional<Session> getSession(String sessionId);
    
    boolean isValid(String sessionId);

    void invalidate(String sessionId);

    int cleanupExpired();

    int getSessionCount();
}
