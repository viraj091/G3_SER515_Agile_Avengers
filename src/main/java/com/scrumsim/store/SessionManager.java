package com.scrumsim.store;

import com.scrumsim.model.User;
import com.scrumsim.model.UserRole;

public interface SessionManager {

    void startSession(User user);

    User getCurrentUser();

    UserRole getCurrentUserRole();

    void clearSession();

    boolean isLoggedIn();
}