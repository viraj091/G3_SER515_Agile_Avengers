package com.scrumsim.store;

import java.util.Optional;


public class SimpleSessionManager implements SessionManager {

    private final DataStore<String, Session> store;

    public SimpleSessionManager(DataStore<String, Session> store) {
        if (store == null) {
            throw new IllegalArgumentException("DataStore cannot be null");
        }
        this.store = store;
    }

    @Override
    public Session createSession(String userId, int timeoutMinutes) {
        Session session = Session.create(userId, timeoutMinutes);

        store.put(session.getSessionId(), session);

        return session;
    }

    @Override
    public Optional<Session> getSession(String sessionId) {
        if (sessionId == null || sessionId.trim().isEmpty()) {
            return Optional.empty();
        }

        return store.get(sessionId);
    }

    @Override
    public boolean isValid(String sessionId) {

        return getSession(sessionId)
                .map(Session::isValid)
                .orElse(false);
    }

    @Override
    public void invalidate(String sessionId) {
        if (sessionId == null || sessionId.trim().isEmpty()) {
            return;
        }

        store.remove(sessionId);
    }

    @Override
    public int cleanupExpired() {
        int removedCount = 0;

        for (Session session : store.getAllValues()) {
            
            if (!session.isValid()) {
                store.remove(session.getSessionId());
                removedCount++;
            }
        }

        return removedCount;
    }

    @Override
    public int getSessionCount() {
        return store.size();
    }
}
