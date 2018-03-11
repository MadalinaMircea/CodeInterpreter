package model.utils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class ExecStack<E> implements IExecStack<E> {
    private Deque<E> stack;

    public ExecStack() {
        stack = new ArrayDeque<>();
    }

    public void push(E el) {
        stack.push(el);
    }

    public E pop() {
        return stack.pop();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer();
        buff.append("\nExecution Stack:\n");
        if (stack.isEmpty())
            buff.append("\tEmpty\n");
        else {
            for (E el : stack) {
                buff.append(el);
                buff.append("\n\n");
            }
        }
        return buff.toString();
    }

    public void clear() {
        stack.clear();
    }

    @Override
    public Iterable<E> getAll() {
        return stack;
    }

    public E peek() {
        return stack.peek();
    }
}
