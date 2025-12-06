package com.scrumsim.service;

import com.scrumsim.model.Story;
import com.scrumsim.model.User;

public interface StoryUpdateGuard {

    boolean canUpdate(User user, Story story);
}
