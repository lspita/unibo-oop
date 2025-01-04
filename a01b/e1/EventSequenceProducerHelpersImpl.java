package a01b.e1;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class EventSequenceProducerHelpersImpl implements EventSequenceProducerHelpers {

    @Override
    public <E> EventSequenceProducer<E> fromIterator(final Iterator<Pair<Double, E>> iterator) {
        return () -> iterator.next();
    }

    private <E> Stream<Pair<Double, E>> toStream(final EventSequenceProducer<E> sequence) {
        try {
            return Stream.concat(Stream.of(sequence.getNext()), toStream(sequence));
        } catch (NoSuchElementException e) {
            return Stream.of();
        }
    }

    @Override
    public <E> List<E> window(final EventSequenceProducer<E> sequence, final double fromTime, final double toTime) {
        return toStream(sequence)
            .filter(event -> event.get1() > fromTime && event.get1() < toTime)
            .map(Pair::get2)
            .toList();
    }

    @Override
    public <E> Iterable<E> asEventContentIterable(final EventSequenceProducer<E> sequence) {
        return toStream(sequence).map(Pair::get2).toList();
    }

    @Override
    public <E> Optional<Pair<Double, E>> nextAt(final EventSequenceProducer<E> sequence, final double time) {
        return toStream(sequence)
            .filter(pair -> pair.get1() > time)
            .findFirst();
    }

    @Override
    public <E> EventSequenceProducer<E> filter(final EventSequenceProducer<E> sequence, final Predicate<E> predicate) {
        return fromIterator(toStream(sequence).filter(pair -> predicate.test(pair.get2())).iterator());
    }

}
