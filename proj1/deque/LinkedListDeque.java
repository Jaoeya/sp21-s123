package deque;


import net.sf.saxon.om.Item;

/**
 * @author jiaxing zhou
 * @version 1.0
 */
public class LinkedListDeque<T> {

    private int size;
    private Node sentinel;
    private Node first;
    private Node last;


    public LinkedListDeque() {
        sentinel = new Node(null, null);
        sentinel.next = sentinel;
        size = 0;
        first = null;
        last = null;
    }

    public void addFirst(T item) {
        Node node = new Node(item, null);
        first = node;
        size += 1;
        sentinel.next = node;
        if (size == 1) {
            node.next = sentinel;
            last = first;
        } else {
            node.next = first;
        }
    }

    public void addLast(T item) {
        Node node = new Node(item, null);
        size += 1;
        node.next = sentinel;
        if (size == 1) {
            sentinel.next = node;
            last = node;
            first = last;
        } else {
            last.next = node;
            last = node;
        }
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
            first = first.next;
        } else {
            sentinel.next=sentinel;
            first = null;
            last = null;
        }
        size -= 1;
        return (T) node.item;
    }

    public T removeLast() {
        if (size == 0) return null;
        if (size == 1) {
            first = null;
            last = null;
            sentinel.next = sentinel;
            size -= 1;
            return (T) last;
        }
        int count = size;
        Node node = first;
        while (count > 1) {
            node = node.next;
            count -= 1;
        }
        Node result = node.next;
        node.next = sentinel;
        last = node;
        size -= 1;
        return (T) result.item;

    }

    public T get(int index) {
        int count = 1;
        Node node = first;
        if (index <= size) {
            while (count < size) {
                node = node.next;
                count += 1;
            }
            return (T) node;
        } else {
            return null;
        }
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

        public Node(T item, Node next) {
            this.item = item;
            this.next = next;
        }
    }
}
