package a01a.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

public class SubsequenceCombinerFactoryImpl implements SubsequenceCombinerFactory {

    private <X, Y> SubsequenceCombiner<X, Y> ofGeneric(
        final BiFunction<List<X>, Integer, Integer> selector,
        final Function<List<X>, Y> transformer
    ) {
        return (list) -> {
            var i = 0;
            final List<List<X>> subSequences = new ArrayList<>();
            while (i < list.size()) {
                final var finalIndex = selector.apply(list, i);
                subSequences.add(
                    IntStream.rangeClosed(i, finalIndex)
                        .filter(ii -> ii < list.size())
                        .mapToObj(list::get)
                        .toList()
                );
                i = finalIndex + 1;
            }
            return subSequences.stream()
                .map(transformer::apply)
                .toList();
        };
    } 

    @Override
    public SubsequenceCombiner<Integer, Integer> tripletsToSum() {
        return ofGeneric(
            (list, i) -> i + 2,
            (sequence) -> sequence.stream().reduce(0, (x, y) -> x + y)
        );
    }

    @Override
    public <X> SubsequenceCombiner<X, List<X>> tripletsToList() {
        return ofGeneric((list, i) -> i + 2, Function.identity());
    }

    @Override
    public SubsequenceCombiner<Integer, Integer> countUntilZero() {
        return ofGeneric(
            (list, i) -> {
                final var subListIndex = list.subList(i, list.size() - 1).indexOf(0);
                return subListIndex != -1 ? i + subListIndex : list.size() - 1;
            },
            (sequence) -> (int) sequence.stream().filter(x -> x != 0).count()
        );
    }

    @Override
    public <X, Y> SubsequenceCombiner<X, Y> singleReplacer(Function<X, Y> function) {
        return ofGeneric(
            (list, i) -> i,
            (sequence) -> sequence.stream().map(function::apply).findFirst().get()
        );
    }

    @Override
    public SubsequenceCombiner<Integer, List<Integer>> cumulateToList(int threshold) {
        return ofGeneric(
            (list, i) -> {
                var sum = 0;
                for (var j = i; j < list.size(); j++) {
                    sum += list.get(j);
                    if (sum >= threshold) {
                        return j;
                    }
                }
                return list.size() - 1;
            },
            Function.identity()
        );
    }

}
