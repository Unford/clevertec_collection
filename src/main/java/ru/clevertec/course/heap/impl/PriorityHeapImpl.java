package ru.clevertec.course.heap.impl;

import java.util.*;

@SuppressWarnings("unchecked")
public class PriorityHeapImpl<T> extends AbstractQueue<T> {
    private static final int INITIAL_CAPACITY = 10;
    private final List<T> heap;
    private final Comparator<? super T> comparator;

    public PriorityHeapImpl(int initialCapacity,
                            Comparator<? super T> comparator) {
        if (initialCapacity < 1) throw new IllegalArgumentException();
        if (comparator == null) {
            comparator = (Comparator<T>) (l, r) -> ((Comparable<? super T>) l).compareTo(r);
        }
        this.heap = new ArrayList<>(initialCapacity);
        this.comparator = comparator;
    }

    public PriorityHeapImpl(int initialCapacity) {
        this(initialCapacity, null);
    }

    public PriorityHeapImpl(Comparator<? super T> comparator) {
        this(INITIAL_CAPACITY, comparator);
    }

    public PriorityHeapImpl() {
        this(null);
    }

    @Override
    public Iterator<T> iterator() {
        return heap.iterator();
    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public boolean offer(T t) {
        heap.add(t);
        siftUp(size() - 1);
        return true;
    }

    @Override
    public T poll() {
        if (heap.isEmpty()) {
            return null;
        }
        T entity = heap.get(0);
        int lastElementIndex = size() - 1;
        heap.set(0, heap.get(lastElementIndex));
        heap.remove(lastElementIndex);
        siftDown(0);
        return entity;
    }


    @Override
    public T peek() {
        return heap.isEmpty() ? null : heap.get(0);
    }

    private int getLeftChildIndex(int i) {
        return i * 2 + 1;
    }

    private int getRightChildIndex(int i) {
        return getLeftChildIndex(i) + 1;
    }

    private int getParentIndex(int i) {
        return (i - 1) / 2;
    }

    private void siftDown(int i) {
        while (getLeftChildIndex(i) < heap.size()) {
            int left = getLeftChildIndex(i);
            int right = getRightChildIndex(i);
            int j = left;

            if (right < heap.size() &&
                    comparator.compare(heap.get(right), heap.get(left)) > 0) {

                j = right;
            }
            if (comparator.compare(heap.get(i), heap.get(j)) >= 0) {
                break;
            }
            Collections.swap(heap, i, j);
            i = j;
        }
    }

    private void siftUp(int i) {
        while (comparator.compare(heap.get(i), heap.get(getParentIndex(i))) < 0) {
            Collections.swap(heap, i, getParentIndex(i));
            i = getParentIndex(i);
        }
    }


}
