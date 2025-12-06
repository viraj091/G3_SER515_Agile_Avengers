package com.scrumsim.service;

import com.scrumsim.model.Story;
import com.scrumsim.model.User;

public class DefaultStoryUpdateGuard implements StoryUpdateGuard {

    @Override
    public boolean canUpdate(User user, Story story) {
        if (user == null) {
            return false;
        }

        if (story == null) {
            return false;
        }

        String assignees = story.getAssignees();
        if (assignees == null || assignees.trim().isEmpty()) {
            return false;
        }

        String userName = user.getName();
        if (userName == null) {
            return false;
        }

        String[] assigneeArray = assignees.split(",");
        for (int i = 0; i < assigneeArray.length; i++) {
            String assignee = assigneeArray[i].trim();
            if (assignee.equals(userName)) {
                return true;
            }
        }

        return false;
    }
}
