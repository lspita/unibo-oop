package a06.e1;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A factory of ZipCombiners. They all have in common the fact the each element of the first list is used to 
 * create one element of the output (or possibly none): such an element of the output may uses 0,1, or many elements of 
 * the second list, iteratively. Possibly, not all elements of the second lists are used, especially if this list is too long;
 * in fact it is always assumed that there are sufficient elements in l2.
 * The test cases provided explain well what each ZipCombiner does.
 */
public interface ZipCombinerFactory {

    /**
     * @param <X>
     * @param <Y>
     * @return a combiner that takes one element of l1, one of element of l2, and produces a pair of them.
     */
    <X,Y> ZipCombiner<X,Y,Pair<X,Y>> classical();

    /**
     * @param <X>
     * @param <Y>
     * @param <Z>
     * @param predicate
     * @param mapper
     * @return a combiner that takes one element of l1, and produces an output only if this passes the predicate test.
     * If it passes it: 
     * - the output element is obtained from the next element of l2, to which the mapper is applied.
     * Otherwise:
     * - the element of l2 is discarded and no output element is provided.
     */
    <X,Y,Z> ZipCombiner<X,Y,Z> mapFilter(Predicate<X> predicate, Function<Y,Z> mapper);

    /**
     * @param <Y>
     * @return a combiner that takes a number from l1, say this is @n, and produces the list of the next @n elements from l2.
     */
    <Y> ZipCombiner<Integer,Y,List<Y>> taker();

    /**
     * @param <X>
     * @return a combiner that takes an element of l1, and creates a pair with it and with the number of next consecutive
     * elements of l2 which are not 0.
     */
    <X> ZipCombiner<X,Integer,Pair<X, Integer>> countUntilZero();
}
