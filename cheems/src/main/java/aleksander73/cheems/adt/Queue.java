package aleksander73.cheems.adt;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Queue<E> implements Iterable<E> {
    private List<E> elements = new ArrayList<>();

    @NonNull
    @Override
    public Iterator<E> iterator() {
        return elements.iterator();
    }

    public void add(E element) {
        elements.add(element);
    }

    public E remove() {
        return elements.remove(0);
    }

    public void clear() {
        elements.clear();
    }
}
