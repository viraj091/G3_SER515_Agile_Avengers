package com.scrumsim.service;

import com.scrumsim.model.StakeholderFeedback;
import com.scrumsim.repository.StakeholderFeedbackRepository;

public class DefaultStakeholderFeedbackService implements StakeholderFeedbackService {

    private final StakeholderFeedbackRepository repository;

    public DefaultStakeholderFeedbackService(StakeholderFeedbackRepository repository) {
        this.repository = repository;
    }

    @Override
    public void submitFeedback(String storyId, String stakeholderName, String message) {
        StakeholderFeedback feedback = new StakeholderFeedback(storyId, stakeholderName, message);
        repository.save(feedback);
    }
}