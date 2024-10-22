package it.unibo.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static it.unibo.collections.test.Assertions.assertContentEqualsInAnyOrder;
import static it.unibo.collections.test.Assertions.assertTrue;

/**
 * Example class using {@link java.util.Collection}.
 */
public final class UseCollection {

    private UseCollection() {
    }

    /**
     * @param args
     *            unused
     */
    public static void main(final String[] args) {
        /*
         * Unless you need to perform specific operations, always use the most
         * generic interface.
         * If we use `var` instead of specifying Collection<Integer> what type
         * will `coll` have?
         */
        final Collection<Integer> coll = new ArrayList<>();
        coll.addAll(List.of(1, 3, 5, 7, 9, 11)); // var-args
        // Add some elements
        coll.add(13);
        coll.add(15);
        coll.add(15);
        // Remove an element
        coll.remove(7);
        assertContentEqualsInAnyOrder(List.of(1, 3, 5, 9, 11, 13, 15, 15), coll);
        // Remove multiple elements at once
        coll.removeAll(List.of(11, 13, 15));
        assertContentEqualsInAnyOrder(List.of(1, 3, 5, 9), coll);
        // Preserve only the elements available in the intersection
        coll.retainAll(List.of(1, 2, 3, 4, 5));
        assertContentEqualsInAnyOrder(List.of(1, 3, 5), coll);
        assertTrue(coll.contains(3));
    }
}
