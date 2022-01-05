package deque;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

/**
 * @author jiaxing zhou
 * @version 1.0
 */
public class MaxArrayDeque<T> extends ArrayDeque<T> implements Deque<T> {

    private Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c) {
        comparator = c;
    }

    public T max() {
        return max(comparator);
    }

    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        int maxIndex = 0;
        for (int i = 1; i < size(); i++) {
            if (c.compare(get(i), get(maxIndex)) > 0) {
                maxIndex = i;
            }
        }
        return get(maxIndex);

    }
}
