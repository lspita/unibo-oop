package a02b.e2;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {
    
    private static final int RANDOM_LEFT_QUANTITY = 10;
    private static final int RANDOM_RIGHT_QUANTITY = 10;

    enum Direction {
        LEFT,
        RIGHT,
        UP
    }

    private final Map<Position, Cell> cells;
    private Direction direction = Direction.UP;
    private final RandomGenerator random = RandomGenerator.getDefault();

    public LogicsImpl(final int size) {
        cells = Stream.iterate(0, x -> x < size, x -> x + 1)
            .flatMap(
                x -> Stream.iterate(0, y -> y < size, y -> y + 1)
                    .map(y -> new Position(x, y))
            ).collect(Collectors.toMap(
                Function.identity(), 
                pos -> Cell.EMPTY
            ));
        
        cells.put(new Position(random.nextInt(size), size - 1), Cell.MAIN);
        fillRandom(Cell.LEFT, RANDOM_LEFT_QUANTITY);
        fillRandom(Cell.RIGHT, RANDOM_RIGHT_QUANTITY);
    }

    @Override
    public boolean hit(final Position position) {
        final var main = mainPosition();
        final var previous = switch (direction) {
            case LEFT -> moveMain(new Position(main.x() - 1, main.y()));
            case RIGHT -> moveMain(new Position(main.x() + 1, main.y()));
            case UP -> moveMain(new Position(main.x(), main.y() - 1));
        };

        previous.map(p -> switch (p) {
            case Cell.LEFT -> Direction.LEFT;
            case Cell.RIGHT -> Direction.RIGHT;
            default -> direction;
        }).ifPresent(d -> {
            direction = d;
        });
        return previous.isEmpty();
    }

    @Override
    public Cell getCell(final Position position) {
        return cells.get(position);
    }

    private Optional<Cell> moveMain(final Position position) {
        final var main = mainPosition();
        cells.put(main, Cell.EMPTY);
        return Optional.ofNullable(cells.put(position, Cell.MAIN));
    }

    private Position mainPosition() {
        return cells.entrySet().stream()
            .filter(e -> e.getValue() == Cell.MAIN)
            .map(Map.Entry::getKey)
            .findFirst().get();
    }

    private void fillRandom(final Cell cell, final int times) {
        IntStream.range(0, times)
            .forEach(i -> {
                final var emptyCells = cells.entrySet().stream()
                    .filter(e -> e.getValue() == Cell.EMPTY)
                    .map(Map.Entry::getKey)
                    .toList();
                cells.put(emptyCells.get(random.nextInt(emptyCells.size())), cell);
            });
    }

}
