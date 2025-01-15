package a02a.e2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {
    
    private static final int STEP = 2;
    private static final int SQUARE_VERTEXES = 4;

    private final Map<Position, Cell> cells;
    private final LinkedList<Position> lastHits = new LinkedList<>();

    public LogicsImpl(final int size) {
        cells = IntStream.range(0, size).boxed()
            .flatMap(x -> IntStream.range(0, size).mapToObj(y -> new Position(x, y)))
            .collect(Collectors.toMap(Function.identity(), pos -> Cell.BLOCKED));
        
        Stream.iterate(0, x -> x < size, x -> x + STEP).flatMap(
            x -> Stream.iterate(0, y -> y < size, y -> y + STEP)
                .map(y -> new Position(x, y))
        ).forEach(pos -> cells.put(pos, Cell.EMPTY));
    }

    @Override
    public boolean hit(final Position position) {
        if (isOver()) {
            return true;
        }

        if (cells.get(position) == Cell.EMPTY) {
            cells.put(position, Cell.ACTIVE);
            lastHits.addLast(position);
            if (lastHits.size() > SQUARE_VERTEXES) {
                lastHits.removeFirst();
            }
        }

        return isOver();
    }

    @Override
    public Cell getCell(final Position position) {
        return cells.get(position);
    }

    private boolean isOver() {
        record Check(Position p1, Position p2, Integer distanceX, Integer distanceY) {
            private boolean isValid() {
                return p1.x() - p2.x() == distanceX && p1.y() - p2.y() == distanceY;
            }
        }
        
        if (lastHits.size() < SQUARE_VERTEXES) {
            return false;
        }

        final var sortedHits = lastHits.stream().sorted().toList();
        return Stream.of(
            new Check(sortedHits.get(1), sortedHits.get(0), STEP, 0),  // NW - NE
            new Check(sortedHits.get(3), sortedHits.get(1), 0, STEP),  // SW - NW
            new Check(sortedHits.get(2), sortedHits.get(3), -STEP, 0), // SE - SW
            new Check(sortedHits.get(0), sortedHits.get(2), 0, -STEP)  // NE - SE
        ).allMatch(Check::isValid);
    }

}
