package it.unibo.collections;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

/**
 * Example performance measuring. Use this class as working example of how to
 * measure the time necessary to perform operations on data structures.
 */
public final class TestPerformance {

    private static final int ELEMS = 1_000_000;

    private TestPerformance() { }

    /**
     * @param s
     *            ignored
     */
    public static void main(final String... s) {
        /*
         * Set up the data structures
         */
        final Set<String> set = new TreeSet<>();
        /*
         * Prepare a variable for measuring time
         */
        long time = System.nanoTime();
        /*
         * Run the benchmark
         */
        for (int i = 1; i <= ELEMS; i++) {
            set.add(Integer.toString(i));
        }
        /*
         * Compute the time and print result
         */
        time = System.nanoTime() - time;
        final var millis = TimeUnit.NANOSECONDS.toMillis(time);
        System.out.println(// NOPMD
            "Converting "
                + set.size()
                + " ints to String and inserting them in a Set took "
                + time
                + "ns ("
                + millis
                + "ms)"
        );
    }
}
