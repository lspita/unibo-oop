package a02c.e1;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ReplacersFactoryImpl implements ReplacersFactory {

    @FunctionalInterface
    interface IndexPredicate<T, S> {
        boolean test(List<T> input, T target, Integer i, S state);
    }

    @FunctionalInterface
    interface StatelessIndexPredicate<T> {
        boolean test(List<T> input, T target, Integer i);
    }

    private <T, S> Replacer<T> general(
        final Supplier<S> stateSupplier,
        final IndexPredicate<T, S> filterFunction,
        final BiFunction<T, S, List<T>> replaceFunction
    ) {
        return (input, t) -> {
            final var state = stateSupplier.get();
            return Stream.iterate(0, i -> i < input.size(), i -> i + 1)
                .filter(i -> input.get(i).equals(t))
                .filter(i -> filterFunction.test(input, t, i, state))
                .map(
                    i -> Stream.concat(
                        input.subList(0, i).stream(),
                        Stream.concat(
                            replaceFunction.apply(input.get(i), state).stream(),
                            input.subList(i + 1, input.size()).stream() 
                        )                    
                    ).toList()
                ).toList();
        };
    }

    private <T, S> Replacer<T> general(
        final StatelessIndexPredicate<T> filterFunction,
        final Function<T, List<T>> replaceFunction
    ) {
        return general(
            () -> null, 
            (input, t, i , s) -> filterFunction.test(input, t, i),
            (x, s) -> replaceFunction.apply(x) 
        );
    }

    @Override
    public <T> Replacer<T> noReplacement() {
        return general(
            (input, t, i) -> false,
            x -> Collections.emptyList()
        );
    }

    @Override
    public <T> Replacer<T> duplicateFirst() {
        return general((input, t, i) -> i == input.indexOf(t), x -> Collections.nCopies(2, x));
    }

    @Override
    public <T> Replacer<T> translateLastWith(final List<T> target) {
        return general((input, t, i) -> i == input.lastIndexOf(t), x -> target);
    }

    @Override
    public <T> Replacer<T> removeEach() {
        return general((input, t, i) -> true, x -> Collections.emptyList());
    }

    @Override
    public <T> Replacer<T> replaceEachFromSequence(final List<T> sequence) {
        return general(
            () -> sequence.iterator(),
            (input, t, i, iterator) -> iterator.hasNext(), 
            (x, iterator) -> List.of(iterator.next())
        );
    }

}
