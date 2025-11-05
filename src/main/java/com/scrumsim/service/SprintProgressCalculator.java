package com.scrumsim.service;

import com.scrumsim.model.Story;
import java.util.List;

/**
 * Implementation of progress calculation for Scrum sprints.
 * Follows SRP by only handling progress calculation logic.
 * Follows OCP by being extensible through the interface.
 */
public class SprintProgressCalculator implements ProgressCalculator {

    @Override
    public int calculateTotalPoints(List<Story> stories) {
        return stories.stream()
                .mapToInt(Story::getPoints)
                .sum();
    }

    @Override
    public int calculateCompletedPoints(List<Story> stories) {
        return stories.stream()
                .filter(Story::isCompleted)
                .mapToInt(Story::getPoints)
                .sum();
    }

    @Override
    public String getProgressMessage(List<Story> stories, int sprintGoal) {
        int currentPoints = calculateTotalPoints(stories);
        return String.format("Sprint Progress: %d/%d points", currentPoints, sprintGoal);
    }
}
