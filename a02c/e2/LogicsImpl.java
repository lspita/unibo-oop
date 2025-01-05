package a02c.e2;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import java.util.stream.Stream;

public class LogicsImpl implements Logics {
    
    private final int size;
    private List<Position> angles = Collections.emptyList();

    public LogicsImpl(final int size) {
        this.size = size;
    }

    @Override
    public List<Position> hit(final Position position) {
        if (isOutOfBounds(position)) {
            throw new IllegalArgumentException();
        }

        if (angles.size() == 0) {
            initSquare(position);
        } else  {
            if (angles.contains(position)) {
                expand(position);
            }
        }

        return Collections.unmodifiableList(activePositions());
    }

    @Override
    public boolean isOver() {
        return activePositions().stream().anyMatch(this::isOnBorder);
    }

    private List<Position> activePositions() {
        return Stream.iterate(angles.get(0).x(), x -> x <= angles.get(1).x(), x -> x + 1)
            .flatMap(
                x -> Stream.iterate(angles.get(0).y(), y -> y <= angles.get(2).y(), y -> y + 1)
                    .map(y -> new Position(x, y))
            ).filter(
                pos -> 
                    angles.stream().anyMatch(a -> a.x() == pos.x()) ||
                    angles.stream().anyMatch(a -> a.y() == pos.y())
            )
            .filter(this::isInBounds)
            .toList();
    }

    private boolean isOnBorder(final Position position) {
        return 
            position.x() == 0 || position.x() == size - 1 ||
            position.y() == 0 || position.y() == size - 1;
    }

    private boolean isOutOfBounds(final Position position) {
        return 
            position.x() < 0 || position.x() >= size ||
            position.y() < 0 || position.y() >= size;
    }

    private boolean isInBounds(final Position position) {
        return !isOutOfBounds(position);
    }

    private void initSquare(final Position center) {
        angles = Stream.of(center.x() - 1, center.x() + 1)
            .flatMap(
                x -> Stream.of(center.y() - 1, center.y() + 1)
                    .map(y -> new Position(x, y))    
            ).sorted()
            .toList();
    }

    private void expand(final Position angle) {
        final var movement = anglesMovements().get(angle);
        angles = angles.stream()
            .map(pos -> new Position(
                pos.x() + (pos.x() == angle.x() ? movement.x() : 0), 
                pos.y() + (pos.y() == angle.y() ? movement.y() : 0)
            )).sorted()
            .toList();
    }

    private Map<Position, Position> anglesMovements() {
        return Map.of(
            angles.get(0), new Position(-1, -1),
            angles.get(1), new Position(1, -1),
            angles.get(2), new Position(-1, 1),
            angles.get(3), new Position(1, 1)
        );
    }

}
