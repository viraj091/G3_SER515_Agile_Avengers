package com.scrumsim.service;

public interface StatusRefreshService {

    void start();

    void stop();

    void addListener(StatusUpdateListener listener);

    void removeListener(StatusUpdateListener listener);

    interface StatusUpdateListener {
        void onStatusUpdate();
    }
}
