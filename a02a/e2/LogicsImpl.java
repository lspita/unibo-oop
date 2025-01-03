package a02a.e2;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {

    private final Map<Position, Cell> cells = new HashMap<>();

    public LogicsImpl(final int size) {
        Stream.iterate(0, x -> x < size, x -> x + 1)
            .flatMap(
                x -> Stream.iterate(0, y -> y < size, y -> y + 1)
                    .map(y -> new Position(x, y))
            ).forEach(pos -> cells.put(pos, Cell.EMPTY));
    }

    @Override
    public void hit(final Position position) {
        checkValid(position);
        switch (cells.get(position)) {
            case BISHOP -> checkAndReset();
            case EMPTY -> putBishop(position);
            case BLOCKED -> throw new IllegalStateException(
                new StringBuilder("Position ").append(position).append(" is blocked").toString()
            );
        }

    }

    @Override
    public Cell getType(final Position position) {
        return cells.get(checkValid(position));
    }

    private void checkAndReset() {
        if (cells.values().stream().allMatch(cell -> cell != Cell.EMPTY)) {
            cells.forEach((position, cell) -> cells.put(position, Cell.EMPTY));
        }
    }

    private void putBishop(final Position position) {
        cells.keySet().stream()
            .filter(pos -> Math.abs(pos.x() - position.x()) == Math.abs(pos.y() - position.y()))
            .forEach(pos -> cells.put(pos, Cell.BLOCKED));
        cells.put(position, Cell.BISHOP);
    }

    private boolean isOutOfBounds(final Position position) {
        return !isInBounds(position);
    }
    
    private boolean isInBounds(final Position position) {
        return cells.containsKey(position);
    }
    
    private Position checkValid(final Position position) {
        if (isOutOfBounds(Objects.requireNonNull(position))) {
            throw new IllegalArgumentException(
                new StringBuilder("Position ").append(position).append(" is out of bounds").toString()
            );
        }
        return position;
    }
    
}
