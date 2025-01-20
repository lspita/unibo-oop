package a03b.e1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class InfiniteIteratorHelpersImpl implements InfiniteIteratorsHelpers {

    private <X> InfiniteIterator<X> ofIterator(final Iterator<X> iterator) {
        return iterator::next;
    }

    private <X> InfiniteIterator<X> ofStream(final Stream<X> stream) {
        return ofIterator(stream.iterator());
    }

    @Override
    public <X> InfiniteIterator<X> of(final X x) {
        return ofStream(Stream.generate(() -> x));
    }

    @Override
    public <X> InfiniteIterator<X> cyclic(final List<X> l) {
        return ofStream(Stream.iterate(0, i -> (i + 1) % l.size()).map(l::get));
    }

    @Override
    public InfiniteIterator<Integer> incrementing(final int start, final int increment) {
        return ofStream(Stream.iterate(start, i -> i + increment));
    }

    @Override
    public <X> InfiniteIterator<X> alternating(final InfiniteIterator<X> i, final InfiniteIterator<X> j) {
        return ofStream(
            Stream.iterate(0, x -> x + 1)
                .map(x -> x % 2 == 0 ? i.nextElement() : j.nextElement())
        );
    }

    @Override
    public <X> InfiniteIterator<List<X>> window(final InfiniteIterator<X> i, final int n) {
        final var window = new LinkedList<X>();
        return ofStream(
            Stream.generate(i::nextElement)
                .map(x -> {
                    window.addLast(x);
                    if (window.size() > n) {
                        window.removeFirst();
                    }
                    return List.copyOf(window);
                })
                .skip(n - 1)
        );
    }

}
