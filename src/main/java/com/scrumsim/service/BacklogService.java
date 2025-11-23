package com.scrumsim.service;

import com.scrumsim.model.Story;
import java.util.List;

public interface BacklogService {

    List<Story> getBacklogStories(List<Story> sprintStories);

    Story createStory(String title, String description, int points);

    void updatePriority(String storyId, int newPriority);

    List<Story> getStoriesSortedByPriority();

    void increasePriority(String storyId);

    void decreasePriority(String storyId);
}