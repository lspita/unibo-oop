package a03a.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WindowingFactoryImpl implements WindowingFactory {

    private <X, Y> Windowing<X, Y> ofMemoryOptional(
        final BiPredicate<List<X>, X> predicate,
        final BiFunction<List<X>, X, Optional<Y>> processor
    ) {
        return new Windowing<X,Y>() {
            final List<X> inputs = new ArrayList<>();
            @Override
            public Optional<Y> process(final X x) {
                final Optional<Y> result = predicate.test(inputs, x) ? processor.apply(inputs, x) : Optional.empty();
                inputs.add(x);
                return result;
            }
        };
    }

    private <X, Y> Windowing<X, Y> ofMemory(
        final BiPredicate<List<X>, X> predicate,
        final BiFunction<List<X>, X, Y> processor
    ) {
        return ofMemoryOptional(
            predicate,
            (inputs, x) -> Optional.of(processor.apply(inputs, x))
        );
    }

    private <X> List<X> concat(final List<X> list, final X x) {
        return Stream.concat(list.stream(), Stream.of(x)).toList();
    }

    @Override
    public <X> Windowing<X, X> trivial() {
        return x -> Optional.of(x);
    }

    @Override
    public <X> Windowing<X, Pair<X, X>> pairing() {
        return ofMemory(
            (inputs, x) -> inputs.size() >= 1,
            (inputs, x) -> new Pair<>(inputs.getLast(), x)
        );
    }

    private <X> List<X> lastN(final List<X> inputs, final X x, final int n) {
        final var list = concat(inputs, x);
        return list.subList(list.size() - n, list.size());
    }

    private Integer listSum(final List<Integer> list) {
        return list.stream().collect(Collectors.summingInt(Integer::valueOf));
    }

    @Override
    public Windowing<Integer, Integer> sumLastFour() {
        return ofMemory(
            (inputs, x) -> inputs.size() >= 3,
            (inputs, x) -> listSum(lastN(inputs, x, 4))
        );
    }

    @Override
    public <X> Windowing<X, List<X>> lastN(final int n) {
        return ofMemory(
            (inputs, x) -> inputs.size() >= n - 1,
            (inputs, x) -> lastN(inputs, x, n)
        );
    }

    @Override
    public Windowing<Integer, List<Integer>> lastWhoseSumIsAtLeast(final int n) {
        return ofMemoryOptional(
            (inputs, x) -> listSum(concat(inputs, x)) >= n,
            (inputs, x) -> {
                final var list = concat(inputs, x);
                return Stream.iterate(list.size() - 1, i -> i >= 0, i -> i - 1)
                    .map(i -> list.subList(i, list.size()))
                    .filter(subList -> listSum(subList) >= n)
                    .findFirst();
            }
        );
    }

}
