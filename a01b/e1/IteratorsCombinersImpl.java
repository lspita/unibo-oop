package a01b.e1;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class IteratorsCombinersImpl implements IteratorsCombiners {

    @Override
    public <X> Iterator<X> alternate(final Iterator<X> i1, final Iterator<X> i2) {
        return Stream.iterate(0, i -> i1.hasNext() || i2.hasNext(), i -> i + 1)
            .map(
                i -> i1.hasNext() && i2.hasNext() ?
                    (i % 2 == 0 ? i1.next() : i2.next()) :
                    (i1.hasNext() ? i1.next() : i2.next())
            ).iterator();
    }
    
    private <X> Stream<X> iteratorStream(final Iterator<X> iterator) {
        return Stream.iterate(iterator, i -> i.hasNext(), i -> i).map(Iterator::next);
    }

    @Override
    public <X> Iterator<X> seq(final Iterator<X> i1, final Iterator<X> i2) {
        return Stream.concat(iteratorStream(i1), iteratorStream(i2)).iterator();
    }

    private <X, Y, Z> Iterator<Z> map(final Iterator<X> i1, final Iterator<Y> i2, final BiFunction<X, Y, Z> operator) {
        return Stream.iterate(0, i -> i1.hasNext() && i2.hasNext(), i -> i + 1)
            .map(i -> operator.apply(i1.next(), i2.next()))
            .iterator();
    }

    @Override
    public <X> Iterator<X> map2(final Iterator<X> i1, final Iterator<X> i2, final BinaryOperator<X> operator) {
        return map(i1, i2, operator);
    }

    @Override
    public <X, Y, Z> Iterator<Pair<X, Y>> zip(final Iterator<X> i1, final Iterator<Y> i2) {
        return map(i1, i2, (x, y) -> new Pair<>(x, y));
    }

}
