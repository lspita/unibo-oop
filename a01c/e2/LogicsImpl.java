package a01c.e2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {
    
    enum Axis {
        ANY,
        X,
        Y
    }

    private final List<Position> pivots = new ArrayList<>();
    private Optional<Axis> currentAxis = Optional.empty();

    @Override
    public void hit(final Position position) {
        if (currentAxis.isEmpty()) {
            pivots.add(position);
            currentAxis = Optional.of(Axis.ANY);
            return;
        }

        final var newAxis = switch (currentAxis.get()) {
            case ANY -> checkAnyAxis(position);
            case X -> checkXAxis(position);
            case Y -> checkYAxis(position);          
        };
        newAxis.ifPresent(a -> {
            pivots.add(position);
            currentAxis = Optional.of(a);
        });
    }

    @Override
    public boolean isActive(final Position position) {
        return pivots.contains(position) || rangeStream(0, pivots.size() - 1)
            .anyMatch(i -> {
                final var pair = Stream.of(pivots.get(i), pivots.get(i + 1)).sorted().toList();
                return 
                    position.x() >= pair.get(0).x() && position.x() <= pair.get(1).x() &&
                    position.y() >= pair.get(0).y() && position.y() <= pair.get(1).y();
            });
    }

    private Stream<Integer> rangeStream(final int start, final int end) {
        return Stream.iterate(start, i -> i < end, i -> i + 1);
    }

    private Optional<Axis> checkAnyAxis(final Position position) {
        final var result = checkXAxis(position);
        return result.isPresent() ? result : checkXAxis(position);
    }

    private Optional<Axis> checkXAxis(final Position position) {
        return sameCoordinateAsLastPivot(position, Position::x) ? Optional.of(Axis.Y) : Optional.empty();
    }

    private Optional<Axis> checkYAxis(final Position position) {
        return sameCoordinateAsLastPivot(position, Position::y) ? Optional.of(Axis.X) : Optional.empty();
    }

    private boolean sameCoordinateAsLastPivot(final Position position, final Function<Position, Integer> coordinateMapper) {
        return coordinateMapper.apply(pivots.getLast()) == coordinateMapper.apply(position);
    }

}
