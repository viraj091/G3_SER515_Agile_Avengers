package com.scrumsim.ui;

import com.scrumsim.model.User;

public interface LoginListener {

    //Called when a user successfully logs in.
    
    void onLoginSuccess(User user);
}
