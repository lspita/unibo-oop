package a03b.sol1;

import java.util.LinkedList;
import java.util.List;

// Soluzione con generazione di iteratori imperativi
public class InfiniteIteratorHelpersImpl2 implements InfiniteIteratorsHelpers {

	@Override
	public <X> InfiniteIterator<X> of(X x) {
		return () -> x;
	}

	@Override
	public <X> InfiniteIterator<X> cyclic(List<X> l) {
		return new InfiniteIterator<>() {
			LinkedList<X> ll = new LinkedList<>(l);
			@Override
			public X nextElement() {
				var e = ll.removeFirst();
				ll.addLast(e);
				return e;
			}			
		};
	}
	
	@Override
	public InfiniteIterator<Integer> incrementing(int start, int increment) {
		return new InfiniteIterator<>() {
			int state = start;
			@Override
			public Integer nextElement() {
				var itemp = state;
				state = state + increment;
				return itemp;
			}			
		};
	}

	@Override
	public <X> InfiniteIterator<X> alternating(InfiniteIterator<X> input1, InfiniteIterator<X> input2) {
		return new InfiniteIterator<X>() {
			private InfiniteIterator<X> i1 = input1;
			private InfiniteIterator<X> i2 = input2;
			@Override
			public X nextElement() {
				var itemp = i1;
				i1 = i2;
				i2 = itemp;
				return i2.nextElement();
			}			
		};
	}

	@Override
	public <X> InfiniteIterator<List<X>> window(InfiniteIterator<X> s, int n) {
		return new InfiniteIterator<List<X>>() {
			LinkedList<X> cache = new LinkedList<>();
			@Override
			public List<X> nextElement() {
				cache.addLast(s.nextElement());
				while (cache.size()<n) {
					cache.addLast(s.nextElement());
				}
				return new LinkedList<>(cache.subList(cache.size()-n, cache.size()));
			}			
		};
	}

}
