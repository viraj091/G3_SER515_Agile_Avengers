package com.scrumsim.service;

public interface StakeholderFeedbackService {

    void submitFeedback(String storyId, String stakeholderName, String message);
}