package a06.sol1;

import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.*;
import java.util.stream.Stream;

public class ZipCombinerFactoryImpl implements ZipCombinerFactory {

    /**
     * @param <X>
     * @param <Y>
     * @param <Z>
     * @param extractor, as a function taking an element of first list, an iterator over second, and yielding
     * a Stream of results (typically just 1, sometimes 0)
     * @return a ZipCombiner created by the extractor
     */
    private static <X, Y, Z> ZipCombiner<X, Y, Z> generic(BiFunction<X, Iterator<Y>, Stream<Z>> extractor){
        return new ZipCombiner<X,Y,Z>() {

            @Override
            public List<Z> zipCombine(List<X> l1, List<Y> l2) {
                var it2 = l2.iterator();
                return l1.stream().flatMap(x -> extractor.apply(x, it2)).toList();
            }
        };
    }

    @Override
    public <X, Y> ZipCombiner<X, Y, Pair<X, Y>> classical() {
        return generic((x, it) -> Stream.of(new Pair<>(x, it.next())));
    }

    @Override
    public <X, Y, Z> ZipCombiner<X, Y, Z> mapFilter(Predicate<X> predicate, Function<Y, Z> mapper) {
        return generic((x, it) -> Stream.of(mapper.apply(it.next())).filter(v -> predicate.test(x)));
    }

    @Override
    public <X> ZipCombiner<Integer, X, List<X>> taker() {
        return generic((x, it) -> Stream.of(Stream.iterate(0, n -> n+1).limit(x).map(n -> it.next()).toList()));
    }

    @Override
    public <X> ZipCombiner<X, Integer, Pair<X, Integer>> countUntilZero() {
        return generic((x, it) -> Stream.of(new Pair<>(x, (int)Stream.iterate(it.next(), n -> n != 0, n -> it.next()).count())));
    }



   

}
