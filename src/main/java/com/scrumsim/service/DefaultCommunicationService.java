package com.scrumsim.service;

import com.scrumsim.model.CommunicationEntry;
import com.scrumsim.store.InMemoryDataStore;
import java.util.Collections;
import java.util.List;

public class DefaultCommunicationService implements CommunicationService {

    private final InMemoryDataStore<?, ?> dataStore;

    public DefaultCommunicationService(InMemoryDataStore<?, ?> dataStore) {
        if (dataStore == null) {
            throw new IllegalArgumentException("DataStore cannot be null");
        }
        this.dataStore = dataStore;
    }

    @Override
    public void record(String stakeholderName, String message) {
        if (stakeholderName == null || stakeholderName.trim().isEmpty()) {
            throw new IllegalArgumentException("Stakeholder name cannot be null or empty");
        }
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }
        CommunicationEntry entry = new CommunicationEntry(stakeholderName, message);
        dataStore.addCommunication(entry);
    }

    @Override
    public List<CommunicationEntry> getAll() {
        return Collections.unmodifiableList(dataStore.getAllCommunications());
    }
}