package a02b.e2;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {
    
    private Optional<Position> center = Optional.empty();
    private final List<Position> pivots;
    private final int size;

    public LogicsImpl(final int size) {
        this.size = size;
        pivots = List.of(
            new Position(2, 2),
            new Position(2, size - 3),
            new Position(size - 3, 2),
            new Position(size - 3, size - 3)
        );
    }

    @Override
    public boolean hit(final Position position) {
        if (center.isEmpty()) {
            center = Optional.of(position);
            return false;
        }

        final var c = center.get();
        if (c.equals(position)) {
            return true;
        }

        final var pivotIndex = getAngles(c).indexOf(position);
        if (pivotIndex != -1) {
            center = Optional.of(pivots.get(pivotIndex));
        }

        return false;
    }

    @Override
    public boolean isActive(final Position position) {
        if (center.isEmpty()) {
            return false;
        }
        final var c = center.get();
        return Math.abs(position.x() - c.x()) <= 2 &&
            Math.abs(position.y() - c.y()) <= 2 &&
            !getAngles(c).contains(position);
    }

    private List<Position> getAngles(final Position center) {
        return Stream.of(center.x() - 1, center.x() + 1)
            .flatMap(
                x -> Stream.of(center.y() - 1, center.y() + 1)
                    .map(y -> new Position(x, y))
            ).toList();
    }

}
