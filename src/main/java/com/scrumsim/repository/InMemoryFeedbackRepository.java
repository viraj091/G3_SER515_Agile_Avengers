package com.scrumsim.repository;

import com.scrumsim.model.Feedback;
import java.util.ArrayList;
import java.util.List;

public class InMemoryFeedbackRepository implements FeedbackRepository {

    private final List<Feedback> feedbacks;

    public InMemoryFeedbackRepository() {
        this.feedbacks = new ArrayList<>();
    }

    @Override
    public void save(Feedback feedback) {
        feedbacks.add(feedback);
    }

    @Override
    public List<Feedback> findByStoryId(String storyId) {
        List<Feedback> result = new ArrayList<>();
        for (Feedback feedback : feedbacks) {
            if (feedback.getStoryId().equals(storyId)) {
                result.add(feedback);
            }
        }
        return result;
    }

    @Override
    public List<Feedback> findAll() {
        return new ArrayList<>(feedbacks);
    }
}
