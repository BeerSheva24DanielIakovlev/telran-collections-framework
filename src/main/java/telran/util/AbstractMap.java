package telran.util;

import java.util.Iterator;

@SuppressWarnings("unchecked")
public abstract class AbstractMap<K, V> implements Map<K, V> {
    protected Set<Entry<K, V>> set;
    protected abstract Set<K> getEmptyKeySet();
    @Override
    public V get(Object key) {
        
        Entry<K, V> pattern = new Entry<>((K)key, null);
       Entry<K,V> entry = set.get(pattern);
       V res = null;
       if (entry != null) {
        res = entry.getValue();
       }
       return res;
    }

    @Override
    public V put(K key, V value) {

        Entry<K, V> newEntry = new Entry<>(key, value);
        Entry<K, V> existingEntry = set.get(newEntry);
    
        V oldValue = null;
    
        if (existingEntry != null) {
            oldValue = existingEntry.getValue();
            existingEntry.setValue(value);
        } else {
            set.add(newEntry);
        }
    
        return oldValue;

    }

    @Override
    public boolean containsKey(Object key) {
        Entry<K, V> pattern = new Entry<>((K)key, null);
        Entry<K, V> entry = set.get(pattern);

        return entry != null;
    }

    @Override
    public boolean containsValue(Object value) {
        boolean res = false;
        Iterator<Entry<K, V>> iterator = set.iterator();
        while (iterator.hasNext()) {
            Entry<K, V> entry = iterator.next();
            if ((value == null && entry.getValue() == null) || 
                (value != null && value.equals(entry.getValue()))) {
                res = true;
            }
        }
        return res;
    }
    

    @Override
    public Set<K> keySet() {
        Set<K> keySet = getEmptyKeySet();
        for (Entry<K, V> entry : set) {    
            keySet.add(entry.getKey());
        }
        return keySet;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
       return set;
    }

    @Override
    public Collection<V> values() {
        Collection<V> valuesCollection = new ArrayList<>();
        for (Entry<K, V> entry : set) {
            valuesCollection.add(entry.getValue());
        }
    return valuesCollection;
    }

    @Override
    public int size() {
       return set.size();
    }

    @Override
    public boolean isEmpty() {
       return set.isEmpty();
    }

}