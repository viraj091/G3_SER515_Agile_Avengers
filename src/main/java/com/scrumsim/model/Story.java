package com.scrumsim.model;

/**
 * Represents a user story in the product backlog.
 * Follows SRP by only managing story-related data and state.
 * Immutable fields with controlled mutation through methods.
 */
public class Story {
    private final String title;
    private final String assignees;
    private StoryStatus status;
    private int points;

    public Story(String title, StoryStatus status, int points, String assignees) {
        this.title = title;
        this.status = status;
        this.points = points;
        this.assignees = assignees;
    }

    public String getTitle() {
        return title;
    }

    public StoryStatus getStatus() {
        return status;
    }

    public void setStatus(StoryStatus status) {
        this.status = status;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Story points cannot be negative");
        }
        this.points = points;
    }

    public String getAssignees() {
        return assignees;
    }

    /**
     * Check if this story is completed.
     * Encapsulates the logic of what "done" means for a story.
     */
    public boolean isCompleted() {
        return status == StoryStatus.DONE;
    }
}
