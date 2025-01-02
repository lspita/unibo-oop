package a02a.e1;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class ListBuilderFactoryImpl implements ListBuilderFactory {

    private record ListBuilderImpl<T>(List<T> elements) implements ListBuilder<T> {

        private List<T> concatLists(final List<T> l1, final List<T> l2) {
            return Stream.concat(l1.stream(), l2.stream()).toList();
        }

        @Override
        public ListBuilder<T> add(final List<T> list) {
            return new ListBuilderImpl<>(concatLists(elements, list));
        }

        @Override
        public ListBuilder<T> concat(final ListBuilder<T> lb) {
            return this.add(lb.build());
        }

        @Override
        public ListBuilder<T> replaceAll(final T t, final ListBuilder<T> lb) {
            return new ListBuilderImpl<>(
                elements.stream()
                    .flatMap(e -> e.equals(t) ? lb.build().stream() : Stream.of(e))
                    .toList()
            );
        }

        @Override
        public ListBuilder<T> reverse() {
            final var reverseElements = new LinkedList<>(elements);
            Collections.reverse(reverseElements);
            return new ListBuilderImpl<>(reverseElements);
        }

        @Override
        public List<T> build() {
            return List.copyOf(elements);
        }

    }

    @Override
    public <T> ListBuilder<T> empty() {
        return this.fromList(Collections.emptyList());
    }

    @Override
    public <T> ListBuilder<T> fromElement(T t) {
        return this.fromList(List.of(t));
    }

    @Override
    public <T> ListBuilder<T> fromList(List<T> list) {
        return new ListBuilderImpl<>(list);
    }

    @Override
    public <T> ListBuilder<T> join(T start, T stop, List<ListBuilder<T>> builderList) {
        return this.fromList(Stream.concat(
            Stream.of(start), 
            Stream.concat(
                builderList.stream().flatMap(lb -> lb.build().stream()),
                Stream.of(stop)
            )
        ).toList());
    }

}
