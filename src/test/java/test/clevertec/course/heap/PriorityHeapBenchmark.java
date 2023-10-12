package test.clevertec.course.heap;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import ru.clevertec.course.heap.impl.PriorityArrayHeap;
import ru.clevertec.course.heap.impl.PriorityArrayListHeap;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 2)
@Measurement(iterations = 2)
@Fork(value = 1, warmups = 1)
public class PriorityHeapBenchmark {
    private static final int ITERATION_COUNT = 10000;
    private PriorityArrayListHeap<Integer> globalArrayListHeap;
    private PriorityArrayHeap<Integer> globalPriorityArrayHeap;

    private PriorityQueue<Integer> globalQueue;

    @Setup(Level.Invocation)
    public void setupInvokation() {
        globalArrayListHeap = new PriorityArrayListHeap<>();
        globalQueue = new PriorityQueue<>();
        globalPriorityArrayHeap = new PriorityArrayHeap<>();
        for (int i = 0; i < ITERATION_COUNT; i++) {
            globalArrayListHeap.add(i);
            globalQueue.add(i);
            globalPriorityArrayHeap.add(i);
        }
    }


    @Benchmark
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
    public boolean benchmarkStandardPriorityQueuePoll(Blackhole blackhole) {
        for (int i = 0; i < ITERATION_COUNT; i++) {
            globalQueue.poll();
        }
        boolean res = globalQueue.isEmpty();
        blackhole.consume(res);
        return res;
    }

    @Benchmark
    public boolean benchmarkCustomPriorityArrayListHeapAdd(Blackhole blackhole) {
        PriorityArrayListHeap<Integer> priorityHeap = new PriorityArrayListHeap<>(Comparator.reverseOrder());
        for (int i = 0; i < ITERATION_COUNT; i++) {
            priorityHeap.add(i);
        }
        boolean res = priorityHeap.size() == ITERATION_COUNT;
        blackhole.consume(res);
        return res;
    }


    @Benchmark
    public boolean benchmarkPriorityArrayListHeapPoll(Blackhole blackhole) {
        for (int i = 0; i < ITERATION_COUNT; i++) {
            globalArrayListHeap.poll();
        }
        boolean res = globalArrayListHeap.isEmpty();
        blackhole.consume(res);
        return res;
    }

    @Benchmark
    public boolean benchmarkCustomPriorityArrayHeapAdd(Blackhole blackhole) {
        PriorityArrayHeap<Integer> priorityHeap = new PriorityArrayHeap<>(Comparator.reverseOrder());
        for (int i = 0; i < ITERATION_COUNT; i++) {
            priorityHeap.add(i);
        }
        boolean res = priorityHeap.size() == ITERATION_COUNT;
        blackhole.consume(res);
        return res;
    }

    @Benchmark
    public boolean benchmarkPriorityArrayHeapPoll(Blackhole blackhole) {
        for (int i = 0; i < ITERATION_COUNT; i++) {
            globalPriorityArrayHeap.poll();
        }
        boolean res = globalPriorityArrayHeap.size() < 1;
        blackhole.consume(res);
        return res;
    }

}
