package model.utils;

public interface IExecStack<E>{
    void push(E el);
    E pop();
    boolean isEmpty();
    void clear();
    Iterable<E> getAll();
    E peek();
}
