package a03a.e1;

import java.util.*;

/**
 * It models a transformer for sequences, which takes the input sequence one at a time, by a `process` method,
 * and gives the output iteratively by return values: each output symbol is produced based on the
 * last "n" elements provided in input (called a window), where such "n" depends on the implementation and could be dynamic. 
 * If there's not enough input to produce a result, Optional.empty is returned.
 * For instance, a specific implementation may output the sum of the last 3 elements provided in input.
 */

public interface Windowing<X, Y> {

    /**
     * @param x is the input consumed
     * @return the result, based on some of the last elements received in input (empty if they are not enough)
     */
    Optional<Y> process(X x);
}
