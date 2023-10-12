package test.clevertec.course.heap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.clevertec.course.heap.impl.PriorityArrayListHeap;
import test.clevertec.course.heap.model.TestComparable;
import test.clevertec.course.heap.model.TestNotComparable;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PriorityArrayListHeapTest {


    @ParameterizedTest
    @MethodSource("IntegerPriorityHeapDataProvider")
    void givenList_whenPoll_thenReturnMin(List<Integer> integers) {
        PriorityArrayListHeap<Integer> priorityHeap = new PriorityArrayListHeap<>();
        priorityHeap.addAll(integers);
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(integers);
        Integer actual = priorityHeap.poll();
        Integer expected = priorityQueue.poll();

        assertEquals(expected, actual);
    }

    static Stream<Arguments> IntegerPriorityHeapDataProvider() {
        return Stream.of(
                Arguments.of(List.of(4, 2, 6, 12, 9, 2, 61, 1)),
                Arguments.of(List.of(12, 822, 2, 143, 1, 32, 61, 111)),
                Arguments.of(List.of(-4, 0, -12, 461, 3, 32, 135, 1)),
                Arguments.of(List.of(42)));
    }


    @Test
    void givenNotComparableClassWithoutComparator_whenCreateAndAddMoreThanOne_thenThrow() {
        assertThrows(ClassCastException.class,
                () -> {
                    PriorityArrayListHeap<TestNotComparable> prior = new PriorityArrayListHeap<>();
                    prior.add(new TestNotComparable(1));
                    prior.add(new TestNotComparable(2));
                }
        );
    }

    @Test
    void givenNull_whenAdd_thenThrow() {
        assertThrows(NullPointerException.class,
                () -> new PriorityArrayListHeap<TestComparable>().add(null));
    }

    @Test
    void givenEmptyHeap_whenPeekOrPoll_thenNull() {
        PriorityArrayListHeap<Integer> priorityHeap = new PriorityArrayListHeap<>();
        assertNull(priorityHeap.peek());
        assertNull(priorityHeap.poll());

    }

    @Test
    void givenComparableClassWithComparator_whenCreate_thenPeek() {
        TestComparable expected = new TestComparable(10, 0.0);
        PriorityArrayListHeap<TestComparable> priorityHeap = new PriorityArrayListHeap<>();
        priorityHeap.add(expected);
        TestComparable actual = priorityHeap.peek();
        assertEquals(expected, actual);
    }


    @ParameterizedTest
    @MethodSource("IntegerAndComparatorPriorityHeapDataProvider")
    void givenListAndComparator_whenPoll_thenReturn(List<Integer> integers,
                                                    Comparator<Integer> comparator) {
        PriorityArrayListHeap<Integer> priorityHeap = new PriorityArrayListHeap<>(comparator);
        priorityHeap.addAll(integers);
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(comparator);
        priorityQueue.addAll(integers);
        Integer actual = priorityHeap.poll();
        Integer expected = priorityQueue.poll();

        assertEquals(expected, actual);
    }

    static Stream<Arguments> IntegerAndComparatorPriorityHeapDataProvider() {
        return Stream.of(
                Arguments.of(List.of(4, 2, 6, 12, 9, 2, 61, 1), Comparator.naturalOrder()),
                Arguments.of(List.of(12, 822, 2, 143, 1, 32, 61, 111), Comparator.reverseOrder()),
                Arguments.of(List.of(-4, 0, -12, 461, 3, 32, 135, 1),
                        Comparator.comparingInt(value -> ((Integer) value) % 2)),
                Arguments.of(List.of(42), null));
    }


    @ParameterizedTest
    @MethodSource("IntegerPriorityHeapDataProvider")
    void givenList_whenPollAll_thenReturnNull(List<Integer> integers) {
        PriorityArrayListHeap<Integer> priorityHeap = new PriorityArrayListHeap<>();
        priorityHeap.addAll(integers);
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        priorityQueue.addAll(integers);
        while (!priorityHeap.isEmpty()) {
            priorityHeap.poll();
            priorityQueue.poll();
            assertThat(priorityHeap).containsExactlyElementsOf(priorityQueue);
        }


    }

    @ParameterizedTest
    @MethodSource("ComparableAndComparatorPriorityHeapDataProvider")
    void givenComparableListAndComparator_whenPoll_thenReturn(List<TestComparable> comparableList,
                                                              Comparator<TestComparable> comparator) {
        PriorityArrayListHeap<TestComparable> priorityHeap = new PriorityArrayListHeap<>(comparator);
        priorityHeap.addAll(comparableList);

        PriorityQueue<TestComparable> priorityQueue = new PriorityQueue<>(comparator);
        priorityQueue.addAll(comparableList);
        TestComparable actual = priorityHeap.poll();
        TestComparable expected = priorityQueue.poll();
        assertEquals(expected, actual);

    }

    static Stream<Arguments> ComparableAndComparatorPriorityHeapDataProvider() {
        return Stream.of(
                Arguments.of(getTestComparableList(), null),
                Arguments.of(getTestComparableList(), Comparator.reverseOrder()),
                Arguments.of(getTestComparableList(), Comparator.naturalOrder()),
                Arguments.of(getTestComparableList(), Comparator.comparingDouble(TestComparable::getPrice)),
                Arguments.of(getTestComparableList(),
                        Comparator.comparingDouble(TestComparable::getPrice).thenComparingInt(TestComparable::getId)),
                Arguments.of(getTestComparableList(), Comparator.comparingInt(TestComparable::getId)),
                Arguments.of(List.of(new TestComparable(1, 1.1)), null));
    }

    private static List<TestComparable> getTestComparableList() {
        return List.of(new TestComparable(12, 11.4),
                new TestComparable(-3, 11.4),
                new TestComparable(12, 121.4),
                new TestComparable(342, 98.92),
                new TestComparable(5, 24.2),
                new TestComparable(12, -3.3),
                new TestComparable(93, -3.3),
                new TestComparable(-6, 2.4),
                new TestComparable(6, 932.4));
    }


    @ParameterizedTest
    @MethodSource("IntegerListWithListToRemovePriorityHeapDataProvider")
    void givenIntegerListAndRemoveList_whenRemove_thenReturn(List<Integer> integers, List<Integer> toRemove) {
        PriorityArrayListHeap<Integer> priorityHeap = new PriorityArrayListHeap<>();
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

        priorityQueue.addAll(integers);
        priorityHeap.addAll(integers);
        toRemove.forEach(r -> {
            priorityHeap.remove(r);
            priorityQueue.remove(r);
        });


        assertThat(priorityHeap).containsExactlyElementsOf(priorityQueue);
    }

    static Stream<Arguments> IntegerListWithListToRemovePriorityHeapDataProvider() {
        return Stream.of(
                Arguments.of(List.of(4, 2, 6, 12, 9, 2, 61, 1), List.of(4, 2)),
                Arguments.of(List.of(12, 822, 2, 143, 1, 32, 61, 111), List.of(4, 2, 822, 1)),
                Arguments.of(List.of(-4, 0, -12, 461, 3, 32, 135, 1),
                        List.of(-4, 0, -12, 461, 3, 32, 135, 1)));
    }

}