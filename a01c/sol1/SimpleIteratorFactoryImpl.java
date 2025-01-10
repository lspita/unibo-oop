package a01c.sol1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class SimpleIteratorFactoryImpl implements SimpleIteratorFactory {

    private static <X> SimpleIterator<X> fromIterator(Iterator<X> iterator){
        return iterator::next;
    }

    private static <X> Stream<X> toStream(SimpleIterator<X> simpleIterator){
        return Stream.generate(simpleIterator::next);
    }

    @Override
    public SimpleIterator<Integer> naturals() {
        return fromIterator(Stream.iterate(0, i -> i+1).iterator());
    }

    @Override
    public <X> SimpleIterator<X> circularFromList(List<X> list) {
        return fromIterator(Stream.iterate(0, i -> i + 1).map(i -> i % list.size()).map(list::get).iterator());
    }

    @Override
    public <X> SimpleIterator<X> cut(int size, SimpleIterator<X> simpleIterator) {
        return fromIterator(toStream(simpleIterator).limit(size).iterator());
    }

    @Override
    public <X> SimpleIterator<Pair<X, X>> window2(SimpleIterator<X> iterator) {
        return fromIterator(toStream(window(2, iterator)).map(l -> new Pair<>(l.get(0), l.get(1))).iterator());
    }

    @Override
    public SimpleIterator<Integer> sumPairs(SimpleIterator<Integer> iterator) {
        return fromIterator(toStream(window2(iterator)).map(p -> p.get1() + p.get2()).iterator());
    }

    @Override
    public <X> SimpleIterator<List<X>> window(int windowSize, SimpleIterator<X> iterator) {
        final List<X> list = new LinkedList<>();
        return fromIterator(toStream(iterator)
                .map(e -> {
                    list.add(e); 
                    if (list.size() > windowSize){
                        list.remove(0);
                    }
                    return list;
                })
                .skip(windowSize-1)
                .iterator());
    }
}
