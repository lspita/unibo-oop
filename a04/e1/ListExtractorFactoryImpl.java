package a04.e1;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class ListExtractorFactoryImpl implements ListExtractorFactory {

    @Override
    public <X> ListExtractor<X, Optional<X>> head() {
        return list -> list.size() == 0 ? Optional.empty() : Optional.of(list.getFirst());
    }

    @Override
    public <X, Y> ListExtractor<X, List<Y>> collectUntil(final Function<X, Y> mapper, final Predicate<X> stopCondition) {
        return list -> list.stream()
            .takeWhile(Predicate.not(stopCondition))
            .map(mapper)
            .toList();
    }

    @Override
    public <X> ListExtractor<X, List<List<X>>> scanFrom(final Predicate<X> startCondition) {
        return list -> IntStream.range(0, list.size()).boxed()
            .map(
                i -> list.stream()
                    .limit(i + 1)
                    .dropWhile(Predicate.not(startCondition))
                    .toList()
            )
            .dropWhile(Collection::isEmpty)
            .toList();
    }

    @Override
    public <X> ListExtractor<X, Integer> countConsecutive(final X x) {
        return list -> (int) list.stream()
            .dropWhile(Predicate.not(x::equals))
            .takeWhile(x::equals)
            .count();
    }

}
