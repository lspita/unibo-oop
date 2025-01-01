package a04.sol1;

import java.util.List;
import java.util.Optional;
import java.util.function.*;

public interface ListExtractorFactory {
	
	/**
	 * @param <X>
	 * @return an extractor of the head of the list as an optional (empty if the list was empty)
	 */
	<X> ListExtractor<X, Optional<X>> head();

	/**
	 * @param <X>
	 * @param <Y>
	 * @param mapper
	 * @param stopCondition
	 * @return a list obtained from extracting all elements of the input list from the beginning until @stopCondition is satisfied,
	 * mapping each element to the output by @mapper
	 */
	<X, Y> ListExtractor<X, List<Y>> collectUntil(Function<X,Y> mapper, Predicate<X> stopCondition);

	/**
	 * @param <X>
	 * @param startCondition
	 * @return a list obtained from extracting all elements of the input list from when @startCondition is satisified to the end; 
	 * say input elements to be considered are (a1,a2,..,an), output is ((a1), (a1,a2), (a1,a2,a3),... (a1,a2,...,an))
	 	 */
	<X> ListExtractor<X, List<List<X>>> scanFrom(Predicate<X> startCondition);

	/**
	 * @param <X>
	 * @param x
	 * @return the numbers of consecutive x found in the list from its first occurrence
	 */
	<X> ListExtractor<X, Integer> countConsecutive(X x);
}
