package com.scrumsim.service;

import com.scrumsim.model.User;
import com.scrumsim.model.UserRole;

public interface AuthService {

    User login(UserRole role, String username, String password);
}
