package com.scrumsim.store;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryDataStore<K, V> implements DataStore<K, V> {

    // ConcurrentHashMap ensures thread-safe operations without locking the entire map
    private final ConcurrentHashMap<K, V> storage;

    public InMemoryDataStore() {
        this.storage = new ConcurrentHashMap<>();
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
        // Return a snapshot of values to avoid concurrent modification issues
        // Creating a new ArrayList ensures the caller can iterate safely even if the underlying storage changes
        return new ArrayList<>(storage.values());
    }

    @Override
    public void clear() {
        storage.clear();
    }
}
