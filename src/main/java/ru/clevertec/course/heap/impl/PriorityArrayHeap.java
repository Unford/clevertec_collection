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
        this.comparator = (Comparator<T>) (l, r) -> ((Comparable<? super T>) l).compareTo(r);
        this.heap = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    public PriorityArrayHeap(int initialCapacity) {
        this.comparator = (Comparator<T>) (l, r) -> ((Comparable<? super T>) l).compareTo(r);
        this.heap = (T[]) new Object[initialCapacity];
        this.size = 0;
    }

    public PriorityArrayHeap(Comparator<? super T> comparator) {
        this.comparator = comparator;
        this.heap = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    public PriorityArrayHeap(int initialCapacity, Comparator<? super T> comparator) {
        this.comparator = comparator;
        this.heap = (T[]) new Object[initialCapacity];
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
            int left = getLeftChildIndex(i);
            int right = getRightChildIndex(i);
            if (right < size &&
                    comparator.compare(heap[left], heap[right]) > 0) {
                left = right;
            }
            if (comparator.compare(x, heap[left]) <= 0) {
                break;
            }
            swap(i, left);
            i = left;
        }
        heap[i] = x;

    }

    private void swap(int from, int to) {
        T temp = heap[from];
        heap[from] = heap[to];
        heap[to] = temp;
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


    public Object[] toArray() {
        return Arrays.copyOf(heap, size);
    }


}
