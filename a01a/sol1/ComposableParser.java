package a01a.sol1;

import java.util.List;

/**
 * Models a parser of elements of type T, consuming one element at a time until the end is reached.
 * Such parsers are assumed to take a finite amount of elements, to be composed one another,
 * and possibly be non-deterministic, that is, the can consume different sequences.
 * For instance, one can build a parser accepting (10,20) or (10,20,30) or (20,40).
 * Auxiliary methods are provided as default methods.
 */
public interface ComposableParser<T> {

    /**
     * @param t
     * @return true whether it was correct for this parser to parse argument t, false otherwise
     */
    boolean parse(T t);

    /**
     * Closes parsing for this object.
     * @return true whether it was possible to end parsing, false otherwise.
     */
    boolean end();

    /**
     * @param list
     * @return whether it was possible to parse all elements in the the argument list, one by one
     */
    default boolean parseMany(List<T> list){
        return list.stream().allMatch(this::parse);
    }

    /**
     * @param list
     * @return whether it was possible to parse all elements in the the argument list, one by one,
     * and then finish parsing.
     */
    default boolean parseToEnd(List<T> list){
        return parseMany(list) && end();
    }

}
