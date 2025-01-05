package a03a.e2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {

    private static final RandomGenerator RANDOM = RandomGenerator.getDefault();

    private final Position goal;
    private final int width;
    private final int height;
    private List<Position> trail = Collections.emptyList();
    
    public LogicsImpl(final int width, final int height) {
        this.width = width;
        this.height = height;
        goal = new Position(width - 1, RANDOM.nextInt(height));
    }

    @Override
    public boolean shoot(final Position from) {
        var movement = new Position(1, onTopBorder(from) ? 1 : -1);
        trail = new ArrayList<>();
        trail.add(from);
        while (trail.getLast().x() != goal.x()) {
            final var lastPosition = trail.getLast();
            final var newPosition = new Position(lastPosition.x() + movement.x(), lastPosition.y() + movement.y());
            trail.add(newPosition);
            if (onBottomBorder(newPosition) || onTopBorder(newPosition)) {
                movement = invertY(movement);
            }
        }

        return trail.contains(goal);
    }

    private Position invertY(final Position position) {
        return new Position(position.x(), -position.y());
    }

    private boolean onTopBorder(final Position position) {
        return position.y() == 0;
    }

    private boolean onBottomBorder(final Position position) {
        return position.y() == height - 1;
    }

    @Override
    public Cell getType(final Position position) {
        if (position.equals(goal)) {
            return Cell.GOAL;
        }
        return trail.contains(position) ? Cell.TRAIL : Cell.EMPTY;
    }

}
