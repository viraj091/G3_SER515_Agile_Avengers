package com.scrumsim.model;

import java.awt.Color;

public enum StoryStatus {
    NEW("New", new Color(255, 140, 0)),
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

    public static StoryStatus fromDisplayName(String displayName) {
        for (StoryStatus status : values()) {
            if (status.displayName.equals(displayName)) {
                return status;
            }
        }
        return NEW;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
