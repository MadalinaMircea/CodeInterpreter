package model.utils;

import java.util.HashMap;
import java.util.Map;

public class Heap<K,V> implements IHeap<K,V>{
    private Map<K,V> h;
    public Heap()
    {
        h = new HashMap<>();
    }

    public void add(K k, V v)
    {
        h.put(k, v);
    }

    public void update(K k, V v)
    {
        h.put(k, v);
    }

    public boolean contains(K k)
    {
        return h.containsKey(k);
    }
    public V get(K k)
    {
        return h.get(k);
    }

    public Iterable<K> getAll()
    {
        return h.keySet();
    }

    @Override
    public String toString()
    {
        StringBuffer buff = new StringBuffer();

        buff.append("Heap:\n");

        if(h.isEmpty())
            buff.append("\tEmpty\n");
        else
            for(Map.Entry<K,V> e : h.entrySet())
            {
                buff.append("\tKey: " + e.getKey() + " - Value: " + e.getValue() + "\n");
            }

        return buff.toString();
    }

    public void setContent(Map<K,V> m)
    {
        h = m;
    }

    public Map<K,V> getContent()
    {
        return h;
    }

}
