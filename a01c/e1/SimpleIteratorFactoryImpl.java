package a01c.e1;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class SimpleIteratorFactoryImpl implements SimpleIteratorFactory {
    
    private <X> SimpleIterator<X> ofStream(final Stream<X> stream) {
        return stream.iterator()::next;
    }

    private <X> Stream<X> toStream(final SimpleIterator<X> iterator) {
        return Stream.generate(iterator::next);
    }

    @Override
    public SimpleIterator<Integer> naturals() {
        return ofStream(Stream.iterate(0, i -> i + 1));
    }

    @Override
    public <X> SimpleIterator<X> circularFromList(final List<X> list) {
        return ofStream(Stream.iterate(0, i -> (i + 1) % list.size()).map(list::get));
    }

    @Override
    public <X> SimpleIterator<X> cut(final int size, final SimpleIterator<X> simpleIterator) {
        return ofStream(toStream(simpleIterator).limit(size));
    }

    @Override
    public <X> SimpleIterator<Pair<X, X>> window2(final SimpleIterator<X> simpleIterator) {
        return ofStream(toStream(window(2, simpleIterator)).map(l -> new Pair<>(l.getFirst(), l.getLast())));
    }

    @Override
    public SimpleIterator<Integer> sumPairs(final SimpleIterator<Integer> simpleIterator) {
        return ofStream(toStream(window2(simpleIterator)).map(p -> p.get1() + p.get2()));
    }

    @Override
    public <X> SimpleIterator<List<X>> window(final int windowSize, final SimpleIterator<X> simpleIterator) {
        final var window = new LinkedList<X>();
        return ofStream(
            toStream(simpleIterator)
                .map(x -> {
                    window.add(x);
                    if (window.size() > windowSize) {
                        window.removeFirst();
                    }
                    return Optional.ofNullable(window.size() < windowSize ? null : List.copyOf(window));
                }).filter(Optional::isPresent)
                .map(Optional::get)
        );
    }

}
