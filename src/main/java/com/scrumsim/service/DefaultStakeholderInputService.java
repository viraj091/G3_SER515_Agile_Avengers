package com.scrumsim.service;

public class DefaultStakeholderInputService implements StakeholderInputService {

    private final StakeholderFeedbackService feedbackService;

    public DefaultStakeholderInputService(StakeholderFeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Override
    public void submitInput(String storyId, String stakeholderName, String message) {
        feedbackService.submitFeedback(storyId, stakeholderName, message);
    }
}