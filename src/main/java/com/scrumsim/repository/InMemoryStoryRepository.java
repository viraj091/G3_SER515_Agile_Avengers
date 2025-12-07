package com.scrumsim.repository;

import com.scrumsim.model.Story;
import com.scrumsim.model.StoryStatus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InMemoryStoryRepository implements StoryRepository {

    private final List<Story> stories;
    private int idCounter;

    public InMemoryStoryRepository() {
        this.stories = new ArrayList<>();
        this.idCounter = 1;
        initializeDefaultStories();
        printRepositoryDebugInfo();
    }

    private void printRepositoryDebugInfo() {
        System.out.println("\n========================================");
        System.out.println("   REPOSITORY INITIALIZED");
        System.out.println("========================================");
        System.out.println("Total stories loaded: " + stories.size());
        System.out.println("\nAll stories in repository:");
        for (Story story : stories) {
            System.out.println("  " + story.getId() + " -> " + story.getTitle());
        }
        System.out.println("========================================\n");
    }

    private void initializeDefaultStories() {
        Story story1 = new Story("Implement user authentication system", StoryStatus.IN_PROGRESS, 8, "Sairaj Dalvi, Pranav Irlapale");
        story1.setId(generateId());
        stories.add(story1);

        Story story2 = new Story("Design dashboard UI components", StoryStatus.TO_DO, 5, "Gunjan Purohit");
        story2.setId(generateId());
        stories.add(story2);

        Story story3 = new Story("Setup CI/CD pipeline", StoryStatus.DONE, 13, "Shreyas Revankar, Viraj Rathor");
        story3.setId(generateId());
        stories.add(story3);

        Story story4 = new Story("Create API documentation", StoryStatus.TO_DO, 3, "Viraj Rathor");
        story4.setId(generateId());
        stories.add(story4);

        Story story5 = new Story("Refactor database layer", StoryStatus.TO_DO, 8, "");
        story5.setId(generateId());
        stories.add(story5);

        Story story6 = new Story("Add unit tests for services", StoryStatus.TO_DO, 5, "");
        story6.setId(generateId());
        stories.add(story6);
    }

    private String generateId() {
        return "STORY-" + idCounter++;
    }

    @Override
    public List<Story> findAll() {
        return new ArrayList<>(stories);
    }

    @Override
    public Story findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }

        for (Story story : stories) {
            if (id.equals(story.getId())) {
                return story;
            }
        }
        return null;
    }

    @Override
    public void save(Story story) {
        if (story == null) {
            throw new IllegalArgumentException("Story cannot be null");
        }

        if (story.getId() == null || story.getId().trim().isEmpty()) {
            if (existsByTitle(story.getTitle())) {
                throw new IllegalArgumentException("Story with this title already exists");
            }
            story.setId(generateId());
            stories.add(story);
        } else {
            boolean found = false;
            for (int i = 0; i < stories.size(); i++) {
                if (stories.get(i).getId().equals(story.getId())) {
                    stories.set(i, story);
                    found = true;
                    break;
                }
            }
            if (!found) {
                stories.add(story);
            }
        }
    }

    @Override
    public boolean existsByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return false;
        }

        String lowerTitle = title.toLowerCase().trim();
        for (Story story : stories) {
            if (story.getTitle().toLowerCase().trim().equals(lowerTitle)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void updatePriority(String storyId, int newPriority) {
        if (storyId == null || storyId.trim().isEmpty()) {
            throw new IllegalArgumentException("Story ID cannot be null or empty");
        }

        boolean storyFound = false;

        for (Story story : stories) {
            if (storyId.equals(story.getId())) {
                story.setPriority(newPriority);
                storyFound = true;
                break;
            }
        }

        if (!storyFound) {
            throw new IllegalArgumentException("Story with ID '" + storyId + "' not found");
        }
    }

    @Override
    public List<Story> findAllSortedByPriority() {
        List<Story> sortedStories = new ArrayList<>(stories);
        Collections.sort(sortedStories, Comparator.comparingInt(Story::getPriority));
        return sortedStories;
    }

    @Override
    public List<Story> findAllSortedByOrderIndex() {
        List<Story> sortedStories = new ArrayList<>(stories);
        Collections.sort(sortedStories, Comparator.comparingInt(Story::getOrderIndex));
        return sortedStories;
    }

    @Override
    public List<Story> findAllSortedByRank() {
        List<Story> sortedStories = new ArrayList<>(stories);
        Collections.sort(sortedStories, Comparator.comparingInt(Story::getRank));
        return sortedStories;
    }

    @Override
    public List<Story> findAllSortedByBusinessValue() {
        List<Story> sortedStories = new ArrayList<>(stories);
        Collections.sort(sortedStories, Comparator.comparingInt(Story::getBusinessValue));
        return sortedStories;
    }

    @Override
    public List<Story> findAllSortedByUrgency() {
        List<Story> sortedStories = new ArrayList<>(stories);
        Collections.sort(sortedStories, Comparator.comparingInt(Story::getUrgency));
        return sortedStories;
    }

    @Override
    public List<Story> findAllSortedByEffort() {
        List<Story> sortedStories = new ArrayList<>(stories);
        Collections.sort(sortedStories, Comparator.comparingInt(Story::getEffort));
        return sortedStories;
    }

    @Override
    public List<Story> findAllSortedByPoints() {
        List<Story> sortedStories = new ArrayList<>(stories);
        Collections.sort(sortedStories, Comparator.comparingInt(Story::getPoints));
        return sortedStories;
    }

    @Override
    public List<Story> findAllSortedByPriorityDescending() {
        List<Story> sortedStories = new ArrayList<>(stories);
        Collections.sort(sortedStories, Comparator.comparingInt(Story::getPriority).reversed());
        return sortedStories;
    }

    @Override
    public List<Story> findAllSortedByBusinessValueDescending() {
        List<Story> sortedStories = new ArrayList<>(stories);
        Collections.sort(sortedStories, Comparator.comparingInt(Story::getBusinessValue).reversed());
        return sortedStories;
    }

    @Override
    public List<Story> findAllByStatusSortedByPriority(StoryStatus status) {
        List<Story> filteredStories = new ArrayList<>();
        for (Story story : stories) {
            if (story.getStatus() == status) {
                filteredStories.add(story);
            }
        }
        Collections.sort(filteredStories, Comparator.comparingInt(Story::getPriority));
        return filteredStories;
    }

    @Override
    public void updateStatus(String storyId, StoryStatus newStatus) {
        if (storyId == null || storyId.trim().isEmpty()) {
            throw new IllegalArgumentException("Story ID cannot be null or empty");
        }

        if (newStatus == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }

        boolean found = false;
        for (Story story : stories) {
            if (storyId.equals(story.getId())) {
                story.setStatus(newStatus);
                found = true;
                break;
            }
        }

        if (!found) {
            throw new IllegalArgumentException("Story with ID '" + storyId + "' not found");
        }
    }

    @Override
    public List<Story> findByAssignee(String name) {
        List<Story> assignedStories = new ArrayList<>();
        if (name == null || name.trim().isEmpty()) {
            return assignedStories;
        }
        String searchName = name.trim();
        String firstName = searchName.split(" ")[0];
        for (Story story : stories) {
            String assignees = story.getAssignees();
            if (assignees != null && !assignees.trim().isEmpty()) {
                if (assignees.contains(searchName) || assignees.contains(firstName)) {
                    assignedStories.add(story);
                }
            }
        }
        return assignedStories;
    }
}