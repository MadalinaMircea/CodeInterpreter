package model.utils;

import java.util.ArrayList;
import java.util.List;

public class OutputList<E> implements IList<E> {
    private List<E> l;
    public OutputList()
    {
        l = new ArrayList<>();
    }
    public void add(E el)
    {
        l.add(el);
    }
    @Override
    public String toString()
    {
        StringBuffer buff = new StringBuffer();
        buff.append("\nList:\n");
        if(l.isEmpty())
            buff.append("\tEmpty\n");
        else
            for(E el : l)
            {
                buff.append("\t");
                buff.append(el);
                buff.append("\n");
            }
        return buff.toString();
    }
    public void clear()
    {
        l.clear();
    }

    @Override
    public Iterable<E> getAll() {
        return l;
    }
}
