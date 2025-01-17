package a02c.e2;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class LogicsImpl implements Logics {

    private List<Position> vertexes = Collections.emptyList();
    private final List<Position> vertexesExpansions = List.of(
        new Position(-1, -1), // NE
        new Position(1, -1), // NW
        new Position(-1, 1), // SE
        new Position(1, 1)  // SW
    );
    private final int size;

    public LogicsImpl(final int size) {
        this.size = size;
    }

    @Override
    public boolean hit(final Position position) {
        if (vertexes.isEmpty()) {
            vertexes = vertexesExpansions.stream()
                .map(position::sum)
                .toList();
        } else if (vertexes.contains(position)) {
            final var vertexExpansion = vertexesExpansions.get(vertexes.indexOf(position));
            vertexes = vertexes.stream()
                .map(
                    v -> v.x() == position.x() ? v.sumX(vertexExpansion) : v
                ).map(
                    v -> v.y() == position.y() ? v.sumY(vertexExpansion) : v
                ).toList();
        }

        return vertexes.stream().anyMatch(this::isOnBorder);
    }

    @Override
    public boolean isActive(final Position position) {
        if (vertexes.isEmpty()) {
            return false;
        }
        final var ne = vertexes.get(0);
        final var sw = vertexes.get(3);
        return 
            (inRange(Position::x, position, ne, sw) && equalsAny(Position::y, position, ne, sw)) ||
            (inRange(Position::y, position, ne, sw) && equalsAny(Position::x, position, ne, sw)); 
    }

    private boolean inRange(
        final Function<Position, Integer> projection, 
        final Position pos, 
        final Position min, 
        final Position max
    ) {
        return 
            projection.apply(pos) >= projection.apply(min) && 
            projection.apply(pos) <= projection.apply(max);
    }

    private boolean equalsAny(
        final Function<Position, Integer> projection, 
        final Position pos, 
        final Position min, 
        final Position max
    ) {
        return 
            projection.apply(pos) == projection.apply(min) || 
            projection.apply(pos) == projection.apply(max);
    }

    private boolean isOnBorder(final Position position) {
        return 
            position.x() == 0 || position.x() == size - 1 ||
            position.y() == 0 || position.y() == size - 1;
    }

}
