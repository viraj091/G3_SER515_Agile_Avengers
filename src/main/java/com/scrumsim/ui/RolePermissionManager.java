package com.scrumsim.ui;

import com.scrumsim.model.User;
import com.scrumsim.model.UserRole;

import javax.swing.*;

public class RolePermissionManager {

    public boolean canCreateTeam(User user) {
        if (user == null) {
            return false;
        }
        return user.getRole() == UserRole.SCRUM_MASTER;
    }

    public boolean canManageRoles(User user) {
        if (user == null) {
            return false;
        }
        return user.getRole() == UserRole.SCRUM_MASTER;
    }

    public boolean canAssignStory(User user) {
        if (user == null) {
            return false;
        }
        return user.getRole() == UserRole.SCRUM_MASTER;
    }

    public boolean canReviewBusinessValue(User user) {
        return user.getRole() == UserRole.PRODUCT_OWNER;
    }

    public void applyButtonPermission(JButton button, User user, String featureName) {
        boolean hasPermission = false;

        if (featureName.equals("Create Team")) {
            hasPermission = canCreateTeam(user);
        } else if (featureName.equals("Manage Roles")) {
            hasPermission = canManageRoles(user);
        } else if (featureName.equals("Assign Story")) {
            hasPermission = canAssignStory(user);
        } else if (featureName.equals("Review Business Value")) {
            hasPermission = canReviewBusinessValue(user);
        }

        if (!hasPermission) {
            button.setVisible(false);
            button.setEnabled(false);
            button.setToolTipText("You do not have permission to access this feature");
        } else {
            button.setVisible(true);
            button.setEnabled(true);
            button.setToolTipText(null);
        }
    }

    public boolean shouldShowButton(User user, String featureName) {
        if (featureName.equals("Create Team")) {
            return canCreateTeam(user);
        } else if (featureName.equals("Manage Roles")) {
            return canManageRoles(user);
        } else if (featureName.equals("Assign Story")) {
            return canAssignStory(user);
        } else if (featureName.equals("Review Business Value")) {
            return canReviewBusinessValue(user);
        }
        return true;
    }
}
