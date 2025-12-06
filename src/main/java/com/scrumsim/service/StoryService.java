package com.scrumsim.service;

import com.scrumsim.model.StoryStatus;
import com.scrumsim.model.User;

public interface StoryService {

    boolean updateStoryStatus(String storyId, StoryStatus newStatus, User user);
}
