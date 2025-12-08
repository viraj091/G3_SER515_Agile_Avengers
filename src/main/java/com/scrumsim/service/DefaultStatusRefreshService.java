package com.scrumsim.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DefaultStatusRefreshService implements StatusRefreshService {

    private final List<StatusUpdateListener> listeners;
    private Timer timer;

    public DefaultStatusRefreshService() {
        this.listeners = new ArrayList<>();
    }

    @Override
    public void start() {
        if (timer != null) {
            return;
        }
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                notifyListeners();
            }
        }, 0, 3000);
    }

    @Override
    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void addListener(StatusUpdateListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    @Override
    public void removeListener(StatusUpdateListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (StatusUpdateListener listener : new ArrayList<>(listeners)) {
            listener.onStatusUpdate();
        }
    }
}
