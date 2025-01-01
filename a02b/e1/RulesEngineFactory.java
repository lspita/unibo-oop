package a02b.e1;

import java.util.List;

public interface RulesEngineFactory {

    /**
     * This is a preliminary helper method, which applies a single rule and gives
     * as many solutions as many times the rule can be applied
     * @param <T>
     * @param rule, in the form of a pair (e, List(a1,...an))
     * applying a rule means substituing an `e` with `a1,...an`
     * @param input
     * @return a list of solutions, where each solution is a list similar to the input
     * but with one application of the rule per occurrence of `e`: from left to right
     * See the test.
     */
    <T> List<List<T>> applyRule(Pair<T, List<T>> rule, List<T> input);

    /**
     * @param <T>
     * @param rule
     * @return an engine giving one result, obtained applying the rule in all possible places
     * See the test.
     */
    <T> RulesEngine<T> singleRuleEngine(Pair<T, List<T>> rule);

    /**
     * @param <T>
     * @param baseRule, (e, (a1,...,an)
     * @param cascadeRule, which is a rule (ai, (b1,..,bm)) activated by previous rule
     * @return an engine applying the rules in all possible places, again always giving one result.
     * See the test.
     */
    <T> RulesEngine<T> cascadingRulesEngine(Pair<T, List<T>> baseRule, Pair<T, List<T>> cascadeRule);

    /**
     * @param <T>
     * @param rule1, (e, (a1,...,an))
     * @param rule2, (e, (b1,...,bn))
     * @return an engine applying the two rules each time an `e` occurs, giving 2^m solutions
     * where m is the number of times `e` occurs
     * See the test.
     */
    <T> RulesEngine<T> conflictingRulesEngine(Pair<T, List<T>> rule1, Pair<T, List<T>> rule2);

}
