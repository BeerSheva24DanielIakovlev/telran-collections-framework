package telran.util;

import java.util.Iterator;
import telran.util.LinkedList.Node;

public class LinkedHashSet<T> implements Set<T> {
    private LinkedList<T> list = new LinkedList<>();
    HashMap<T, Node<T>> map = new HashMap<>();

    @Override
    public boolean add(T obj) {
        boolean res = false;
        if (!contains(obj)) {
            Node<T> node = new Node<>(obj);
            list.addNode(node, list.size());
            map.put(obj, node);
            res = true;

        }

        return res;
    }

    @Override
    public boolean remove(T pattern) {
        boolean res = contains(pattern);
       
        if (res) {
            Node<T> node = map.get(pattern);
            list.removeNode(node);
            map.remove(pattern);
        }

        return res;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(T pattern) {
        return map.containsKey(pattern);
    }

    @Override
    public Iterator<T> iterator() {
        return new LHSIterator();
    }

    private class LHSIterator implements Iterator<T> {
        Iterator<T> iterator = list.iterator();
        T meow;

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public T next() {
            meow = iterator.next();
            return meow;
        }
        
        @Override
        public void remove() {
            iterator.remove();
            map.remove(meow);
        }
    }

    @Override
    public T get(Object pattern) {
        Node<T> node = map.get(pattern);
        return node == null ? null : node.obj;
    }

}