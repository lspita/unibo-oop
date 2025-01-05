package a06.e1;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FluentParserFactoryImpl implements FluentParserFactory {

    private <X> FluentParser<X> ofIterator(final Iterator<X> iterator) {
        return new FluentParser<X>() {
            @Override
            public FluentParser<X> accept(final X value) {
                if (iterator.hasNext() && value.equals(iterator.next())) {
                    return ofIterator(iterator);
                }
                throw new IllegalStateException();
            }
        };
    }

    private <X> FluentParser<X> ofStream(final Stream<X> stream) {
        return ofIterator(stream.iterator());
    }

    private Stream<Integer> naturalsStream() {
        return Stream.iterate(0, i -> i + 1);
    }

    @Override
    public FluentParser<Integer> naturals() {
        return ofStream(naturalsStream());
    }
    
    private Stream<List<Integer>> incrementalNaturalsStream() {
        return naturalsStream().map(i -> naturalsStream().limit(i).toList());
    }

    @Override
    public FluentParser<List<Integer>> incrementalNaturalLists() {
        return ofStream(incrementalNaturalsStream());
    }

    private Stream<Integer> repetitiveIncrementalNaturalsStream() {
        return incrementalNaturalsStream().flatMap(l -> l.stream());
    }

    @Override
    public FluentParser<Integer> repetitiveIncrementalNaturals() {
        return ofStream(repetitiveIncrementalNaturalsStream());
    }

    private Stream<String> repetitiveIncrementalStringsStream(final String s) {
        return repetitiveIncrementalNaturalsStream().map(i -> String.join("", Collections.nCopies(i + 1, s)));
    }

    @Override
    public FluentParser<String> repetitiveIncrementalStrings(final String s) {
        return ofStream(repetitiveIncrementalStringsStream(s));
    }

    private Integer transformNTimes(final int i0, final UnaryOperator<Integer> op, final int n) {
        var result = i0;
        for (int i = 0; i < n; i++) {
            result = op.apply(result);
        }
        return result;
    }

    @Override
    public FluentParser<Pair<Integer, List<String>>> incrementalPairs(final int i0, final UnaryOperator<Integer> op, final String s) {
        return ofStream(incrementalNaturalsStream().map(l -> {
            final var n = transformNTimes(i0, op, l.size());
            return new Pair<>(n, IntStream.range(0, n).mapToObj(i -> s).toList());
        }));
    }

}
