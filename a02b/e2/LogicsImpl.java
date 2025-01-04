package a02b.e2;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {
    
    private static final int DIAGONAL_REQUIRED_CELLS = 3;

    private final Map<Position, CellState> cells;
    private boolean pendingRestart = false;

    public LogicsImpl(final int size) {
        cells = Stream.iterate(0, x -> x < size, x -> x + 1)
            .flatMap(x -> Stream.iterate(0, y -> y < size, y -> y + 1).map(y -> new Position(x, y)))
            .collect(Collectors.toMap(Function.identity(), (pos) -> CellState.EMPTY));
    }

    @Override
    public CellState hit(final Position position) {
        final var currentState = cells.get(checkPosition(position));
        if (pendingRestart) {
            return currentState;
        }
        final var newState = switch (currentState) {
            case ACTIVE -> CellState.EMPTY;
            case EMPTY -> CellState.ACTIVE;
            default -> throw new IllegalArgumentException(
                new StringBuilder("Position ").append(position).append(" is in illegal state").toString()
            );
        };
        cells.put(position, newState);
        return newState;
    }

    @Override
    public void checkReset() {
        if (pendingRestart) {
            reset();
            pendingRestart = false;
        } else {
            pendingRestart = check();
        }
    }

    @Override
    public CellState getCell(final Position position) {
        return cells.get(checkPosition(position));
    }

    private void reset() {
        cells.keySet().forEach(position -> cells.put(position, CellState.EMPTY));
    }

    private boolean check() {
        final var diagonal = cells.entrySet().stream()
            .filter(entry -> entry.getValue() == CellState.ACTIVE)
            .map(Map.Entry::getKey)
            .map(this::diagonal)
            .filter(d -> d.stream().filter(pos -> cells.get(pos) == CellState.ACTIVE).count() == DIAGONAL_REQUIRED_CELLS)
            .findAny();
        diagonal.ifPresent(d -> d.forEach(pos -> cells.put(pos, CellState.BLOCKED)));
        return diagonal.isPresent();
    }

    private List<Position> diagonal(final Position position) {
        return cells.keySet().stream()
            .filter(pos -> pos.x() - position.x() == pos.y() - position.y())
            .toList();
    }

    private boolean isInBounds(final Position position) {
        return cells.containsKey(position);
    }

    private boolean isOutOfBounds(final Position position) {
        return !isInBounds(position);
    }

    private Position checkPosition(final Position position) {
        if (isOutOfBounds(Objects.requireNonNull(position))) {
            throw new IllegalArgumentException(
                new StringBuilder("Position ").append(position).append(" is out of bounds").toString()
            );
        }
        return position;
    }

}
