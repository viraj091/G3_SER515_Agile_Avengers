package com.scrumsim.service;

import com.scrumsim.model.Story;
import com.scrumsim.repository.StoryRepository;

public class DefaultBusinessValueService implements BusinessValueService {

    private StoryRepository storyRepository;

    public DefaultBusinessValueService(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    @Override
    public void applyBusinessValue(Story story, int value) {
        if (value >= 0) {
            story.setBusinessValue(value);
            storyRepository.save(story);
        }
    }
}
