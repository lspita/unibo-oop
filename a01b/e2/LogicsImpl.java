package a01b.e2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {
    
    private static final int N_ANGLES = 3;

    private final int size;
    private final List<Position> angles = new ArrayList<>(N_ANGLES);

    public LogicsImpl(final int size) {
        this.size = size;
    }

    @Override
    public List<Position> hit(final Position position) {
        checkPosition(position);

        if (
            angles.size() == 0 ||
            (angles.size() == 1 && checkSameLine(angles.getFirst(), position)) ||
            (
                angles.size() > 1 &&
                angles.size() < N_ANGLES && 
                checkPerpendicular(angles.get(angles.size() - 2), angles.getLast(), position)
            )
        ) {
            angles.add(position);
        }

        return Collections.unmodifiableList(angles);
    }

    @Override
    public Optional<List<Position>> getComputed() {
        if (angles.size() < N_ANGLES) {
            return Optional.empty();
        }

        return Optional.of(
            Stream.iterate(0, i -> i < angles.size() - 1, i -> i + 1)
                .flatMap(
                    i -> {
                        final var angleX = Stream.of(angles.get(i).x(), angles.get(i + 1).x()).sorted().toList();
                        final var angleY = Stream.of(angles.get(i).y(), angles.get(i + 1).y()).sorted().toList();
                        return Stream.iterate(angleX.get(0), x -> x <= angleX.get(1), x -> x + 1)
                            .flatMap(
                                x -> Stream.iterate(angleY.get(0), y -> y <= angleY.get(1), y -> y + 1)
                                    .map(y -> new Position(x, y))
                            );
                    }
                )
            .filter(pos -> !angles.contains(pos))
            .toList()
        );
    }

    private boolean checkSameLine(final Position pivot, final Position position) {
        return position.x() == pivot.x() || position.y() == pivot.y();
    }

    private boolean checkPerpendicular(final Position pivot1, final Position pivot2, final Position position) {
        return (
            (pivot1.x() != pivot2.x() && (position.x() == pivot1.x() || position.x() == pivot2.x())) ||
            (pivot1.y() != pivot2.y() && (position.y() == pivot1.y() || position.y() == pivot2.y()))
        );
    }

    private boolean isOutOfBounds(final Position position) {
        return
            position.x() < 0 || position.x() >= size ||
            position.y() < 0 || position.y() >= size;
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
