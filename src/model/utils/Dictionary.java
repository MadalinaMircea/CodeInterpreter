package model.utils;

import java.util.HashMap;
import java.util.Map;

public class Dictionary<K,V> implements IDictionary<K,V> {
    private Map<K,V> dict;
    public Dictionary()
    {
        dict = new HashMap<>();
    }
    public boolean contains(K key)
    {
        return dict.containsKey(key);
    }
    public void update(K key, V value)
    {
        dict.put(key, value);
    }
    public void add(K key, V value)
    {
        dict.put(key, value);
    }
    public V get(K key)
    {
        return dict.get(key);
    }
    @Override
    public String toString()
    {
        StringBuffer buff = new StringBuffer();
        buff.append("\nDictionary:\n");
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
    public void clear()
    {
        dict.clear();
    }
    public Iterable<K> getAll()
    {
        return dict.keySet();
    }
    public Map<K,V> getContent() {return dict;}
    public IDictionary<K,V> copy()
    {
        IDictionary<K,V> result = new Dictionary<K, V>();
        for(Map.Entry<K, V> e : dict.entrySet()){
            result.add(e.getKey(),e.getValue());
        }
        return result;
    }
}
