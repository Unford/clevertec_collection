package ru.clevertec.course.heap;


public interface PriorityHeap<T>  {
    boolean add(T entity);

    T peek();

    T poll();
    int size();


}
