package com.scrumsim.model;

public class StakeholderFeedback {
    private String storyId;
    private String stakeholderName;
    private String message;

    public StakeholderFeedback(String storyId, String stakeholderName, String message) {
        this.storyId = storyId;
        this.stakeholderName = stakeholderName;
        this.message = message;
    }

    public String getStoryId() {
        return storyId;
    }

    public String getStakeholderName() {
        return stakeholderName;
    }

    public String getMessage() {
        return message;
    }
}