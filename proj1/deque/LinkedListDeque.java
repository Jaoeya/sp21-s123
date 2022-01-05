package deque;


import net.sf.saxon.om.Item;

import java.util.Iterator;

/**
 * @author jiaxing zhou
 * @version 1.0
 */
public class LinkedListDeque<T> implements Iterable<T> {

    private int size;
    private Node sentinel;
    private Node first;
    private Node last;


    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.pre = sentinel;
        size = 0;
        first = null;
        last = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof LinkedListDeque)) return false;

        LinkedListDeque<T> that = (LinkedListDeque<T>) o;

        if (size != that.size) return false;
        if (sentinel != null ? !sentinel.equals(that.sentinel) : that.sentinel != null) return false;
        if (first != null ? !first.equals(that.first) : that.first != null) return false;
        return last != null ? last.equals(that.last) : that.last == null;
    }


    public void addFirst(T item) {
        Node node = new Node(item, null);
        size += 1;
        sentinel.next = node;
        node.pre = sentinel;
        if (size == 1) {
            node.next = sentinel;
            sentinel.pre = node;
            last = node;
        } else {
            node.next = first;
            first.pre = node;
        }
        first = node;
    }

    public void addLast(T item) {
        Node node = new Node(item, null);
        size += 1;
        node.next = sentinel;
        sentinel.pre = node;
        if (size == 1) {
            sentinel.next = node;
            node.pre = sentinel;
            first = node;
        } else {
            node.pre = last;
            last.next = node;
        }
        last = node;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int count = size;
        Node node = first;
        while (count > 0) {
            System.out.println(first.item.toString() + "->\t");
            node = node.next;
            count -= 1;
        }
        System.out.println();
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        Node node = first;
        if (size != 1) {
            sentinel.next = first.next;
            first.next.pre = sentinel;
            first = first.next;
        } else {
            sentinel.next = sentinel;
            sentinel.pre = sentinel;
            first = null;
            last = null;
        }
        size -= 1;
        return (T) node.item;
    }

    public T removeLast() {
        if (size == 0) return null;

        Node node = last;

        if (size == 1) {
            first = null;
            last = null;
            sentinel.next = sentinel;
            sentinel.pre = sentinel;

        } else {
            last.pre.next = sentinel;
            sentinel.pre = last.pre;
            last = last.pre;
        }

        size -= 1;
        return (T) node.item;

    }

    public T get(int index) {
        if (index < 0 || index > size - 1) {
            return null;
        }
        Node currentNode = first;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                break;
            }
            currentNode = currentNode.next;
        }
        return currentNode.item;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator<T>();
    }

//    public T getRecursive(int index) {
//
//    }


//    public boolean equals(Object o) {
//
//    }


    private class Node {
        private T item;
        private Node next;
        private Node pre;

        public Node(T item, Node next, Node pre) {
            this.item = item;
            this.next = next;
            this.pre = pre;
        }

        public Node(T item, Node next) {
            this.item = item;
            this.next = next;
        }
    }


    private class LinkedListDequeIterator<T> implements Iterator<T> {

        private int seer;

        public LinkedListDequeIterator() {
            seer = 0;
        }

        @Override
        public boolean hasNext() {
            return seer != size;
        }

        @Override
        public T next() {
            T item = (T) get(seer);
            seer += 1;
            return item;
        }
    }

}
