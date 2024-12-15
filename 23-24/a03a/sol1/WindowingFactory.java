package a03a.sol1;

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
     * @return the last "n" elements of the input are used to return a list of them
     */
    <X> Windowing<X, List<X>> lastN(int n);

    /**
     * @param <X>
     * @param n
     * @return the last elements of the input whose sum is sufficient to be at least n are used to return a list of them
     */
    Windowing<Integer, List<Integer>> lastWhoseSumIsAtLeast(int n);

}
