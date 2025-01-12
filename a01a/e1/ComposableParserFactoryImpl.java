package a01a.e1;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class ComposableParserFactoryImpl implements ComposableParserFactory {

    private <X> ComposableParser<X> ofIteratorsStream(final Stream<Iterator<X>> iteratorsStream) {
        return new ComposableParser<X>() {
            List<Iterator<X>> iterators = iteratorsStream.toList();
            @Override
            public boolean parse(final X t) {
                iterators = iterators.stream()
                    .filter(i -> i.hasNext() && i.next().equals(t))
                    .toList();
                return iterators.size() > 0;
            }
            @Override
            public boolean end() {
                return iterators.size() == 0 || iterators.stream().anyMatch(i -> !i.hasNext());
            }
        };
    }

    @Override
    public <X> ComposableParser<X> empty() {
        return ofIteratorsStream(Stream.empty());
    }

    @Override
    public <X> ComposableParser<X> one(X x) {
        return ofIteratorsStream(Stream.of(Stream.of(x).iterator()));
    }

    @Override
    public <X> ComposableParser<X> fromList(final List<X> list) {
        return ofIteratorsStream(Stream.of(list.iterator()));
    }

    @Override
    public <X> ComposableParser<X> fromAnyList(final Set<List<X>> input) {
        return ofIteratorsStream(input.stream().map(List::iterator));
    }

    @Override
    public <X> ComposableParser<X> seq(final ComposableParser<X> parser, final List<X> list) {
        final var listParser = fromList(list);
        return new ComposableParser<X>() {
            @Override
            public boolean parse(final X t) {
                return parser.parse(t) || listParser.parse(t);
            }
            @Override
            public boolean end() {
                return parser.end() && listParser.end();
            }
        };
    }

    @Override
    public <X> ComposableParser<X> or(final ComposableParser<X> p1, final ComposableParser<X> p2) {
        return new ComposableParser<X>() {
            @Override
            public boolean parse(final X t) {
                final var result1 = p1.parse(t);
                final var result2 = p2.parse(t);
                return result1 || result2;
            }
            @Override
            public boolean end() {
                return p1.end() && p2.end();
            }
        };
    }

}
