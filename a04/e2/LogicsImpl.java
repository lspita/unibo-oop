package a04.e2;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

public class LogicsImpl implements Logics {

    private static final RandomGenerator RANDOM = RandomGenerator.getDefault();

    private final List<Position> activeCells = new ArrayList<>();
    private final int targetY;

    public LogicsImpl(final int size) {
        this.targetY = size - 1;
        activeCells.add(new Position(RANDOM.nextInt(size), 0));
    }

    @Override
    public boolean hit(final Position position) {
        if (position.y() != 0 && !activeCells.contains(position)) {
            activeCells.add(position);
        }
        return activeCells.stream()
            .filter(p -> p.y() == targetY)
            .map(this::moveUp)
            .anyMatch(e -> e.y() == 0);
    }

    @Override
    public boolean isActive(final Position position) {
        return activeCells.contains(position);
    }

    private Position moveUp(final Position position) {
        if (position.y() == 0) {
            return position;
        }
        final var left = activeCells.stream()
            .filter(p -> p.y() == position.y() - 1 && p.x() == position.x() - 1)
            .findAny()
            .map(this::moveUp);
        final var right = activeCells.stream()
            .filter(p -> p.y() == position.y() - 1 && p.x() == position.x() + 1)
            .findAny()
            .map(this::moveUp);

        if (left.isPresent() && right.isPresent()) {
            return List.of(left.get(), right.get()).stream()
                .sorted()
                .findFirst()
                .get();
        }
        return left.orElse(right.orElse(position));
    }
    
}
