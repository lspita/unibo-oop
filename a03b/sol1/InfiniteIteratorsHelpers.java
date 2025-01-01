package a03b.sol1;

import java.util.List;

/**
 * Utilities for working with infinite iterators, including factories and combinators
 */
public interface InfiniteIteratorsHelpers {
	
	/**
	 * @param x
	 * @return an iterator always giving value x
	 */
	<X> InfiniteIterator<X> of(X x);
	
	/**
	 * @param l
	 * @return an iterator cyclically giving all elements in l (for example, if l is [1,2,3]
	 * it gives the iterator representing [1,2,3,1,2,3,1,2,3,1,2,3,...]
	 */
	<X> InfiniteIterator<X> cyclic(List<X> l);
	
	/**
	 * @param start
	 * @param increment
	 * @return the iterator representing [start, start+increment, start+increment+increment, ...]
	 */
	InfiniteIterator<Integer> incrementing(int start, int increment);

	/**
	 * @param i
	 * @param j
	 * @return given iterators representing [s0,s1,s2,s3,..] and [r0,r1,r2,r3,..] returns 
	 * the iterator representing [s0,r0,s1,r1,s2,r2,...]
	 */
	<X> InfiniteIterator<X> alternating(InfiniteIterator<X> i, InfiniteIterator<X> j);

	/**
	 * @param i
	 * @param n
	 * @return assume n=3, and given iterator representing [s0,s1,s2,s3,..] returns 
	 * the iterator representing [s0,s1,s2],[s1,s2,s3],[s2,s3,s4],...
	 */
	<X> InfiniteIterator<List<X>> window(InfiniteIterator<X> i, int n);
}
