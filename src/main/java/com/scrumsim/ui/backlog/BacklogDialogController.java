package com.scrumsim.ui.backlog;

import com.scrumsim.model.Story;
import com.scrumsim.service.BacklogService;

public class BacklogDialogController {

    private final BacklogService backlogService;
    private final PriorityChangeListener listener;

    public BacklogDialogController(BacklogService backlogService, PriorityChangeListener listener) {
        if (backlogService == null) {
            throw new IllegalArgumentException("BacklogService cannot be null");
        }
        if (listener == null) {
            throw new IllegalArgumentException("PriorityChangeListener cannot be null");
        }
        this.backlogService = backlogService;
        this.listener = listener;
    }

    
    public void handleIncreasePriority(Story story) {
        if (story == null || story.getId() == null) {
            System.err.println("Cannot increase priority: Invalid story");
            return;
        }

        backlogService.increasePriority(story.getId());
        listener.onPriorityChanged();
    }


    public void handleDecreasePriority(Story story) {
        if (story == null || story.getId() == null) {
            System.err.println("Cannot decrease priority: Invalid story");
            return;
        }

        backlogService.decreasePriority(story.getId());
        listener.onPriorityChanged();
    }

    public interface PriorityChangeListener {
        void onPriorityChanged();
    }
}
