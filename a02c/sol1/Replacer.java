package a02c.sol1;

import java.util.*;

/**
 * Models a "replacer", namely, an algorithm that searches certain occurrences (the first, the last, all of them, 
 * depending on implementation) of a term in a list, and for each of them replace such a term with 0,1, or a list of elements.
 * The call to replace method should have no side-effect.
 */
@FunctionalInterface
public interface Replacer<T> {

    /**
     * @param input
     * @param t, the element to be replaced
     * @return all solutions founds, corresponding to occurrences from left to right
     */
    List<List<T>> replace(List<T> input, T t);

}
