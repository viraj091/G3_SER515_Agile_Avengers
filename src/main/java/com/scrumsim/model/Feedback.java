package com.scrumsim.model;

public class Feedback {
    private String storyId;
    private String stakeholderName;
    private String message;
    private int businessValue;

    public Feedback(String storyId, String stakeholderName, String message, int businessValue) {
        this.storyId = storyId;
        this.stakeholderName = stakeholderName;
        this.message = message;
        this.businessValue = businessValue;
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

    public int getBusinessValue() {
        return businessValue;
    }
}
