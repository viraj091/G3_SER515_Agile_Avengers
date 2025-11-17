package com.scrumsim.service;

import com.scrumsim.model.Story;
import com.scrumsim.repository.StoryRepository;
import java.util.ArrayList;
import java.util.List;

public class DefaultBacklogService implements BacklogService {

    private final StoryRepository storyRepository;

    public DefaultBacklogService(StoryRepository storyRepository) {
        if (storyRepository == null) {
            throw new IllegalArgumentException("StoryRepository cannot be null");
        }
        this.storyRepository = storyRepository;
    }

    @Override
    public List<Story> getBacklogStories(List<Story> sprintStories) {
        List<Story> allStories = storyRepository.findAll();
        List<Story> backlogStories = new ArrayList<>();

        for (Story story : allStories) {
            boolean isInSprint = false;

            for (Story sprintStory : sprintStories) {
                if (story.getTitle().equals(sprintStory.getTitle())) {
                    isInSprint = true;
                    break;
                }
            }

            if (!isInSprint) {
                backlogStories.add(story);
            }
        }

        return backlogStories;
    }
}
