package com.scrumsim.service;

import com.scrumsim.model.Story;
import java.util.List;

public interface BacklogService {

    List<Story> getBacklogStories(List<Story> sprintStories);
}
