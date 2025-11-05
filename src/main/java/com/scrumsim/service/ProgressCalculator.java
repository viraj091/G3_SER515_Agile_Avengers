package com.scrumsim.service;

import com.scrumsim.model.Story;
import java.util.List;

/**
 * Interface for calculating sprint progress.
 * Follows ISP by defining only progress calculation methods.
 * Follows DIP by allowing high-level UI components to depend on this abstraction.
 */
public interface ProgressCalculator {

    /**
     * Calculate the total story points from a list of stories.
     * @param stories The list of stories to calculate from
     * @return Total points
     */
    int calculateTotalPoints(List<Story> stories);

    /**
     * Calculate completed story points from a list of stories.
     * @param stories The list of stories to calculate from
     * @return Points from completed stories
     */
    int calculateCompletedPoints(List<Story> stories);

    /**
     * Generate a formatted progress message.
     * @param stories The list of stories
     * @param sprintGoal The target points for the sprint
     * @return Formatted progress string
     */
    String getProgressMessage(List<Story> stories, int sprintGoal);
}
