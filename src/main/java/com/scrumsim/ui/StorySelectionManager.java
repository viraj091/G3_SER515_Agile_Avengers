package com.scrumsim.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StorySelectionManager {

    private final Set<String> selectedStoryIds;

    public StorySelectionManager() {
        this.selectedStoryIds = new HashSet<>();
    }

    public void toggleSelect(String storyId) {
        if (selectedStoryIds.contains(storyId)) {
            selectedStoryIds.remove(storyId);
        } else {
            selectedStoryIds.add(storyId);
        }
    }

    public void clearSelection() {
        selectedStoryIds.clear();
    }

    public boolean isSelected(String storyId) {
        return selectedStoryIds.contains(storyId);
    }

    public List<String> getSelectedStoryIds() {
        return new ArrayList<>(selectedStoryIds);
    }

    public int getSelectionCount() {
        return selectedStoryIds.size();
    }
}
