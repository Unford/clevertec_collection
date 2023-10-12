package test.clevertec.course.heap.model;

import java.util.Comparator;

public class TestComparable implements Comparable<TestComparable> {
        private final int id;
        private final double price;


        public TestComparable(int id, double price) {
            this.id = id;
            this.price = price;
        }

        public int getId() {
            return id;
        }

        public double getPrice() {
            return price;
        }

        @Override
        public int compareTo(TestComparable o) {
            return Comparator.comparingInt(TestComparable::getId)
                    .thenComparingDouble(TestComparable::getPrice).compare(this, o);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TestComparable that)) return false;

            if (id != that.id) return false;
            return Double.compare(price, that.price) == 0;
        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = id;
            temp = Double.doubleToLongBits(price);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }
    }