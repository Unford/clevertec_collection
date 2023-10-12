package ru.clevertec.course.heap.impl;

import ru.clevertec.course.heap.PriorityHeap;

import java.util.*;

@SuppressWarnings("unchecked")
public class PriorityArrayHeap<T> implements PriorityHeap<T> {
    private static final int INITIAL_CAPACITY = 8;
    private T[] heap;
    private int size;

    private final Comparator<? super T> comparator;

    public PriorityArrayHeap() {
        this(INITIAL_CAPACITY);
    }

    public PriorityArrayHeap(int initialCapacity) {
        this(initialCapacity, null);
    }

    public PriorityArrayHeap(Comparator<? super T> comparator) {
        this(INITIAL_CAPACITY, comparator);
    }

    public PriorityArrayHeap(int initialCapacity,
                             Comparator<? super T> comparator) {
        this((T[]) new Object[initialCapacity > 0 ? initialCapacity : INITIAL_CAPACITY], comparator);
    }

    public PriorityArrayHeap(PriorityQueue<T> priorityQueue) {
        this(((T[]) priorityQueue.toArray(new Object[0])), priorityQueue.comparator());
    }

    private PriorityArrayHeap(T[] heap, Comparator<? super T> comparator) {
        if (comparator == null) {
            comparator = (Comparator<T>) (l, r) -> ((Comparable<? super T>) l).compareTo(r);
        }
        this.heap = heap;
        this.comparator = comparator;
        this.size = 0;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(T t) {
        if (t == null) throw new NullPointerException();
        int i = size++;
        if (i >= heap.length) {
            growHeap(i + 1);
        }
        siftUp(i, t);
        return true;
    }

    private void growHeap(int oldCapacity) {
        heap = Arrays.copyOf(heap, oldCapacity << 1);
    }


    @Override
    public T poll() {
        T entity = heap[0];
        if (entity != null) {
            int lastElementIndex = --size;
            T x = heap[lastElementIndex];
            heap[lastElementIndex] = null;
            if (size() > 0) {
                siftDown(0, x);
            }
        }

        return entity;
    }


    @Override
    public T peek() {
        return size == 0 ? null : heap[(0)];
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
            T c = heap[child];
            if (right < size &&
                    comparator.compare(c,
                            heap[right]) > 0) {

                c = heap[right];
                child = right;
            }
            if (comparator.compare(x, c) <= 0) {
                break;
            }
            heap[i] = c;
            i = child;
        }
        heap[i] = x;

    }

    private void siftUp(int i, T x) {
        while (i > 0) {
            int parentIndex = getParentIndex(i);
            T e = heap[parentIndex];
            if (comparator.compare(x, e) >= 0) {
                break;
            }
            heap[i] = e;
            i = parentIndex;
        }
        heap[i] = x;


    }

    public void addAll(Collection<T> collection) {
        collection.forEach(this::add);
    }

    public Comparator<? super T> getComparator() {
        return comparator;
    }

    public Object[] toArray() {


        return Arrays.copyOf(heap, size);
    }


}
