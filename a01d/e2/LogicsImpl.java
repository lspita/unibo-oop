package a01d.e2;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {
    
    enum Border {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }
    private static final Map<Border, Position> MOVEMENTS = Map.of(
        Border.TOP, new Position(0, -1),
        Border.BOTTOM, new Position(0, 1),
        Border.LEFT, new Position(-1,  0),
        Border.RIGHT, new Position(1, 0)
    );
    private static final int SQUARE_HALF_SIZE = 2; // 2 + 1 + 2

    private final int size;
    private Optional<Position> center = Optional.empty();

    public LogicsImpl(final int size) {
        this.size = size;
    }

    @Override
    public boolean hit(final Position position) {
        center.ifPresentOrElse(
            c -> {
                final var border = getBorder(position);
                border.ifPresent(b -> {
                    center = Optional.of(c.sum(MOVEMENTS.get(b)));
                });
            },
            () -> { center = Optional.of(position); } 
        );

        return getSquare().stream().anyMatch(this::isOnBorder);
    }

    @Override
    public boolean isActive(final Position position) {
        return getSquare().contains(position);
    }

    private Optional<Border> getBorder(final Position position) {
        if (position.x() == 0) {
            return Optional.of(Border.LEFT);
        } else if (position.x() == size - 1) {
            return Optional.of(Border.RIGHT);
        } else if (position.y() == 0) {
            return Optional.of(Border.TOP);
        } else if (position.y() == size - 1) {
            return Optional.of(Border.BOTTOM);
        }
        return Optional.empty();
    }

    private boolean isOnBorder(final Position position) {
        return getBorder(position).isPresent();
    }

    private List<Position> getSquare() {
        if (center.isEmpty()) {
            return Collections.emptyList();
        }

        return center.map(
            c -> Stream.iterate(c.x() - SQUARE_HALF_SIZE, x -> x <= c.x() + SQUARE_HALF_SIZE, x -> x + 1)
                .flatMap(
                    x -> Stream.iterate(c.y() - SQUARE_HALF_SIZE, y -> y <= c.y() + SQUARE_HALF_SIZE, y -> y + 1)
                        .map(y -> new Position(x, y))
                ).toList()
        ).get();
    }

}
