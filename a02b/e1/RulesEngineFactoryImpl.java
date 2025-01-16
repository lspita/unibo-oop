package a02b.e1;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RulesEngineFactoryImpl implements RulesEngineFactory {

    private <T> RulesEngine<T> general(
        final Function<List<T>, Stream<List<T>>> listsSupplier
    ) {
        return new RulesEngine<T>() {
            Optional<Iterator<List<T>>> iterator = Optional.empty();
            @Override
            public void resetInput(final List<T> input) {
                iterator = Optional.of(listsSupplier.apply(input).iterator());
            }
            @Override
            public boolean hasOtherSolutions() {
                return iterator.filter(Iterator::hasNext).isPresent();
            }
            @Override
            public List<T> nextSolution() {
                return iterator.get().next();
            }
        };
    }

    private <T> List<T> applyRuleAtIndexes(
        final Pair<T, List<T>> rule, 
        final List<Integer> indexes,
        final List<T> input
    ) {
        return IntStream.range(0, input.size()).boxed()
            .flatMap(i -> indexes.contains(i) ? rule.get2().stream() : Stream.of(input.get(i)))
            .toList();
    }

    private <T> List<Integer> matchingIndexes(final Pair<T, List<T>> rule, final List<T> input) {
        return IntStream.range(0, input.size()).boxed()
            .filter(i -> input.get(i).equals(rule.get1()))
            .toList();
    }

    @Override
    public <T> List<List<T>> applyRule(final Pair<T, List<T>> rule, final List<T> input) {
        return matchingIndexes(rule, input).stream()
            .map(i -> applyRuleAtIndexes(rule, List.of(i), input))
            .toList();
    }

    @Override
    public <T> RulesEngine<T> singleRuleEngine(final Pair<T, List<T>> rule) {
        return general(input -> Stream.of(applyRuleAtIndexes(
            rule, 
            matchingIndexes(rule, input), 
            input
        )));
    }

    @Override
    public <T> RulesEngine<T> cascadingRulesEngine(final Pair<T, List<T>> baseRule, final Pair<T, List<T>> cascadeRule) {
        final var baseRuleEngine = singleRuleEngine(baseRule);
        final var cascadeRuleEngine = singleRuleEngine(cascadeRule);
        return general(
            input -> {
                baseRuleEngine.resetInput(input);
                if (baseRuleEngine.hasOtherSolutions()) {
                    cascadeRuleEngine.resetInput(baseRuleEngine.nextSolution());
                }
                return cascadeRuleEngine.hasOtherSolutions() ?
                    Stream.of(cascadeRuleEngine.nextSolution()) :
                    Stream.empty();
            }
        );
    }

    @Override
    public <T> RulesEngine<T> conflictingRulesEngine(final Pair<T, List<T>> rule1, final Pair<T, List<T>> rule2) {
        return general(
            input -> null
        );
    }

}
