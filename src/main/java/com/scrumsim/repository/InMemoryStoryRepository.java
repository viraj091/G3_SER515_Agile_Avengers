package com.scrumsim.repository;

import com.scrumsim.model.Story;
import com.scrumsim.model.StoryStatus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InMemoryStoryRepository implements StoryRepository {

    private final List<Story> stories;

    public InMemoryStoryRepository() {
        this.stories = new ArrayList<>(Arrays.asList(
            new Story("Implement user authentication system", StoryStatus.IN_PROGRESS, 8, "Sairaj Dalvi, Pranav Irlapale"),
            new Story("Design dashboard UI components", StoryStatus.TODO, 5, "Gunjan Purohit"),
            new Story("Setup CI/CD pipeline", StoryStatus.DONE, 13, "Shreyas Revankar, Viraj Rathor"),
            new Story("Create API documentation", StoryStatus.NEW, 3, "Viraj Rathor"),
            new Story("Refactor database layer", StoryStatus.NEW, 8, ""),
            new Story("Add unit tests for services", StoryStatus.NEW, 5, "")
        ));
    }

    @Override
    public List<Story> findAll() {
        return new ArrayList<>(stories);
    }

    @Override
    public Story findById(String id) {
        for (Story story : stories) {
            if (story.getTitle().equals(id)) {
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

        boolean found = false;
        for (int i = 0; i < stories.size(); i++) {
            if (stories.get(i).getTitle().equals(story.getTitle())) {
                stories.set(i, story);
                found = true;
                break;
            }
        }

        if (!found) {
            stories.add(story);
        }
    }

    @Override
    public boolean existsByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return false;
        }

        for (Story story : stories) {
            if (story.getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }
}
