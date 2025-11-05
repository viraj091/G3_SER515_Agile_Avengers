package com.scrumsim.model;

import java.awt.Color;

/**
 * Enum representing the possible states of a user story.
 * Follows OCP by making it easy to add new statuses without modifying existing code.
 * Each status knows its own display properties (color, label).
 */
public enum StoryStatus {
    NEW("New", new Color(255, 140, 0)),           // Dark orange
    TODO("To Do", Color.DARK_GRAY),
    IN_PROGRESS("In Progress", new Color(50, 80, 200)),
    DONE("Done", new Color(0, 128, 0));

    private final String displayName;
    private final Color color;

    StoryStatus(String displayName, Color color) {
        this.displayName = displayName;
        this.color = color;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Color getColor() {
        return color;
    }

    /**
     * Convert display name back to enum.
     * Useful for UI components like dropdowns.
     */
    public static StoryStatus fromDisplayName(String displayName) {
        for (StoryStatus status : values()) {
            if (status.displayName.equals(displayName)) {
                return status;
            }
        }
        return NEW; // Default fallback
    }

    @Override
    public String toString() {
        return displayName;
    }
}
