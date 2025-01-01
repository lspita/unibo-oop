package a02a.e1;

import java.util.*;

/**
 * Models a builder of a List (in the sense of pattern Builder). In particular, it should be assumed to be immutable, 
 * hence operations generally produce a new builder. Essentially, a builder wraps a list, return as we call method build().
 */
public interface ListBuilder<T> {

    /**
     * @param list
     * @return a new builder, also considering @list concatenated at the end 
     */
    ListBuilder<T> add(List<T> list);

    /**
     * @param lb
     * @return a new builder, also considering the list represented by @lb concatenated at the end 
     */
    ListBuilder<T> concat(ListBuilder<T> lb);

    /**
     * @param lb
     * @return a new builder, representing a list similar to this, but where each instance of element @t 
     * is replaced by the list represented by builder @lb
     */
    ListBuilder<T> replaceAll(T t, ListBuilder<T> lb);

    /**
     * @param lb
     * @return a new builder, representing the reversed list
     */
    ListBuilder<T> reverse();

    /**
     * @return the list this builder represents: this method could be called many times, with same result
     */
    List<T> build();
}
