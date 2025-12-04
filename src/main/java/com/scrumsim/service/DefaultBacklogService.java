package com.scrumsim.service;

import com.scrumsim.model.Story;
import com.scrumsim.model.StoryStatus;
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

    @Override
    public Story createStory(String title, String description, int points) {
        if (!isValidTitle(title)) {
            return null;
        }

        if (!isValidDescription(description)) {
            return null;
        }

        if (!isValidPoints(points)) {
            return null;
        }

        if (storyRepository.existsByTitle(title)) {
            return null;
        }

        Story newStory = new Story(title, description, StoryStatus.NEW, points, "");
        storyRepository.save(newStory);

        return newStory;
    }

    private boolean isValidTitle(String title) {
        if (title == null) {
            return false;
        }
        if (title.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean isValidDescription(String description) {
        if (description == null) {
            return false;
        }
        if (description.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean isValidPoints(int points) {
        if (points < 0) {
            return false;
        }
        return true;
    }
}