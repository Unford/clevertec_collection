package ru.clevertec.course.heap;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.List;

public class PriorityHeap<T> extends AbstractCollection<T> {
    private List<T> heap;
    @Override
    public Iterator<T> iterator() {
        return heap.iterator();
    }

    @Override
    public int size() {
        return heap.size();
    }

}
