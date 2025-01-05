package a02b.e1;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class RulesEngineFactoryImpl implements RulesEngineFactory {

    private <T> List<Integer> matchingIndexes(final Pair<T, List<T>> rule, final List<T> input) {
        return Stream.iterate(0, i -> i < input.size(), i -> i + 1)
            .filter(i -> rule.get1().equals(input.get(i)))
            .toList();
    }

    private <T> List<T> applyRuleAtIndexes(final Pair<T, List<T>> rule, final List<T> input, final List<Integer> indexes) {
        return Stream.iterate(0, i -> i < input.size(), i -> i + 1)
            .flatMap(
                i -> indexes.contains(i) ?
                    rule.get2().stream() :
                    Stream.of(input.get(i))
            )
            .toList();
    }

    @Override
    public <T> List<List<T>> applyRule(final Pair<T, List<T>> rule, final List<T> input) {
        return matchingIndexes(rule, input).stream()
            .map(i -> applyRuleAtIndexes(rule, input, List.of(i)))
            .toList();
    }

    @Override
    public <T> RulesEngine<T> singleRuleEngine(final Pair<T, List<T>> rule) {
        return new RulesEngine<T>() {
            Optional<List<T>> state;
            @Override
            public void resetInput(final List<T> input) {
                state = Optional.of(applyRuleAtIndexes(rule, input, matchingIndexes(rule, input)));
            }
            @Override
            public boolean hasOtherSolutions() {
                return state.isPresent();
            }
            @Override
            public List<T> nextSolution() {
                final var result = state.get();
                state = Optional.empty();
                return result;
            }
        };
    }

    @Override
    public <T> RulesEngine<T> cascadingRulesEngine(final Pair<T, List<T>> baseRule, final Pair<T, List<T>> cascadeRule) {
        final RulesEngine<T> baseEngine = singleRuleEngine(baseRule);
        final RulesEngine<T> cascadeEngine = singleRuleEngine(cascadeRule);
        return new RulesEngine<T>() {
            @Override
            public void resetInput(final List<T> input) {
                baseEngine.resetInput(input);
            }
            @Override
            public boolean hasOtherSolutions() {
                return baseEngine.hasOtherSolutions();
            }
            @Override
            public List<T> nextSolution() {
                cascadeEngine.resetInput(baseEngine.nextSolution());
                return cascadeEngine.nextSolution();
            }
        };
    }

    @Override
    public <T> RulesEngine<T> conflictingRulesEngine(final Pair<T, List<T>> rule1, final Pair<T, List<T>> rule2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'conflictingRulesEngine'");
    }

}
