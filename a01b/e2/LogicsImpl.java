package a01b.e2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {
    
    private final List<Position> vertexes = new ArrayList<>(2);
    private Optional<List<Position>> diamond = Optional.empty();
    private final int size;

    public LogicsImpl(final int size) {
        this.size = size;
    }

    @Override
    public List<Position> hit(final Position position) {
        if (diamond.isPresent()) {
            vertexes.clear();
            diamond = Optional.empty();
        } else if (vertexes.isEmpty()) {
            vertexes.add(position);
        } else {
            final var v1 = vertexes.getFirst();
            if (
                position.x() == v1.x() &&
                position.y() != v1.y() &&
                yDistance(position, v1) % 2 == 0 &&
                yDistance(position, v1) / 2 <= Math.min(xDistance(v1, new Position(0, v1.y())), xDistance(v1, new Position(size - 1, v1.y()))) 
            ) {
                vertexes.add(position);
                Collections.sort(vertexes, (p1, p2) -> Integer.compare(p1.y(), p2.y()));
                createDiamond(vertexes.getFirst(), vertexes.getLast());
            } else {
                vertexes.clear();
            }
        }
        return vertexes;
    }

    @Override
    public Optional<List<Position>> getDiamond() {
        return diamond;
    }

    private int yDistance(final Position p1, final Position p2) {
        return Math.abs(p1.y() - p2.y());
    }

    private int xDistance(final Position p1, final Position p2) {
        return Math.abs(p1.x() - p2.x());
    }

    private void createDiamond(final Position topVertex, final Position bottomVertex) {
        final var distance = yDistance(topVertex, bottomVertex);
        diamond = Optional.of(Stream.concat(
            IntStream.range(1, distance / 2).boxed()
                .flatMap(
                    i -> IntStream.rangeClosed(topVertex.x() - i, topVertex.x() + i)
                            .mapToObj(x -> new Position(x, topVertex.y() + i))
                ),
            IntStream.range(1, distance / 2 + 1).boxed()
                .flatMap(
                    i -> IntStream.rangeClosed(bottomVertex.x() - i, bottomVertex.x() + i)
                            .mapToObj(x -> new Position(x, bottomVertex.y() - i))
                ) 
        ).toList());
    }

}
