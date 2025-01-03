package a01a.e2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class LogicsImpl implements Logics {
    
    private static final int DIAGONAL_SIZE = 3;

    private final int size;
    private final List<Position> activePositions = new ArrayList<>();

    public LogicsImpl(final int size) {
        this.size = size;
    }

    @Override
    public List<Position> hit(final Position position) {
        if (isOver()) {
            throw new IllegalStateException("Game is already over");
        }

        if (activePositions.contains(position)) {
            activePositions.remove(position);
        }
        else {
            activePositions.add(position);
        }

        return Collections.unmodifiableList(activePositions);
    }

    @Override
    public boolean isOver() {
        record PositionIndex(Position pos, int index) {}

        if (activePositions.size() < DIAGONAL_SIZE) {
            return false;
        }

        final var positions = activePositions.subList(activePositions.size() - DIAGONAL_SIZE, activePositions.size());
        final var firstPos = positions.get(0);
        return IntStream.range(0, positions.size())
            .mapToObj(i -> new PositionIndex(positions.get(i), i))
            .allMatch(pair -> Math.abs(pair.pos.x() - firstPos.x()) == pair.index && Math.abs(pair.pos.y() - firstPos.y()) == pair.index);
    }

}
