package com.scrumsim.model;

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

    public boolean isCompleted() {
        return status == StoryStatus.DONE;
    }
}
