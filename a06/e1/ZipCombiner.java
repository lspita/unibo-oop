package a06.e1;

import java.util.List;

/**
 * An interface to model a pure function that combines two lists to form another list, where typically
 * an element of the first list is combined with 0,1 or many element of the second list to form an element of the ouput,
 * iteratively. Both lists are intended to be read by left-to-right iteration.
 */
public interface ZipCombiner<X, Y, Z> {

    /**
     * @param l1
     * @param l2
     * @return a combination of the two lists, according to the specific implementation. There are no-side-effects.
     */
    List<Z> zipCombine(List<X> l1, List<Y> l2);

}
