package telran.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class HashSet<T> implements Set<T> {
    private static final int DEFAULT_HASH_TABLE_LENGTH = 16;
    private static final float DEFAULT_FACTOR = 0.75f;
    List<T>[] hashTable;
    float factor;
    int size;
    
    private class HashSetIterator implements Iterator<T> {
        Iterator<T> currentIterator;
        Iterator<T> prevIterator;
        int indexIterator;

        @Override
        public boolean hasNext() {
            while (currentIterator == null || !currentIterator.hasNext()) {
                if (indexIterator >= hashTable.length) {
                    return false;
                }
                currentIterator = hashTable[indexIterator++] != null ? hashTable[indexIterator - 1].iterator() : null;
            }
            return currentIterator != null && currentIterator.hasNext();
        }
        

        @Override
        public T next() {
             if(!hasNext()) {
                throw new NoSuchElementException();
            }
            prevIterator = currentIterator;
            return currentIterator.next();
        }

        @Override
        public void remove() {
            if (prevIterator == null) {
                throw new IllegalStateException();
            }
            prevIterator.remove();
            prevIterator = null;
            size--;
        }   
    }

    public HashSet(int hashTableLength, float factor) {
        hashTable = new List[hashTableLength];
        this.factor = factor;
    }

    public HashSet() {
        this(DEFAULT_HASH_TABLE_LENGTH, DEFAULT_FACTOR);
    }

    @Override
    public boolean add(T obj) {
        boolean res = false;
        if (!contains(obj)) {
            res = true;
            if(size >= hashTable.length * factor) {
                hashTableRealocation();
            }

            addObjInHashTable(obj, hashTable);
            size++;
        }
        
        return res;
    }

    private void addObjInHashTable(T obj, List<T>[] table) {
        int index = getIndex(obj, table.length);
        List<T> list = table[index];
        if (list == null) {
            list = new ArrayList<>(3);
        }
        
        list.add(obj);
        table[index] = list; 

    }

    private int getIndex(T obj, int length) {
        int hashCode = obj.hashCode();

        return Math.abs(hashCode % length);
    }

    private void hashTableRealocation() {
        List<T>[]tempTable = new List[hashTable.length * 2];

        for(List<T> list : hashTable) {
            if (list != null) {
                list.forEach(obj -> addObjInHashTable(obj, tempTable));
                list.clear(); //??? for testing (if it does not work -> remove this statement)
            }
        }
        hashTable = tempTable;
    }

    @Override
    public boolean remove(T pattern) {
        boolean res = false;

        if (contains(pattern)) {
            removeObjInHashTable(pattern, hashTable);
            
            res = true;
            size--;
        }

        return res;
    }

    private void removeObjInHashTable(T pattern, List<T>[] table) {
        int index = getIndex(pattern, table.length);
        List<T> list = table[index];
        list.remove(pattern);
        table[index] = list; 
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(T pattern) {
        int index = getIndex(pattern, hashTable.length); 
        List<T> list = hashTable[index];

        return list != null && list.contains(pattern);
    }

    @Override
    public Iterator<T> iterator() {
        return new HashSetIterator();
    }

    @Override
    public T get(Object pattern) {
        T res = null;
        int index = getIndex((T) pattern, hashTable.length);
        List<T> list = hashTable[index];

        if (list != null) {
            Iterator<T> iterator = list.iterator();
            while (iterator.hasNext()) {
                T element = iterator.next();
                if (element.equals(pattern)) {
                    res = element; 
                    break; 
                }
            }
        }
        return res;
    }
    

}
