package a01c.e2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class LogicsImpl implements Logics {

    private static final int MAX_VERTEXES = 2;

    private final List<Position> vertexes = new ArrayList<>(MAX_VERTEXES);
    private int expandLevel;
    private List<Position> activeCells = Collections.emptyList(); 
    private final int size;

    public LogicsImpl(final int size) {
        this.size = size;
    }

    @Override
    public boolean hit(final Position position) {
        if (!vertexes.contains(position) && vertexes.size() < MAX_VERTEXES) {
            vertexes.add(position);
        } else {
            expand();
        }

        return isOver();
    }

    @Override
    public Optional<Integer> getVertexIndex(final Position position) {
        return Optional.of(vertexes.indexOf(position))
            .filter(i -> i != -1)
            .map(i -> i + 1);
    }

    @Override
    public boolean isActive(final Position position) {
        return activeCells.contains(position);
    }

    private boolean isOver() {
        return IntStream.range(0, size).boxed()
            .flatMap(
                x -> IntStream.range(0, size).mapToObj(y -> new Position(x, y))
            ).allMatch(pos -> activeCells.contains(pos) || vertexes.contains(pos));
    }

    private void expand() {
        final var minVertex = vertexes.stream().min(Position::compareTo).get();
        final var maxVertex = vertexes.stream().max(Position::compareTo).get();
        activeCells = IntStream.rangeClosed(minVertex.x() - expandLevel, maxVertex.x() + expandLevel).boxed()
            .flatMap(
                x -> IntStream.rangeClosed(minVertex.y() - expandLevel, maxVertex.y() + expandLevel)
                    .mapToObj(y -> new Position(x, y))
            ).filter(Predicate.not(vertexes::contains))
            .toList();
        expandLevel++;
    }
    
}