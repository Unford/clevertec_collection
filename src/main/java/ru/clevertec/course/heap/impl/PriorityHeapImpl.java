package ru.clevertec.course.heap.impl;

import java.util.*;

@SuppressWarnings("unchecked")
public class PriorityHeapImpl<T> extends AbstractQueue<T> {
    private static final int INITIAL_CAPACITY = 10;
    private final List<T> heap;
    private final Comparator<? super T> comparator;

    public PriorityHeapImpl() {
        this(INITIAL_CAPACITY);
    }

    public PriorityHeapImpl(int initialCapacity) {
        this(initialCapacity, null);
    }

    public PriorityHeapImpl(Comparator<? super T> comparator) {
        this(INITIAL_CAPACITY, comparator);
    }

    public PriorityHeapImpl(int initialCapacity,
                            Comparator<? super T> comparator) {
        this(new ArrayList<>(initialCapacity), comparator);
    }

    public PriorityHeapImpl(PriorityQueue<T> priorityQueue) {
        this(new ArrayList<>(priorityQueue), priorityQueue.comparator());
    }

    private PriorityHeapImpl(List<T> heap, Comparator<? super T> comparator) {
        if (comparator == null) {
            comparator = (Comparator<T>) (l, r) -> ((Comparable<? super T>) l).compareTo(r);
        }
        this.heap = heap;
        this.comparator = comparator;

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
        if (t == null) throw new NullPointerException();
        heap.add(t);
        siftUp(size() - 1, t);
        return true;
    }

    @Override
    public T poll() {
        if (heap.isEmpty()) {
            return null;
        }
        T entity = heap.get(0);
        int lastElementIndex = size() - 1;
        T x = heap.get(lastElementIndex);
        heap.remove(lastElementIndex);
        if (size() > 0) {
            siftDown(0, x);
        }
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

    private void siftDown(int i, T x) {
        int half = size() >>> 1;
        while (i < half) {
            int child = getLeftChildIndex(i);
            int right = getRightChildIndex(i);
            T c = heap.get(child);
            if (right < heap.size() &&
                    comparator.compare(c,
                            heap.get(right)) > 0) {

                c = heap.get(right);
                child = right;
            }
            if (comparator.compare(x, c) <= 0) {
                break;
            }
            heap.set(i, c);
            i = child;
        }
        heap.set(i, x);
    }

    private void siftUp(int i, T x) {
        while (i > 0) {
            int parentIndex = getParentIndex(i);
            T e = heap.get(parentIndex);
            if (comparator.compare(x, e) >= 0) {
                break;
            }
            heap.set(i, e);
            i = parentIndex;
        }
        heap.set(i, x);
    }


    @Override
    public boolean remove(Object o) {
        int i = this.heap.indexOf(o);
        if (i == -1)
            return false;
        else {
            removeAt(i);
            return true;
        }
    }

    private void removeAt(int i) {
        int s = size() - 1;
        if (i == s) {
            heap.remove(i);
        } else {
            T moved = heap.get(s);
            heap.remove(s);
            siftDown(i, moved);
            if (heap.get(i) == moved) {
                siftUp(i, moved);
            }
        }
    }

    public PriorityQueue<T> toPriorityQueue() {
        return new PriorityQueue<>(heap);
    }


}
