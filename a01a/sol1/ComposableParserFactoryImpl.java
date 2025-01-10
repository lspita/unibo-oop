package a01a.sol1;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import static java.util.stream.Collectors.*;
import java.util.stream.Stream;

public class ComposableParserFactoryImpl implements ComposableParserFactory {

    private static class ComposableParserImpl<T> implements ComposableParser<T>{
        private Set<Iterator<T>> iterators;
        public ComposableParserImpl(Set<Iterator<T>> iterators){
            this.iterators = Set.copyOf(iterators);
        }
        @Override
        public boolean parse(T t) { 
            iterators = iterators   // at each parsing, less iterators remain!
                    .stream()
                    .filter(Iterator::hasNext)
                    .filter(it -> it.next().equals(t))
                    .collect(toSet());
            return !iterators.isEmpty();
        }
        @Override
        public boolean end() {
            return iterators.stream().anyMatch(it -> !it.hasNext());
        }
    }

    private static <X> Stream<Iterator<X>> asStream(ComposableParser<X> parser){
        return ((ComposableParserImpl<X>)parser).iterators.stream();
    }

    @Override
    public <X> ComposableParser<X> empty() {
        return fromList(Collections.emptyList());
    }

    @Override
    public <X> ComposableParser<X> one(X x) {
        return fromList(List.of(x));
    }

    @Override
    public <X> ComposableParser<X> fromList(List<X> elements) {
        return fromAnyList(Set.of(elements));
    }

    @Override
    public <X> ComposableParser<X> fromAnyList(Set<List<X>> elements) {
        return new ComposableParserImpl<>(elements.stream().map(List::iterator).collect(toSet()));
    }

    @Override
    public <X> ComposableParser<X> seq(ComposableParser<X> p, List<X> elements){
        return new ComposableParserImpl<>(asStream(p).map(it -> seq(it, elements.stream())).collect(toSet()));
    }

    @Override
    public <X> ComposableParser<X> or(ComposableParser<X> p1, ComposableParser<X> p2) {
        return new ComposableParserImpl<>(Stream.concat(asStream(p1), asStream(p2)).collect(toSet()));
    }

    private <X> Iterator<X> seq(Iterator<X> i1, Stream<X> i2){
        return Stream.concat(Stream.iterate(i1, Iterator::hasNext, it -> it).map(Iterator::next), i2).iterator();
    }
}
