package a01b.sol1;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class IteratorsCombinersFactory implements IteratorsCombiners {

    private <X, Y, Z> Iterator<Z> genericCombiner(Iterator<X> i1, Iterator<Y> i2,
            BiFunction<Iterator<X>, Iterator<Y>, List<Z>> function) {
        return new Iterator<>() {
            List<Z> cache = new LinkedList<>();

            @Override
            public boolean hasNext() {
                checkCache();
                return !cache.isEmpty();
            }

            private void checkCache() {
                if (cache.isEmpty()) {
                    cache.addAll(function.apply(i1,i2));
                }
            }

            @Override
            public Z next() {
                checkCache();
                return cache.remove(0);
            }
        };
    }

    @Override
    public <X> Iterator<X> alternate(Iterator<X> i1, Iterator<X> i2) {
        return genericCombiner(i1, i2, (it1, it2) -> Stream.of(it1, it2).filter(Iterator::hasNext).map(Iterator::next).toList());
    }
        
    @Override
    public <X> Iterator<X> seq(Iterator<X> i1, Iterator<X> i2) {
        return genericCombiner(i1, i2, (it1, it2) -> it1.hasNext() ? List.of(it1.next()) 
                : it2.hasNext() ? List.of(it2.next()) : List.of());
    }

    private <X, Y, Z> Iterator<Z> genericMap2(Iterator<X> i1, Iterator<Y> i2, BiFunction<X,Y,Z> function){
        return genericCombiner(i1, i2, (it1, it2) -> it1.hasNext() && it2.hasNext() ? 
                List.of(function.apply(it1.next(), i2.next())) : Collections.emptyList());
    }

    @Override
    public <X> Iterator<X> map2(Iterator<X> i1, Iterator<X> i2, BinaryOperator<X> operator){
        return genericMap2(i1, i2, operator);
    }

    @Override
    public <X, Y, Z> Iterator<Pair<X,Y>> zip(Iterator<X> i1, Iterator<Y> i2){
        return genericMap2(i1, i2, Pair::new);
    }
    

}
