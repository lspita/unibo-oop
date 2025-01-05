package a02b.e2;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {
    
    enum Cell {
        ACTIVE,
        EMPTY
    }

    enum Angle {
        NE,
        NW,
        SE,
        SW
    }

    private final Map<Position, Cell> cells;
    private final Map<Angle, Position> pivots;
    private Optional<Position> center = Optional.empty();

    public LogicsImpl(final int size) {
        cells = Stream.iterate(0,  x -> x < size, x -> x + 1)
            .flatMap(
                x -> Stream.iterate(0,  y -> y < size, y -> y + 1)
                    .map(y -> new Position(x, y))   
            ).collect(Collectors.toMap(Function.identity(), pos -> Cell.EMPTY));
        
        pivots = Map.of(
            Angle.NE, new Position(2, 2),
            Angle.NW, new Position(size - 3, 2),
            Angle.SE, new Position(2, size - 3),
            Angle.SW, new Position(size - 3, size -3)
        );
    }

    @Override
    public boolean hit(final Position position) {
        if (isOutOfBounds(Objects.requireNonNull(position))) {
            return true;
        }

        if (center.isEmpty()) {
            activateCells(position);
        } else {
            final var c = center.get();
            if (c.equals(position)) {
                return true;
            }
            updateCells(c, position);
        }
        return false;
    }

    @Override
    public List<Position> getActiveCells() {
        return activeCells().toList();
    }

    private Stream<Position> activeCells() {
        return cells.entrySet().stream()
            .filter(e -> e.getValue() == Cell.ACTIVE)
            .map(Map.Entry::getKey);
    }
    
    private void activateCells(final Position center) {
        cells.keySet().forEach(pos -> cells.put(pos, Cell.EMPTY));
        final var angles = getAngles(center, 2).keySet().stream().sorted().toList();
        Stream.iterate(angles.getFirst().x(),  x -> x <= angles.getLast().x(), x -> x + 1)
            .flatMap(
                x -> Stream.iterate(angles.getFirst().y(),  y -> y <= angles.getLast().y(), y -> y + 1)
                    .map(y -> new Position(x, y))
            ).filter(this::isInBounds)
            .forEach(pos -> cells.put(pos, Cell.ACTIVE));
        
        this.center = Optional.of(center);
        getAngles(center, 1).keySet().forEach(pos -> cells.put(pos, Cell.EMPTY));
    }

    private void updateCells(final Position center, final Position position) {
        final var angle = getAngles(center, 1).entrySet().stream()
            .filter(e -> e.getKey().equals(position))
            .map(Map.Entry::getValue)
            .findFirst();
        angle.ifPresent(a -> activateCells(pivots.get(a)));
    }

    private Map<Position, Angle> getAngles(final Position position, final int distance) {
        return Map.of(
            new Position(position.x() - distance, position.y() - distance), Angle.NE,
            new Position(position.x() + distance, position.y() - distance), Angle.NW,
            new Position(position.x() - distance, position.y() + distance), Angle.SE,
            new Position(position.x() + distance, position.y() + distance), Angle.SW
        );
    }

    private boolean isOutOfBounds(final Position position) {
        return !isInBounds(position);
    }


    private boolean isInBounds(final Position position) {
        return cells.containsKey(position);
    }

}
