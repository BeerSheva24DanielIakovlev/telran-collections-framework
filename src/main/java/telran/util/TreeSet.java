package telran.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class TreeSet<T> implements Set<T> {
    private static class Node<T> {
        T obj;
        Node<T> parent;
        Node<T> left;
        Node<T> right;

        Node(T obj) {
            this.obj = obj;
        }
    }

    private class TreeSetIterator implements Iterator<T> {
        private Node<T> current = findMin(root);
        private Node<T> lastReturned = null;
        private Node<T> max;
    
        @Override
        public boolean hasNext() {
            return current != null;
        }
    
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturned = current;
            current = getNextNode(current);
            return lastReturned.obj;
        }
    
        private Node<T> findMin(Node<T> node) {
            while (node != null && node.left != null) {
                node = node.left;
            }
            return node;
        }
    
        private Node<T> getNextNode(Node<T> node) {
            if (node.right != null) {
                return findMin(node.right);
            } else {
                Node<T> parent = node.parent;
                Node<T> child = node;
                while (parent != null && child == parent.right) {
                    child = parent;
                    parent = parent.parent;
                }
                return parent;
            }
        }

        @Override
        public void remove() {
            if (current.left == null && current.right == null) {
                current = null;
            } else {
                max = findMax(current.left);
                current = max;
                max = null;
            }
        }
    }

    public Node<T> findMin(Node<T> node) {
        while (node != null && node.left != null) {
            node = node.left;
        }
        return node;
    }

    public Node<T> findMax(Node<T> node) {
        while (node != null && node.right != null) {
            node = node.right;
        }
        return node;
    }


    private Node<T> root;
    private Comparator<T> comparator;
    int size;
    public TreeSet(Comparator<T> comparator) {
        this.comparator = comparator;
    } 
    
    public TreeSet() {
        this((Comparator<T>)Comparator.naturalOrder());
    }
    @Override
    public boolean add(T obj) {
        boolean res = false;
        if (!contains(obj)) {
            res = true;
            Node<T> node = new Node<>(obj);
            if(root == null) {
                addRoot(node);
            } else {
                addAfterParent(node);
            }
            size++;

        }
        return res;
    }

    private void addAfterParent(Node<T> node) {
        Node<T> parent = getParent(node.obj);
        if(comparator.compare(node.obj, parent.obj) > 0) {
            parent.right = node;
        } else {
            parent.left = node;
        }
        node.parent = parent;
    }

    private void addRoot(Node<T> node) {
        root = node;
    }

    @Override
    public boolean remove(T pattern) {
        boolean res = false;
        Node <T> max;
        Node <T> ss = getNode(pattern);
        if (ss != null) {
            if (ss.left == null && ss.right == null) {
                ss = null;
            } else {
                max = findMax(ss.left);
                ss = max;
                max = null;
            }
            res = true;
        }
        return res;
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
        return getNode(pattern) != null;
    }

    @Override
    public Iterator<T> iterator() {
        return new TreeSetIterator();
    }

    @Override
    public T get(Object pattern) {
        Node<T> node = getNode((T) pattern);
        return node != null ? node.obj : null;
    }

    private Node<T> getParentOrNode(T pattern) {
        Node<T> current = root;
        Node<T> parent = null;
        int compRes = 0;
        while(current != null && (compRes = comparator.compare(pattern, current.obj)) != 0) {
            parent = current;
            current = compRes > 0 ? current.right : current.left;
        }
        return current == null ? parent : current; 
    }

    private Node<T> getNode(T pattern) {
        Node<T> res = getParentOrNode(pattern);
        if(res != null) {
            int compRes = comparator.compare(pattern, res.obj);
            res = compRes == 0 ? res : null;
        }
        
        return res;
    }
    private Node<T> getParent(T pattern) {
        Node<T> res = getParentOrNode(pattern);
        int compRes = comparator.compare(pattern, res.obj);
        return compRes == 0 ? null : res;
    }

}

