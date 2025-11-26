package com.scrumsim.repository;

import com.scrumsim.model.Feedback;
import java.util.List;

public interface FeedbackRepository {

    void save(Feedback feedback);

    List<Feedback> findByStoryId(String storyId);

    List<Feedback> findAll();
}
