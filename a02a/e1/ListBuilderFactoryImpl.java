package a02a.e1;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class ListBuilderFactoryImpl implements ListBuilderFactory {

    private static <X> List<X> concatLists(final List<X> left, final List<X> right) {
        return Stream.concat(left.stream(), right.stream()).toList();
    }

    public record ListBuilderImpl<X>(List<X> elements) implements ListBuilder<X> {
        @Override
        public ListBuilder<X> add(final List<X> list) {
            return new ListBuilderImpl<>(concatLists(elements, list));
        }
        @Override
        public ListBuilder<X> concat(final ListBuilder<X> lb) {
            return add(lb.build());
        }
        @Override
        public ListBuilder<X> replaceAll(final X t, final ListBuilder<X> lb) {
            return new ListBuilderImpl<>(
                elements.stream()
                    .flatMap(x -> x.equals(t) ? lb.build().stream() : Stream.of(x))
                    .toList()
            );
        }
        @Override
        public ListBuilder<X> reverse() {
            return new ListBuilderImpl<>(elements.reversed());
        }
        @Override
        public List<X> build() {
            return Collections.unmodifiableList(elements);
        }
    }

    @Override
    public <T> ListBuilder<T> empty() {
        return fromList(Collections.emptyList());
    }

    @Override
    public <T> ListBuilder<T> fromElement(final T t) {
        return fromList(List.of(t));
    }

    @Override
    public <T> ListBuilder<T> fromList(final List<T> list) {
        return new ListBuilderImpl<>(list);
    }

    @Override
    public <T> ListBuilder<T> join(final T start, T stop, final List<ListBuilder<T>> builderList) {
        return builderList.stream()
            .reduce(fromElement(start), (b1, b2) -> b1.concat(b2))
            .concat(fromElement(stop));
    }

}
