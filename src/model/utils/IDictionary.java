package model.utils;

import java.util.Map;

public interface IDictionary<K,V> {
    boolean contains(K key);
    void update(K key, V value);
    void add(K key, V value);
    V get(K key);
    void clear();
    Iterable<K> getAll();
    Map<K,V> getContent();
    IDictionary<K,V> copy();
}
