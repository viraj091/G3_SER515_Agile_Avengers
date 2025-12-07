package com.scrumsim.service;

import com.scrumsim.model.Story;
import com.scrumsim.model.StoryStatus;
import com.scrumsim.model.User;
import com.scrumsim.repository.StoryRepository;

public class DefaultStoryService implements StoryService {

    private final StoryRepository storyRepository;
    private final StoryUpdateGuard storyUpdateGuard;

    public DefaultStoryService(StoryRepository storyRepository, StoryUpdateGuard storyUpdateGuard) {
        if (storyRepository == null) {
            throw new IllegalArgumentException("StoryRepository cannot be null");
        }
        if (storyUpdateGuard == null) {
            throw new IllegalArgumentException("StoryUpdateGuard cannot be null");
        }
        this.storyRepository = storyRepository;
        this.storyUpdateGuard = storyUpdateGuard;
    }

    @Override
    public boolean updateStoryStatus(String storyId, StoryStatus newStatus, User user) {
        if (storyId == null || storyId.trim().isEmpty()) {
            return false;
        }

        if (newStatus == null) {
            return false;
        }

        Story story = storyRepository.findById(storyId);
        if (story == null) {
            return false;
        }

        if (!storyUpdateGuard.canUpdate(user, story)) {
            return false;
        }

        storyRepository.updateStatus(storyId, newStatus);
        return true;
    }
}
