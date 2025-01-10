package a01c.sol1;

import java.util.List;

/**
 * Models a factory for iterators. The most important part of it is "windowing", namely, building an iteration
 * considering the next "n" element of the original iterator.
 */
public interface SimpleIteratorFactory {

    /**
     * @return a SimpleIterator over 0,1,2,3,...
     */
    SimpleIterator<Integer> naturals();

    /**
     * @param <X>
     * @param list, of elements e1,e2,...,en
     * @return a SimpleIterator over e1,e2,...,en,e1,e2,...,en,e1,...
     */
    <X> SimpleIterator<X> circularFromList(List<X> list);

    /**
     * @param <X>
     * @param size
     * @param simpleIterator
     * @return a SimpleIterator similar to argument simpleIterator, though it is over after size elements 
     */
    <X> SimpleIterator<X> cut(int size, SimpleIterator<X> simpleIterator);

    /**
     * @param <X>
     * @param simpleIterator, assuming it iterates over e1,e2,e3,....
     * @return a SimpleIterator over pairs <e1,e2>, <e2,e3>, <e3,e4>,...
     */
    <X> SimpleIterator<Pair<X, X>> window2(SimpleIterator<X> simpleIterator);

    /**
     * @param simpleIterator, assuming it iterates over e1,e2,e3,....
     * @return a SimpleIterator over e1+e2, e2+e3, e3+e4,...
     */
    SimpleIterator<Integer> sumPairs(SimpleIterator<Integer> simpleIterator);

    /**
     * @param <X>
     * @param windowSize, 
     * @param simpleIterator, assuming it iterates over e1,e2,e3,....
     * @return a SimpleIterator over lists [e1,e2,...], [e2,e3,...], [e3,e4,...] each with length windowSize
     */
    <X> SimpleIterator<List<X>> window(int windowSize, SimpleIterator<X> simpleIterator);
}
