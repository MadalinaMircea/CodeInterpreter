package model.utils;

import java.util.Map;

public interface IHeap<K,V> {
    void add(K k, V v);
    void update(K k, V v);
    boolean contains(K k);
    V get(K k);
    Iterable<K> getAll();
    void setContent(Map<K,V> m);
    Map<K,V> getContent();
}
