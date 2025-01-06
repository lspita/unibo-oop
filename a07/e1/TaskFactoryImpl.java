package a07.e1;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TaskFactoryImpl implements TaskFactory {

    private <X> Task<X> general(
        final Supplier<X> initialStateSupplier,
        final UnaryOperator<X> stateTransformer
    ) {
        final var task = new Task<X>() {
            X state;
            @Override
            public void reset() {
                state = initialStateSupplier.get();
            }
            @Override
            public void computationStep() {
                state = stateTransformer.apply(state);
            }
            @Override
            public X temporaryResult() {
                return state;
            }
        };
        task.reset();
        return task;
    }

    @Override
    public Task<Integer> counter() {
        return general(() -> -1, i -> i + 1);
    }

    @Override
    public Task<List<Integer>> fibonacciSequenceCreator() {
        return general(
            () -> Collections.emptyList(), 
            l -> Stream.concat(
                l.stream(),
                Stream.of(l.size() < 2 ? l.size() : l.getLast() + l.get(l.size() - 2))
            ).toList()
        );
    }

    @Override
    public Task<Set<Integer>> removeBiggerThan(final Set<Integer> set, final int bound) {
        return general(
            () -> Collections.unmodifiableSet(set),
            s -> {
                final var max = s.stream().max(Integer::compare);
                return max.isEmpty() || max.get() < bound ? 
                    Set.copyOf(s) : 
                    s.stream().filter(x -> x != max.get()).collect(Collectors.toSet());
            }
        );
    }

}
