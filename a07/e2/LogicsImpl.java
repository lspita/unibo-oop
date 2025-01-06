package a07.e2;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {

    private final Map<Position, Boolean> cells;

    public LogicsImpl(final int size) {
        cells = positionsStream(
                new Position(0, 0), 
                new Position(size - 1, size - 1)
            ).collect(Collectors.toMap(Function.identity(), pos -> false));
    }

    @Override
    public void hit(final Position position) {
        if (isOver() || !isInBounds(position)) {
            return;
        }

        final var square = getSquare(position);
        final var newValue = !isMajorityActive(square);
        square.forEach(pos -> cells.put(pos, newValue));
    }

    @Override
    public boolean isOver() {
        return isMajorityActive(cells.keySet());
    }

    @Override
    public boolean isActive(final Position position) {
        return isInBounds(position) && cells.get(position);
    }

    private boolean isInBounds(final Position position) {
        return cells.containsKey(position);
    }

    private boolean isMajorityActive(final Collection<Position> positions) {
        return positions.stream().filter(cells::get).count() > positions.size() / 2;
    }

    private Stream<Position> positionsStream(final Position min, final Position max) {
        return Stream.iterate(min.x(), x -> x <= max.x(), x -> x + 1)
            .flatMap(
                x -> Stream.iterate(min.y(), y -> y <= max.y(), y -> y + 1)
                    .map(y -> new Position(x, y))
            );
    }

    private List<Position> getSquare(final Position center) {
        return positionsStream(
                new Position(center.x() - 1, center.y() - 1), 
                new Position(center.x() + 1, center.y() + 1)
            ).filter(this::isInBounds)
            .toList();
    }

}
