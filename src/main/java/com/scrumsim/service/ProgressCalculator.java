package com.scrumsim.service;

import com.scrumsim.model.Story;
import java.util.List;

public interface ProgressCalculator {

    int calculateTotalPoints(List<Story> stories);

    int calculateCompletedPoints(List<Story> stories);

    String getProgressMessage(List<Story> stories, int sprintGoal);
}
