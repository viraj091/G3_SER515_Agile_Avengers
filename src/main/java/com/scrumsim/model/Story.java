package com.scrumsim.model;

public class Story {
    private String title;
    private String description;
    private String assignees;
    private StoryStatus status;
    private int points;

    public Story(String title, String description, StoryStatus status, int points, String assignees) {
        this.title = title;
        this.description = description != null ? description : "";
        this.status = status;
        this.points = points;
        this.assignees = assignees != null ? assignees : "";
    }

    public Story(String title, StoryStatus status, int points, String assignees) {
        this(title, "", status, points, assignees);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Story title cannot be empty");
        }
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description != null ? description : "";
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

    public void setAssignees(String assignees) {
        this.assignees = assignees != null ? assignees : "";
    }

    public boolean isCompleted() {
        return status == StoryStatus.DONE;
    }
}
