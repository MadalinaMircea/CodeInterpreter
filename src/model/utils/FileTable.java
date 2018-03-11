package model.utils;

import java.util.HashMap;
import java.util.Map;

public class FileTable<K,V> implements IFileTable<K,V> {
    private Map<K,V> dict;
    public FileTable()
    {
        dict = new HashMap<>();
    }
    public void add(K key, V value)
    {
        dict.put(key, value);
    }
    public void remove(K key)
    {
        dict.remove(key);
    }
    public boolean contains(K key)
    {
        return dict.containsKey(key);
    }
    public V get(K key)
    {
        return dict.get(key);
    }
    public Iterable<K> getAll()
    {
        return dict.keySet();
    }
    public Iterable<V> getValues()
    {
        return dict.values();
    }
    @Override
    public String toString()
    {
        StringBuffer buff = new StringBuffer();
        buff.append("\nFileTable:\n");
        if(dict.isEmpty())
            buff.append("\tEmpty\n");
        else
            for(Map.Entry<K,V> d : dict.entrySet())
            {
                buff.append("\tKey: ");
                buff.append(d.getKey());
                buff.append(" - Value: ");
                buff.append(d.getValue());
                buff.append("\n");
            }
        return buff.toString();
    }
}
