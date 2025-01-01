package a02c.sol1;

import java.util.*;

/**
 * Models a factory for replacers: they are actually variations of a common pattern...
 */
public interface ReplacersFactory {

    /**
     * @param <T>
     * @return a replacer that looks for "no occurrence", that is, always giving no solution
     */
    <T> Replacer<T> noReplacement();

    /**
     * @param <T>
     * @return a replacer that looks for the first occurrence of an element `e`, and replaces it with (e,e)
     */
    <T> Replacer<T> duplicateFirst();

    /**
     * @param <T>
     * @param target
     * @return a replacer that looks for the last occurrence of an element `e`, and replaces it with @target
     */
    <T> Replacer<T> translateLastWith(List<T> target);

    /**
     * @param <T>
     * @return a replacer that looks for all occurrence of an element `e`, and for each it creates a solution where `e` is removed
     */
    <T> Replacer<T> removeEach();

    /**
     * @param <T>
     * @param sequence
     * @return a replacer that looks for occurrence of an element `e`, and iteratively replaces it with an element from sequence.
     * Hence it gives at most N solutions where N is the size of sequence.
     * See the Test.
     */
    <T> Replacer<T> replaceEachFromSequence(List<T> sequence);

}
