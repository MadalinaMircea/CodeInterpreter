package model.utils;

public interface IList<E> {
    void add(E el);
    void clear();
    Iterable<E> getAll();
}
