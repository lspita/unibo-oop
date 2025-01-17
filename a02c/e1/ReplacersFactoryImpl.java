package a02c.e1;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ReplacersFactoryImpl implements ReplacersFactory {

    private <T, S> Replacer<T> general(
        final BiFunction<List<T>, T, S> stateGenerator,
        final BiFunction<List<T>, T, List<Integer>> indexesSelector,
        final BiFunction<T, S, Stream<T>> transformer
    ) {
        return (input, t) -> {
            final var state = stateGenerator.apply(input, t);
            return IntStream.range(0, input.size()).boxed()
                .filter(indexesSelector.apply(input, t)::contains)
                .map(
                    i -> IntStream.range(0, input.size()).boxed()
                        .flatMap(
                            ii -> ii == i ? transformer.apply(input.get(ii), state) : Stream.of(input.get(ii))
                        ).toList()
                )
                .toList();
        };
    }

    private <T, S> Replacer<T> generalStateless(
        final BiFunction<List<T>, T, List<Integer>> indexesSelector,
        final Function<T, Stream<T>> transformer
    ) {
        return general((input, t) -> null, indexesSelector, (t, s) -> transformer.apply(t));
    }

    @Override
    public <T> Replacer<T> noReplacement() {
        return generalStateless(
            (input, t) -> Collections.emptyList(),
            t -> Stream.empty()
        );
    }

    @Override
    public <T> Replacer<T> duplicateFirst() {
        return generalStateless(
            (input, t) -> List.of(input.indexOf(t)),
            t -> Stream.of(t, t) 
        );
    }

    @Override
    public <T> Replacer<T> translateLastWith(final List<T> target) {
        return generalStateless(
            (input, t) -> List.of(input.lastIndexOf(t)),
            t -> target.stream() 
        );
    }

    private <T> Stream<Integer> matchingIndexes(final List<T> input, final T target) {
        return IntStream.range(0, input.size()).boxed()
            .filter(i -> input.get(i).equals(target));
    }

    @Override
    public <T> Replacer<T> removeEach() {
        return generalStateless(
            (input, t) -> matchingIndexes(input, t).toList(),
            t -> Stream.empty()
        );
    }

    @Override
    public <T> Replacer<T> replaceEachFromSequence(final List<T> sequence) {
        return general(
            (input, t) -> sequence.iterator(),
            (input, t) -> matchingIndexes(input, t).limit(sequence.size()).toList(),
            (t, s) -> Stream.of(s.next())
        );
    }

}
