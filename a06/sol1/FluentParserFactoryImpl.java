package a06.sol1;

import java.util.Iterator;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FluentParserFactoryImpl implements FluentParserFactory {

    private <T> FluentParser<T> fromIterator(Iterator<T> iterator){
        return t -> {
            if (iterator.hasNext() && iterator.next().equals(t)){ 
                return fromIterator(iterator);
            } else {
                throw new IllegalStateException();
            }
        };
    }

    @Override
    public FluentParser<Integer> naturals() {
        return fromIterator(IntStream.iterate(0, i->i+1).iterator());
    }

    @Override
    public FluentParser<List<Integer>> incrementalNaturalLists() {
        return fromIterator(Stream.iterate(0, i->i+1)
                .map(i -> IntStream.range(0,i).mapToObj(j->j).collect(Collectors.toList()))
                .iterator());
    }

    @Override
    public FluentParser<Integer> repetitiveIncrementalNaturals() {
        return fromIterator(Stream.iterate(0, i->i+1)
                .flatMap(i -> IntStream.range(0,i).mapToObj(j->j))
                .iterator());
    }

    @Override
    public FluentParser<String> repetitiveIncrementalStrings(String s) {
        return fromIterator(Stream.iterate(1, i->i+1) // 1,2,3,....
                .flatMap(i -> Stream.iterate(1, j->j+1).limit(i)) // 1,1,2,1,2,3,...
                .map(j -> Stream.generate(()->s).limit(j).reduce(String::concat).get())
                .iterator());
    }

    @Override
    public FluentParser<Pair<Integer, List<String>>> incrementalPairs(int i0, UnaryOperator<Integer> op, String s) {
        return fromIterator(Stream.iterate(0, i->i+2)
                .map(i->new Pair<>(i,Stream.generate(()->s).limit(i).collect(Collectors.toList())))
                .iterator()); 
    }
}