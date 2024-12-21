package a03b.sol1;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

// Soluzione con stream
public class InfiniteIteratorHelpersImpl implements InfiniteIteratorsHelpers {

	private <X> InfiniteIterator<X> ofStream(Stream<X> s){
		final Iterator<X> it = s.iterator();
		return () -> it.next();
	}

	@Override
	public <X> InfiniteIterator<X> of(X x) {
		return () -> x;
	}

	@Override
	public <X> InfiniteIterator<X> cyclic(List<X> x) {
		return ofStream(Stream.generate(x::stream).flatMap(e->e));
	}
	
	
	@Override
	public InfiniteIterator<Integer> incrementing(int start, int increment) {
		return ofStream(Stream.iterate(start, i -> i + increment));
	}

	@Override
	public <X> InfiniteIterator<X> alternating(InfiniteIterator<X> input1, InfiniteIterator<X> input2) {
		return ofStream(Stream
				.iterate(new Pair<>(input1, input2), p -> new Pair<>(p.get2(), p.get1()))
				.map(p -> p.get1().nextElement()));
	}

	@Override
	public <X> InfiniteIterator<List<X>> window(InfiniteIterator<X> i, int n) {
		return ofStream(Stream
				.iterate(Collections.<X>emptyList(), l -> Stream.concat(l.stream(), Stream.of(i.nextElement())).toList())
				.filter(l -> l.size() >= n)
				.map(l -> new LinkedList<>(l.subList(l.size()-n, l.size())))
		);
	}
}
