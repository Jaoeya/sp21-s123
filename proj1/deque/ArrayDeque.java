package deque;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * @author jiaxing zhou
 * @version 1.0
 */
public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    private int nextFirst;
    private int nextLast;
    private int size;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;
        size = 0;
    }

    private boolean isFull() {
        return size == items.length;
    }


    private int plusOne(int index) {
        return (index + 1) % items.length;
    }

    /**
     * Minus one circularly.
     */
    private int minusOne(int index) {
        // unlike Python, in Java, the % symbol represents "remainder" rather than "modulus",
        // therefore, it may give negative value, so + items.length is necessary,
        // or to use Math.floorMod(x, y)
        return (index - 1 + items.length) % items.length;
    }

    private boolean isSparse() {
        return items.length >= 16 && size < (items.length / 4);
    }

    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        int oldIndex = plusOne(nextFirst);
        // System.arraycopy(items, oldIndex, newItems, 0, size);
        for (int i = 0; i < size; i++) {
            newItems[i] = items[oldIndex];
            oldIndex = plusOne(oldIndex);
        }

        items = newItems;
        nextLast = size;
        nextFirst = capacity - 1;
    }

    private void upSize() {
        resize(size * 2);
    }

    private void downSize() {
        resize(items.length / 2);
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void addFirst(T item) {
        if (isFull()) {
            upSize();
        }
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    @Override
    public void addLast(T item) {
        if (isFull()) {
            upSize();
        }
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size += 1;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        int startIndex = plusOne(nextFirst);
        for (int i = startIndex; i != nextLast; i = plusOne(i)) {
            System.out.print(items[i] + "->");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {

        if (isSparse()) {
            downSize();
        }
        if (isEmpty()) {
            return null;
        }
        int index = plusOne(nextFirst);
        T removeItem = (T) items[index];
        items[index] = null;
        nextFirst = index;
        size -= 1;
        return removeItem;
    }

    @Override
    public T removeLast() {
        if (isSparse()) {
            downSize();
        }
        if (isEmpty()) {
            return null;
        }
        int index = minusOne(nextLast);
        T removeItem = (T) items[index];
        items[index] = null;
        nextLast = index;
        size -= 1;
        return removeItem;
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        int start = plusOne(nextFirst);
        return items[(start + index) % items.length];
    }

    @Override
    public Iterator<T> iterator() {
        return new DequeIteratot<T>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof ArrayDeque)) return false;

        ArrayDeque<T> that = (ArrayDeque<T>) o;

        if (nextFirst != that.nextFirst) return false;
        if (nextLast != that.nextLast) return false;
        if (size != that.size) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(items, that.items);
    }


    private class DequeIteratot<T> implements Iterator<T> {
        private int seer;

        public DequeIteratot() {
            seer = nextFirst;
        }

        @Override
        public boolean hasNext() {
            return seer != nextLast;
        }

        @Override
        public T next() {
            seer = plusOne(seer);
            return (T) items[seer];
        }
    }
}
