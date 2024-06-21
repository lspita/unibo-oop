package a04.e1;

import java.util.*;

/**
 * An interface to model a stateless funcionality to extract information from an input list, 
 * according to the specific implementation.
 * All implementation have in common the fact that:
 * - the list (with elements of type A) is considered from a certain begin to a certain end
 * - each element contribute in updating an output value (of type B), properly inizialized.
 */
public interface ListExtractor<A, B> {
	
	/**
	 * @param list
	 * @return an output result from extracting information from a certain subsequence in list
	 */
	B extract(List<A> list);
}
