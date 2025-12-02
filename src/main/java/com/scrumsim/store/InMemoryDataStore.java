package com.scrumsim.store;

import com.scrumsim.model.CommunicationEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryDataStore<K, V> implements DataStore<K, V> {

    private final ConcurrentHashMap<K, V> storage;
    private final List<CommunicationEntry> communications;

    public InMemoryDataStore() {
        this.storage = new ConcurrentHashMap<>();
        this.communications = new CopyOnWriteArrayList<>();
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        storage.put(key, value);
    }

    @Override
    public Optional<V> get(K key) {
        if (key == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(storage.get(key));
    }

    @Override
    public void remove(K key) {
        if (key == null) {
            return; // Silently ignore null keys on removal
        }
        storage.remove(key);
    }

    @Override
    public boolean contains(K key) {
        if (key == null) {
            return false;
        }
        return storage.containsKey(key);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public Collection<V> getAllValues() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void clear() {
        storage.clear();
    }

    public void addCommunication(CommunicationEntry entry) {
        if (entry == null) {
            throw new IllegalArgumentException("Communication entry cannot be null");
        }
        communications.add(entry);
    }

    public List<CommunicationEntry> getAllCommunications() {
        return new ArrayList<>(communications);
    }
}