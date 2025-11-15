package com.scrumsim.service;

import com.scrumsim.model.User;

public interface AuthService {

    User authenticate(String username, String password);
}