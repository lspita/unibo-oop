package a02a.e2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {
    
    private static final int BLOCK_CELL_JUMP = 2;
    private final int lowerBound = 0;
    private final int upperBound;

    private final Map<Position, CellType> positions = new HashMap<>();

    public LogicsImpl(final int size) {
        this.upperBound = size - 1;
        Stream.iterate(lowerBound, x -> x + 1).limit(size).forEach(
            x -> Stream.iterate(lowerBound, y -> y + 1).limit(size).forEach(
                y -> {
                    if (x % BLOCK_CELL_JUMP != 0 || y % BLOCK_CELL_JUMP != 0) {
                        positions.put(new Position(x, y), CellType.BLOCKED);
                    }
                }
            )
        );
    }

    @Override
    public void hit(final Position position) {
        if (isOver()) {
            throw new IllegalStateException("Game is already over");
        }
        if (isOutOfBounds(Objects.requireNonNull(position))) {
            throw new IllegalArgumentException(
                new StringBuilder("Position ").append(position).append(" is out of bounds").toString()
            );
        }

        positions.putIfAbsent(position, CellType.ACTIVE);
    }

    @Override
    public Optional<CellType> getType(final Position position) {
        return Optional.ofNullable(positions.get(position));
    }

    @Override
    public boolean isOver() {
        return positions.entrySet().stream()
            .filter(entry -> entry.getValue() ==  CellType.ACTIVE)
            .map(Entry::getKey)
            .flatMap(pos -> minimalSquares(pos).stream())
            .filter(square -> square.stream().allMatch(this::isInBounds))
            .anyMatch(square -> square.stream().allMatch(pos -> positions.get(pos) == CellType.ACTIVE));
    }

    private boolean isOutOfBounds(final Position position) {
        return 
            position.x() < lowerBound || position.x() > upperBound ||
            position.y() < lowerBound || position.y() > upperBound;
    }

    private boolean isInBounds(final Position position) {
        return !isOutOfBounds(position);
    }

    private List<List<Position>> minimalSquares(final Position position) {
        return List.of(
            squarePositions(position, BLOCK_CELL_JUMP, BLOCK_CELL_JUMP),
            squarePositions(position, -BLOCK_CELL_JUMP, BLOCK_CELL_JUMP),
            squarePositions(position, BLOCK_CELL_JUMP, -BLOCK_CELL_JUMP),
            squarePositions(position, -BLOCK_CELL_JUMP, -BLOCK_CELL_JUMP)
        );
    }

    private List<Position> squarePositions(final Position position, final int xDifference, final int yDifference) {
        return Stream.of(position.x() + xDifference, position.x())
            .flatMap(x -> Stream.of(position.y() + yDifference, position.y()).map(y -> new Position(x, y)))
            .toList();
    }

}
