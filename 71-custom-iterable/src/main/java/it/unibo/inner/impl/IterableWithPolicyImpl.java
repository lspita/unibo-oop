package it.unibo.inner.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

/**
 * Basic implementation of the {@link IterableWithPolicy} interface.
 */
public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {
    private final List<T> elements;
    private Predicate<T> filter;

    /**
     * Create new instance from given elements.
     * 
     * @param elements from which to create the instance
     */
    public IterableWithPolicyImpl(final T[] elements) {
        this(elements, new Predicate<T>() {
            /**
             * {@inheritDoc}
             * <p>
             * Every element passes the test.
             * </p>
             */
            @Override
            public boolean test(T elem) {
                return true;
            }

        });
    }

    /**
     * Create new instance from elements with a filter.
     * 
     * @param elements from which to create the instance
     * @param filter   to use on the elements
     */
    public IterableWithPolicyImpl(final T[] elements, final Predicate<T> filter) {
        this.elements = new ArrayList<>(Arrays.asList(elements));
        this.setIterationPolicy(filter);
    }

    class PredicateIterator implements Iterator<T> {
        private int currentIndex;

        public PredicateIterator() {
            this.currentIndex = 0;
        }

        /**
         * {@inheritDoc}
         * <p>
         * {@code false} is returned even if there are still elements but no one can
         * satisfy the filter.
         * </p>
         */
        @Override
        public boolean hasNext() {
            while (this.currentIndex < elements.size()) {
                final T candidate = elements.get(this.currentIndex);
                if (filter.test(candidate)) {
                    return true;
                }
                this.currentIndex++;
            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T next() {
            if (this.hasNext()) {
                return elements.get(this.currentIndex++);
            }
            throw new NoSuchElementException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<T> iterator() {
        return new PredicateIterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        this.filter = filter;
    }
}
