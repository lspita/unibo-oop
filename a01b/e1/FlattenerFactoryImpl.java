package a01b.e1;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FlattenerFactoryImpl implements FlattenerFactory {

    private <X, Y> Flattener<X, Y> ofTransformer(final Function<List<X>, Stream<Y>> transformer) {
        return list -> list.stream().flatMap(transformer::apply).toList();
    }

    @Override
    public Flattener<Integer, Integer> sumEach() {
        return each(list -> IntStream.range(0, list.size()).map(i -> list.get(i)).sum());
    }

    @Override
    public <X> Flattener<X, X> flattenAll() {
        return ofTransformer(List::stream);
    }

    @Override
    public Flattener<String, String> concatPairs() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'concatPairs'");
    }

    @Override
    public <I, O> Flattener<I, O> each(Function<List<I>, O> mapper) {
        return ofTransformer(list -> Stream.of(mapper.apply(list)));
    }

    @Override
    public Flattener<Integer, Integer> sumVectors() {
        return list -> list.size() == 0 ? 
            Collections.emptyList() : 
            Stream.iterate(0, i -> i < list.get(0).size(), i -> i + 1)
                .map(i -> sumEach().flatten(List.of(list.stream().map(vec -> vec.get(i)).toList())).get(0))
                .toList();
    }

}
