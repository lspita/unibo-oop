package a01a.e1;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GraphFactoryImpl implements GraphFactory {

    private static <X> List<X> concat(final List<X> left, final List<X> right) {
        return Stream.concat(left.stream(), right.stream()).toList();
    }

    private static <X> List<X> concat(final List<X> left, final X right) {
        return concat(left, List.of(right));
    }

    @Override
    public <X> Graph<X> combine(final Graph<X> g1, final Graph<X> g2) {
        return new Graph<X>() {
            @Override
            public Set<X> getNodes() {
                return Stream.concat(g1.getNodes().stream(), g2.getNodes().stream()).collect(Collectors.toSet());
            }
            @Override
            public boolean edgePresent(final X start, final X end) {
                return g1.edgePresent(start, end) || g2.edgePresent(start, end);
            }
            @Override
            public int getEdgesCount() {
                return (int) getEdgesStream().count();
            }
            @Override
            public Stream<Pair<X, X>> getEdgesStream() {
                return Stream.concat(g1.getEdgesStream(), g2.getEdgesStream()).distinct();
            }
        };
    }

    private <X> Graph<X> empty() {
        return createDirectedChain(Collections.emptyList());
    }

    @Override
    public <X> Graph<X> createDirectedChain(final List<X> nodes) {
        return new Graph<X>() {
            @Override
            public Set<X> getNodes() {
                return nodes.stream().collect(Collectors.toSet());
            }
            @Override
            public boolean edgePresent(final X start, final X end) {
                return getEdgesStream().anyMatch(new Pair<>(start, end)::equals);
            }
            @Override
            public int getEdgesCount() {
                return (int) getEdgesStream().count();
            }
            @Override
            public Stream<Pair<X, X>> getEdgesStream() {
                return Stream.iterate(0, i -> i < nodes.size() - 1, i -> i + 1)
                    .map(i -> new Pair<>(nodes.get(i), nodes.get(i + 1)));
            }
        };
    }

    @Override
    public <X> Graph<X> createBidirectionalChain(final List<X> nodes) {
        return combine(createDirectedChain(nodes), createDirectedChain(nodes.reversed()));
    }

    @Override
    public <X> Graph<X> createDirectedCircle(final List<X> nodes) {
        return createDirectedChain(concat(nodes, nodes.getFirst()));
    }

    @Override
    public <X> Graph<X> createBidirectionalCircle(final List<X> nodes) {
        return combine(createDirectedCircle(nodes), createDirectedCircle(nodes.reversed()));
    }

    @Override
    public <X> Graph<X> createDirectedStar(final X center, final Set<X> nodes) {
        return nodes.stream()
            .map(n -> createDirectedChain(List.of(center, n)))
            .reduce(empty(), this::combine);
    }

    @Override
    public <X> Graph<X> createBidirectionalStar(final X center, final Set<X> nodes) {
        return nodes.stream()
            .flatMap(n -> Stream.of(createDirectedChain(List.of(center, n)), createDirectedChain(List.of(n, center))))
            .reduce(empty(), this::combine);
    }

    @Override
    public <X> Graph<X> createFull(final Set<X> nodes) {
        return nodes.stream()
            .map(n -> createDirectedStar(
                n, 
                nodes.stream().filter(Predicate.not(n::equals)).collect(Collectors.toSet())
            ))
            .reduce(empty(), this::combine);
    }

}
