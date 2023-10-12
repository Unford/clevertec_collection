package test.clevertec.course.heap;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import ru.clevertec.course.heap.impl.PriorityHeapImpl;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class PriorityHeapImplBenchmark {
    private static final int ITERATION_COUNT = 10000;
    private PriorityHeapImpl<Integer> globalHeap;
    private PriorityQueue<Integer> globalQueue;

    @Setup(Level.Invocation)
    public void setupInvokation() {
        globalHeap = new PriorityHeapImpl<>();
        globalQueue = new PriorityQueue<>();
        for (int i = 0; i < ITERATION_COUNT; i++) {
            globalHeap.add(i);
            globalQueue.add(i);

        }
    }


    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 3)
    @Measurement(iterations = 3)
    @Fork(value = 1, warmups = 1)
    public boolean benchmarkStandardPriorityQueueAdd(Blackhole blackhole) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i = 0; i < ITERATION_COUNT; i++) {
            priorityQueue.add(i);
        }
        boolean res = priorityQueue.size() == ITERATION_COUNT;
        blackhole.consume(res);
        return res;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 3)
    @Measurement(iterations = 3)
    @Fork(value = 1, warmups = 1)
    public boolean benchmarkCustomPriorityQueueAdd(Blackhole blackhole) {
        PriorityHeapImpl<Integer> priorityHeap = new PriorityHeapImpl<>(Comparator.reverseOrder());
        for (int i = 0; i < ITERATION_COUNT; i++) {
            priorityHeap.add(i);
        }
        boolean res = priorityHeap.size() == ITERATION_COUNT;
        blackhole.consume(res);
        return res;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 3)
    @Measurement(iterations = 3)
    @Fork(value = 1, warmups = 1)
    public boolean benchmarkStandardPriorityQueuePoll(Blackhole blackhole) {
        for (int i = 0; i < ITERATION_COUNT; i++) {
            globalQueue.poll();
        }
        boolean res = globalQueue.isEmpty();
        blackhole.consume(res);
        return res;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 3)
    @Measurement(iterations = 3)
    @Fork(value = 1, warmups = 1)
    public boolean benchmarkCustomPriorityQueuePoll(Blackhole blackhole) {
        for (int i = 0; i < ITERATION_COUNT; i++) {
            globalHeap.poll();
        }
        boolean res = globalHeap.isEmpty();
        blackhole.consume(res);
        return res;
    }

}
