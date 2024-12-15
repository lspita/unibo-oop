package a03a.e1;

import java.util.List;

/**
 * Models a factory for windowings
 */
public interface WindowingFactory {

    /**
     * @param <X>
     * @return the windowing in which each input gets transferred in out
     */
    <X> Windowing<X, X> trivial();

    /**
     * @param <X>
     * @return the windowing in which the last two elements of the input become a pair
     */
    <X> Windowing<X, Pair<X, X>> pairing();

    /**
     * @return the windowing in which the last four elements of the input are used to return their sum
     */
    Windowing<Integer, Integer> sumLastFour();

    /**
     * @param <X>
     * @param n
     * @return the windowing in which the last "n" elements of the input are used to return the list of them
     */
    <X> Windowing<X, List<X>> lastN(int n);

    /**
     * @param <X>
     * @param n
     * @return the windowing in which the last elements of the input whose sum is at least n are used to return the list of them
     */
    Windowing<Integer, List<Integer>> lastWhoseSumIsAtLeast(int n);

}
