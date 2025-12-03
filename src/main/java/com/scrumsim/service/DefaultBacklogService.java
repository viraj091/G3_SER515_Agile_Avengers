package com.scrumsim.service;

import com.scrumsim.model.Story;
import com.scrumsim.model.StoryStatus;
import com.scrumsim.repository.StoryRepository;
import java.util.ArrayList;
import java.util.List;

public class DefaultBacklogService implements BacklogService {

    private static final int MAX_STORY_POINTS = 100;
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
            System.err.println("Validation failed: Story title is invalid");
            return null;
        }

        if (!isValidDescription(description)) {
            System.err.println("Validation failed: Story description is invalid");
            return null;
        }

        if (!isValidPoints(points)) {
            System.err.println("Validation failed: Story points must be non-negative");
            return null;
        }

        if (storyRepository.existsByTitle(title)) {
            System.err.println("Validation failed: Story with title '" + title + "' already exists");
            return null;
        }

        Story newStory = new Story(title, description, StoryStatus.TO_DO, points, "");
        storyRepository.save(newStory);

        System.out.println("Successfully created new story: " + title);
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
        if (points < 0 || points > MAX_STORY_POINTS) {
            return false;
        }
        return true;
    }

    @Override
    public void updatePriority(String storyId, int newPriority) {
        if (!isValidStoryId(storyId)) {
            System.err.println("Validation failed: Story ID is invalid");
            return;
        }

        if (!storyExists(storyId)) {
            System.err.println("Validation failed: Story with ID '" + storyId + "' does not exist");
            return;
        }

        if (!isValidPriority(newPriority)) {
            System.err.println("Validation failed: Priority must be between 1 and 5");
            return;
        }

        storyRepository.updatePriority(storyId, newPriority);
        System.out.println("Successfully updated priority for story: " + storyId);
    }

    @Override
    public List<Story> getStoriesSortedByPriority() {
        return storyRepository.findAllSortedByPriority();
    }

    private boolean isValidStoryId(String storyId) {
        if (storyId == null) {
            return false;
        }
        if (storyId.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean storyExists(String storyId) {
        Story story = storyRepository.findById(storyId);
        if (story == null) {
            return false;
        }
        return true;
    }

    private boolean isValidPriority(int priority) {
        if (priority < 1 || priority > 5) {
            return false;
        }
        return true;
    }

    @Override
    public void increasePriority(String storyId) {
        Story story = storyRepository.findById(storyId);
        int currentPriority = story.getPriority();
        int newPriority = currentPriority + 1;

        if (newPriority <= 5) {
            storyRepository.updatePriority(storyId, newPriority);
        }
    }

    @Override
    public void decreasePriority(String storyId) {
        Story story = storyRepository.findById(storyId);
        int currentPriority = story.getPriority();
        int newPriority = currentPriority - 1;

        if (newPriority >= 1) {
            storyRepository.updatePriority(storyId, newPriority);
        }
    }
}