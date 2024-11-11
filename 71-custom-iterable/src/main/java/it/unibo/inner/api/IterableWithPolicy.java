package it.unibo.inner.api;

/**
 * This interface represents an iterable collection with a policy.
 * The policy is used to filter out the elements during the iteration.
 */
public interface IterableWithPolicy<T> extends Iterable<T> {
    /**
     * Configures the policy used to filter out the elements to iterate.
     * @param filter the predicate used to filter out the elements in the collection during the iteration.
     */
    void setIterationPolicy(Predicate<T> filter);
}
