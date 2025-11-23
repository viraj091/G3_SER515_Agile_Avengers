package com.scrumsim.ui.backlog;

import com.scrumsim.model.User;
import com.scrumsim.store.UserSession;

public class BacklogPermissionHelper {

    private final UserSession userSession;

    public BacklogPermissionHelper() {
        this.userSession = UserSession.getInstance();
    }

    public boolean isProductOwner() {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return false;
        }
        return currentUser.isProductOwner();
    }

    public boolean isScrumMaster() {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return false;
        }
        return currentUser.isScrumMaster();
    }

    public boolean isDeveloper() {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return false;
        }
        return currentUser.isDeveloper();
    }

    public boolean canModifyPriority() {
        return isProductOwner();
    }

    public boolean canViewPriority() {
        return getCurrentUser() != null;
    }

    public boolean canCreateStory() {
        return isProductOwner();
    }

    public String getCurrentRoleDisplayName() {
        User currentUser = getCurrentUser();
        if (currentUser == null || currentUser.getRole() == null) {
            return "Unknown";
        }
        return currentUser.getRole().getDisplayName();
    }

    public String getPriorityRestrictionMessage() {
        if (isProductOwner()) {
            return "";
        }
        return "Only Product Owner can modify priorities";
    }

    private User getCurrentUser() {
        if (userSession == null) {
            return null;
        }
        return userSession.getCurrentUser();
    }
}