package a02b.sol1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/*
 * Idea of the solution: a single rule engine can capture all cases!
 * For each element in the input checks which rules can apply, each rule
 * is applied and will give certains solutions -- all solutions are then combined.
 * The use of flatMap facilitates production of all algorithms here.
 */
public class RulesEngineFactoryImpl implements RulesEngineFactory {

    // Utility to replace from 'source' the element in index-position with 'toReplace'
    private <T> List<T> replaceAtPosition(int index, List<T> source, List<T> newElements){
        var l = new LinkedList<>(source);
        l.remove(index);
        var it = newElements.listIterator(newElements.size());
        while (it.hasPrevious()){
            l.add(index, it.previous());
        }
        return l;
    }

    public <T> List<List<T>> applyRule(Pair<T, List<T>> rule, List<T> input){
        return Stream
                .iterate(0, i -> i < input.size(), i -> i + 1)
                .flatMap(i -> input.get(i).equals(rule.get1()) ? Stream.of(replaceAtPosition(i, input, rule.get2())): Stream.empty())
                .toList();
    }

    private <T> boolean applicable(List<Pair<T, List<T>>> rules, List<T> input){
        return rules.stream().anyMatch(r -> input.contains(r.get1()));
    }

    // this method does the whole job: essentially by nesting two flatMaps
    private <T> Stream<List<T>> applyRules(List<Pair<T, List<T>>> rules, List<T> input){
        return !applicable(rules, input) 
                ? Stream.of(input) 
                : rules
                    .stream()
                    .flatMap(rule -> applyRule(rule, input).stream().flatMap(list -> applyRules(rules, list)).distinct())
                    .distinct();
    }

    private <T> RulesEngine<T> fromRules(List<Pair<T, List<T>>> rules){
        return new RulesEngine<T>() {
            private Iterator<List<T>> iterator = null;

            @Override
            public void resetInput(List<T> input) {
                this.iterator = applyRules(rules, input).iterator();
            }

            @Override
            public boolean hasOtherSolutions() {
                return this.iterator.hasNext();
            }

            @Override
            public List<T> nextSolution() {
                return this.iterator.next();
            }
            
        };
    }

    @Override
    public <T> RulesEngine<T> singleRuleEngine(Pair<T, List<T>> rule) {
        return fromRules(List.of(rule));
    }

    @Override
    public <T> RulesEngine<T> cascadingRulesEngine(Pair<T, List<T>> rule1, Pair<T, List<T>> rule2) {
        return fromRules(List.of(rule1, rule2));
    }

    @Override
    public <T> RulesEngine<T> conflictingRulesEngine(Pair<T, List<T>> rule1, Pair<T, List<T>> rule2) {
        return fromRules(List.of(rule1, rule2));
    }

}
