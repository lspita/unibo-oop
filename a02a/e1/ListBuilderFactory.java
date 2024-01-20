package a02a.e1;

import java.util.*;

/**
 * Models a factory for list builders
 */
public interface ListBuilderFactory {

    /**
     * @param <T>
     * @return a builder representing the empty list
     */
    <T> ListBuilder<T> empty();

    /**
     * @param <T>
     * @param t
     * @return a builder representing the list with only one element @t
     */
    <T> ListBuilder<T> fromElement(T t);

    /**
     * @param <T>
     * @param list
     * @return a builder representing @list
     */
    <T> ListBuilder<T> fromList(List<T> list);

    /**
     * @param <T>
     * @param start
     * @param stop
     * @param builderList
     * @return a builder representing concatenation of @start, the lists of each builder in @builderList, and @stop
     */
    <T> ListBuilder<T> join(T start,  T stop, List<ListBuilder<T>> builderList);
}
