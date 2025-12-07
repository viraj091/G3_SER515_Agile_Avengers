package com.scrumsim.service;

import com.scrumsim.model.TeamMembers;

public class DefaultAssignmentValidator implements AssignmentValidator {

    @Override
    public boolean isValidAssignee(String assigneeName) {
        if (assigneeName == null || assigneeName.trim().isEmpty()) {
            return true;
        }
        return TeamMembers.isValidAssignee(assigneeName);
    }
}