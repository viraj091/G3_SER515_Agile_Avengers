package com.scrumsim.model;

public class Story {
    private String id;
    private String title;
    private String description;
    private String assignees;
    private StoryStatus status;
    private int points;
    private int priority;
    private int orderIndex;
    private int rank;
    private int businessValue;
    private int urgency;
    private int effort;

    public Story(String title, String description, StoryStatus status, int points, String assignees) {
        this.title = title;
        this.description = description != null ? description : "";
        this.status = status;
        this.points = points;
        this.assignees = assignees != null ? assignees : "";
        this.priority = 0;
        this.orderIndex = 0;
        this.rank = 0;
        this.businessValue = 0;
        this.urgency = 0;
        this.effort = 0;
    }

    public Story(String title, StoryStatus status, int points, String assignees) {
        this(title, "", status, points, assignees);
    }

    public Story(String title, String description, StoryStatus status, int points, String assignees, int priority) {
        this.title = title;
        this.description = description != null ? description : "";
        this.status = status;
        this.points = points;
        this.assignees = assignees != null ? assignees : "";
        this.priority = priority;
        this.orderIndex = 0;
        this.rank = 0;
        this.businessValue = 0;
        this.urgency = 0;
        this.effort = 0;
    }

    public Story(String title, StoryStatus status, int points, String assignees, int priority) {
        this(title, "", status, points, assignees, priority);
    }

    public Story(String title, String description, StoryStatus status, int points, String assignees, int priority, int orderIndex) {
        this.title = title;
        this.description = description != null ? description : "";
        this.status = status;
        this.points = points;
        this.assignees = assignees != null ? assignees : "";
        this.priority = priority;
        this.orderIndex = orderIndex;
        this.rank = 0;
        this.businessValue = 0;
        this.urgency = 0;
        this.effort = 0;
    }

    public Story(String title, StoryStatus status, int points, String assignees, int priority, int orderIndex) {
        this(title, "", status, points, assignees, priority, orderIndex);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        if (assignees != null && !assignees.trim().isEmpty()) {
            if (!TeamMembers.isValidAssigneeList(assignees)) {
                throw new IllegalArgumentException("Invalid assignee. Only allowed team members: " +
                    String.join(", ", TeamMembers.ALLOWED_MEMBERS));
            }
        }
        this.assignees = assignees != null ? assignees : "";
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getBusinessValue() {
        return businessValue;
    }

    public void setBusinessValue(int businessValue) {
        this.businessValue = businessValue;
    }

    public int getUrgency() {
        return urgency;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }

    public int getEffort() {
        return effort;
    }

    public void setEffort(int effort) {
        this.effort = effort;
    }

    public boolean isCompleted() {
        return status == StoryStatus.DONE;
    }
}
