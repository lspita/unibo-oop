package a03a.sol1;

import java.util.*;
import java.util.function.*;

public class WindowingFactoryImpl implements WindowingFactory {

    private <X,Y> Windowing<X,Y> genericWindowing(Predicate<List<X>> ready, Function<List<X>, Y> mapper){
        return new Windowing<X,Y>() {

            private final LinkedList<X> cache = new LinkedList<>();

            @Override
            public Optional<Y> process(X x) {
                cache.addLast(x);
                if (!ready.test(cache)){
                    return Optional.empty();
                }
                while (!cache.isEmpty() && ready.test(cache.subList(1, cache.size()))){
                    cache.removeFirst();
                }
                return Optional.of(cache).map(mapper);
            }
        };
    }

    @Override
    public <X> Windowing<X, X> trivial() {
        return genericWindowing(l -> l.size() >= 1, l -> l.get(0));
    }

    @Override
    public <X> Windowing<X, Pair<X, X>> pairing() {
        return genericWindowing(l -> l.size() >= 2, l -> new Pair<>(l.get(0), l.get(1)));
    }

    @Override
    public Windowing<Integer, Integer> sumLastFour() {
        return genericWindowing(l -> l.size() >= 4, l -> listSum(l));
    }

    @Override
    public <X> Windowing<X, List<X>> lastN(int n) {
        return genericWindowing(l -> l.size() >= n, l -> l);
    }


    @Override
    public Windowing<Integer, List<Integer>> lastWhoseSumIsAtLeast(int n) {
        return genericWindowing(l -> listSum(l) >= n, l -> l);
    }

    private int listSum(List<Integer> l) {
        return l.stream().mapToInt(i->i).sum();
    }
}
