package a01a.e1;

import java.util.List;
import java.util.Set;

/**
 * Models a factory for ComposalParsers. The most important part of it is that such parsers can be composed
 * to form more complex parsers.
 * To do so, you can assume thar this factory is the only of creating ComposableParsers. 
 */
public interface ComposableParserFactory {

    /**
     * @param <X>
     * @return a parser consuming the empty list of tokens
     */
    <X> ComposableParser<X> empty();
    
    /**
     * @param <X>
     * @param x
     * @return a parser consuming only one element x of type X
     */
    <X> ComposableParser<X> one(X x);
    
    /**
     * @param <X>
     * @param list
     * @return a parser consuming the elements in list, one by one
     */
    <X> ComposableParser<X> fromList(List<X> list);

    /**
     * @param <X>
     * @param input
     * @return a parser consuming any list of elements included in input
     */
    <X> ComposableParser<X> fromAnyList(Set<List<X>> input);

    /**
     * @param <X>
     * @param parser
     * @param list
     * @return a parser consuming any sequence of elements that would be consumed by argument parser, 
     * followed by the elements in argument list
     * E.g., if parser can consume (10,20,30), and list is [40,50], then the output parser
     * can consume (10,20,30,40,50)
     */
    <X> ComposableParser<X> seq(ComposableParser<X> parser, List<X> list);

    /**
     * @param <X>
     * @param p1
     * @param p2
     * @return a parser that can consume the sequences consumed by p1 AND the sequences consumed 
     * by p2
     */
    <X> ComposableParser<X> or(ComposableParser<X> p1, ComposableParser<X> p2);
}
