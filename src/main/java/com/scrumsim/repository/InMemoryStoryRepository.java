package com.scrumsim.repository;

import com.scrumsim.model.Story;
import com.scrumsim.model.StoryStatus;
import java.util.ArrayList;
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

        Story story2 = new Story("Design dashboard UI components", StoryStatus.TODO, 5, "Gunjan Purohit");
        story2.setId(generateId());
        stories.add(story2);

        Story story3 = new Story("Setup CI/CD pipeline", StoryStatus.DONE, 13, "Shreyas Revankar, Viraj Rathor");
        story3.setId(generateId());
        stories.add(story3);

        Story story4 = new Story("Create API documentation", StoryStatus.NEW, 3, "Viraj Rathor");
        story4.setId(generateId());
        stories.add(story4);

        Story story5 = new Story("Refactor database layer", StoryStatus.NEW, 8, "");
        story5.setId(generateId());
        stories.add(story5);

        Story story6 = new Story("Add unit tests for services", StoryStatus.NEW, 5, "");
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
}
