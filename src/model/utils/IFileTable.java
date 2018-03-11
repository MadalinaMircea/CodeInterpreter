package model.utils;

public interface IFileTable<K,V> {
    void add(K key, V value);
    void remove(K key);
    boolean contains(K key);
    V get(K key);
    Iterable<K> getAll();
    Iterable<V> getValues();
}
