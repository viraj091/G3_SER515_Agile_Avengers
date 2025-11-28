package com.scrumsim.repository;

import com.scrumsim.model.StakeholderFeedback;
import java.util.ArrayList;
import java.util.List;

public class InMemoryStakeholderFeedbackRepository implements StakeholderFeedbackRepository {

    private final List<StakeholderFeedback> feedbacks;

    public InMemoryStakeholderFeedbackRepository() {
        this.feedbacks = new ArrayList<>();
    }

    @Override
    public void save(StakeholderFeedback feedback) {
        feedbacks.add(feedback);
    }

    @Override
    public List<StakeholderFeedback> findByStoryId(String storyId) {
        List<StakeholderFeedback> result = new ArrayList<>();
        for (StakeholderFeedback feedback : feedbacks) {
            if (feedback.getStoryId().equals(storyId)) {
                result.add(feedback);
            }
        }
        return result;
    }
}