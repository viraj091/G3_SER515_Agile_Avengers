package com.scrumsim.store;

import java.util.Collection;
import java.util.Optional;

public interface DataStore<K, V> {

    void put(K key, V value);

    Optional<V> get(K key);

    void remove(K key);

    boolean contains(K key);

    int size();

    Collection<V> getAllValues();

    //Removes all key-value pairs from the store. After this operation, the store will be empty.
    void clear();
}
