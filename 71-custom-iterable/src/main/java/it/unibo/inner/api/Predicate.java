package it.unibo.inner.api;

/**
 * This interface represents a predicate.
 * A predicate is a function that takes an element and returns a boolean.
 * @param <T> the type of the element to test.
 */
public interface Predicate<T> {
    /**
     * Tests the given element.
     * @param elem the element to test.
     * @return true if the element satisfies the predicate, false otherwise.
     */
    boolean test(T elem);
}
