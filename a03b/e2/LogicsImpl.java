package a03b.e2;

import java.util.Map;
import java.util.function.Function;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {
    
    private static final RandomGenerator RANDOM = RandomGenerator.getDefault();

    private Map<Position, Cell> cells;
    private final int size;

    public LogicsImpl(final int size) {
        this.size = size;
        reset();
    }

    // IntStream.range() but using normal stream
    private static Stream<Integer> rangeStream(final int start, final int stop) {
        return Stream.iterate(start, i -> i < stop, i -> i + 1);
    }

    @Override
    public void hit(final Position position) {
        if (cells.get(position) == Cell.PLAYER) {
            final var nearCPU = Stream.of(position.sum(-1, -1), position.sum(1, -1))
                .filter(pos -> cells.get(pos) == Cell.CPU)
                .findAny();
            
            nearCPU.ifPresentOrElse(
                cpu -> {
                    move(position, cpu);
                },
                () -> moveUp(position)
            );
        }

        if (cells.values().stream().filter(Cell.CPU::equals).count() == 0) {
            reset();
        }
    }

    @Override
    public Cell getCell(final Position position) {
        return cells.get(position);
    }

    private void reset() {
        cells = rangeStream(0, size)
            .flatMap(x -> rangeStream(0, size).map(y -> new Position(x, y)))
            .collect(Collectors.toMap(Function.identity(), pos -> Cell.EMPTY));

        rangeStream(0, size)
            .map(x -> new Position(x, size - 1))
            .forEach(pos -> cells.put(pos, Cell.PLAYER));

        rangeStream(0, size)
            .map(x -> new Position(x, RANDOM.nextInt(2)))
            .forEach(pos -> cells.put(pos, Cell.CPU));
    }

    private void move(final Position from, final Position to) {
        if (!cells.containsKey(to)) {
            return;
        }

        final var fromType = cells.get(from);
        cells.put(from, Cell.EMPTY);
        cells.put(to, fromType);
    }

    private void moveUp(final Position position) {
        move(position, position.sum(0, -1));
    }

}
