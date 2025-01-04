package a02b.e1;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class CursorHelpersImpl implements CursorHelpers {

    private <X> Cursor<X> ofIterator(final Iterator<X> iterator) {
        return new Cursor<X>() {
            X current = iterator.next();
            @Override
            public X getElement() {
                return current;
            }
            @Override
            public boolean advance() {
                final var hasNext = iterator.hasNext();
                if (hasNext) {
                    current = iterator.next();
                }
                return hasNext;
            }
        };
    }

    private <X> Cursor<X> ofStream(final Stream<X> stream) {
        return ofIterator(stream.iterator());
    }
    
    private <X> Stream<X> toStream(final Cursor<X> input) {
        return Stream.concat(Stream.of(input), Stream.iterate(input, i -> i.advance(), i -> i))
            .map(Cursor::getElement);
    }

    @Override
    public <X> Cursor<X> fromNonEmptyList(final List<X> list) {
        return ofIterator(list.iterator());
    }

    @Override
    public Cursor<Integer> naturals() {
        return ofStream(Stream.iterate(0, i -> i + 1));
    }

    @Override
    public <X> Cursor<X> take(final Cursor<X> input, final int max) {
        return ofStream(toStream(input).limit(max));
    }

    @Override
    public <X> void forEach(final Cursor<X> input, final Consumer<X> consumer) {
        toStream(input).forEach(consumer);
    }

    @Override
    public <X> List<X> toList(final Cursor<X> input, final int max) {
        return toStream(take(input, max)).toList();
    }

}
