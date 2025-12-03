package com.scrumsim.repository;

import com.scrumsim.model.StakeholderFeedback;
import java.util.List;

public interface StakeholderFeedbackRepository {

    void save(StakeholderFeedback feedback);

    List<StakeholderFeedback> findByStoryId(String storyId);
}