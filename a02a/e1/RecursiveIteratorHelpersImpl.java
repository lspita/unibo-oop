package a02a.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RecursiveIteratorHelpersImpl implements RecursiveIteratorHelpers {

    @Override
    public <X> RecursiveIterator<X> fromList(final List<X> list) {
        return list.size() == 0 ? null : new RecursiveIterator<X>() {
            @Override
            public X getElement() {
                return list.getFirst();
            }
            @Override
            public RecursiveIterator<X> next() {
                return fromList(list.subList(1, list.size()));
            }
        };
    }

    private <X> Stream<X> toStream(final RecursiveIterator<X> input) {
        return Stream.iterate(input, i -> i != null, i -> i.next()).map(RecursiveIterator::getElement);
    }

    private <X> List<X> toList(final RecursiveIterator<X> input) {
        return toStream(input).toList();
    }

    @Override
    public <X> List<X> toList(final RecursiveIterator<X> input, final int max) {
        return toStream(input).limit(max).toList();
    }

    private <X, Y> List<Pair<X, Y>> zipLists(final List<X> l1, final List<Y> l2) {
        return Stream.iterate(0, i -> i < Math.min(l1.size(), l2.size()), i -> i + 1)
            .map(i -> new Pair<>(l1.get(i), l2.get(i)))
            .toList();
    }

    @Override
    public <X, Y> RecursiveIterator<Pair<X, Y>> zip(final RecursiveIterator<X> first, final RecursiveIterator<Y> second) {
        return fromList(zipLists(toList(first), toList(second)));
    }

    @Override
    public <X> RecursiveIterator<Pair<X, Integer>> zipWithIndex(final RecursiveIterator<X> iterator) {
        return zip(iterator, fromList(Stream.iterate(0, i -> i < toList(iterator).size(), i -> i + 1).toList()));
    }

    @Override
    public <X> RecursiveIterator<X> alternate(final RecursiveIterator<X> first, final RecursiveIterator<X> second) {
        final var firstList = toList(first);
        final var secondList = toList(second);
        return fromList(
            Stream.iterate(0, i -> i < Math.max(firstList.size(), secondList.size()), i -> i + 1)
                .flatMap(i -> {
                    final var tmpList = new ArrayList<X>(2);
                    if (i < firstList.size()) {
                        tmpList.add(firstList.get(i));
                    }
                    if (i < secondList.size()) {
                        tmpList.add(secondList.get(i));
                    }
                    return tmpList.stream();
                })
                .toList()
        );
    }

}
