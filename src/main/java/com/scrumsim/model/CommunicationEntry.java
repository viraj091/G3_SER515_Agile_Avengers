package com.scrumsim.model;

import java.time.LocalDateTime;

public class CommunicationEntry {

    private final String stakeholderName;
    private final String message;
    private final LocalDateTime timestamp;

    public CommunicationEntry(String stakeholderName, String message) {
        this.stakeholderName = stakeholderName;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public String getStakeholderName() {
        return stakeholderName;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}