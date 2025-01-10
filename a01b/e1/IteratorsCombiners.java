package a01b.e1;

import java.util.Iterator;
import java.util.function.BinaryOperator;

/**
 * An interface for creating iterators combining other iterators
 */
public interface IteratorsCombiners {

    /**
     * @param <X>
     * @param i1
     * @param i2
     * @return an iterator giving one element from i1, and one from i2, alternatively,
     * if one iterator is over, it produces all elements of the other one.
     */
    <X> Iterator<X> alternate(Iterator<X> i1, Iterator<X> i2);

    /**
     * @param <X>
     * @param i1
     * @param i2
     * @return an iterator giving all elements of i1, and then all elements of i2
     */
    <X> Iterator<X> seq(Iterator<X> i1, Iterator<X> i2);

    /**
     * @param <X>
     * @param i1
     * @param i2
     * @param operator
     * @return an iterator the produces each time an element obtained combinig with operator the next element
     * of i1, and the next of i2. If an input iterator is over, nothing more is produced.
     */
    <X> Iterator<X> map2(Iterator<X> i1, Iterator<X> i2, BinaryOperator<X> operator);

    /**
     * @param <X>
     * @param <Y>
     * @param <Z>
     * @param i1
     * @param i2
     * @return an iterator the produces each time a pair with operator the next element
     * of i1, and the next of i2. If an input iterator is over, nothing more is produced.
     */
    <X, Y, Z> Iterator<Pair<X, Y>> zip(Iterator<X> i1, Iterator<Y> i2);
}
