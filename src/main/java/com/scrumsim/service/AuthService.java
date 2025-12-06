package com.scrumsim.service;

import com.scrumsim.model.User;
import com.scrumsim.model.UserRole;

public interface AuthService {

    User authenticate(String username, String password);

    User authenticate(String username, String password, UserRole selectedRole);
}