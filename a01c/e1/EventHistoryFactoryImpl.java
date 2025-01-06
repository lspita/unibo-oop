package a01c.e1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class EventHistoryFactoryImpl implements EventHistoryFactory {

    record Event<E>(double time, E content) {}

    private <E> EventHistory<E> ofStream(final Stream<Event<E>> stream) {
        final var iterator = stream.sorted((e1, e2) -> Double.compare(e1.time, e2.time)).iterator();
        final var history = new EventHistory<E>() {
            Optional<Event<E>> current = Optional.empty();
            @Override
            public double getTimeOfEvent() {
                return current.map(Event::time).get();
            }
            @Override
            public E getEventContent() {
                return current.map(Event::content).get();
            }
            @Override
            public boolean moveToNextEvent() {
                final var hasNext = iterator.hasNext();
                if (hasNext) {
                    current = Optional.of(iterator.next());
                }
                return hasNext;
            }
        };
        history.moveToNextEvent();
        return history;
    }

    @Override
    public <E> EventHistory<E> fromMap(final Map<Double, E> map) {
        return ofStream(
            map.entrySet().stream()
                .map(entry -> new Event<>(entry.getKey(), entry.getValue()))
        );
    }

    @Override
    public <E> EventHistory<E> fromIterators(final Iterator<Double> times, final Iterator<E> content) {
        return ofStream(
            Stream.iterate(0, i -> times.hasNext() && content.hasNext(), i -> i + 1)
                .map(i -> new Event<>(times.next(), content.next()))
        );
    }

    private <E> EventHistory<E> fromListAndDeltaSupplier(final List<E> content, final double initial, final Supplier<Double> deltaSupplier) {
        return fromIterators(Stream.iterate(initial, i -> i + deltaSupplier.get()).iterator(), content.iterator());
    }

    @Override
    public <E> EventHistory<E> fromListAndDelta(final List<E> content, final double initial, final double delta) {
        return fromListAndDeltaSupplier(content, initial, () -> delta);
    }

    @Override
    public <E> EventHistory<E> fromRandomTimesAndSupplier(final Supplier<E> content, final int size) {
        return fromListAndDeltaSupplier(Stream.generate(content::get).limit(size).toList(), Math.random(), Math::random);
    }

    @Override
    public EventHistory<String> fromFile(final String file) throws IOException {
        return ofStream(
            Files.lines(Path.of(file))
                .map(l -> l.split(":"))
                .map(split -> new Event<>(Double.valueOf(split[0]), split[1]))
        );
    }

}
