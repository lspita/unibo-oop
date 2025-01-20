package a03b.e2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.random.RandomGenerator;
import java.util.stream.IntStream;

public class LogicsImpl implements Logics {
    
    private static final RandomGenerator RANDOM = RandomGenerator.getDefault();

    private final Position target;
    private final List<List<Position>> shots = new ArrayList<>();
    private final int width;
    private final int height;

    public LogicsImpl(final int width, final int height) {
        this.width = width;
        this.height = height;

        target = new Position(RANDOM.nextInt(width), RANDOM.nextInt(height));
    }

    @Override
    public boolean hit(final Position position) {
        final var shot = new ArrayList<Position>();
        List<Position> lastColumn = Collections.emptyList();
        var currentExpansion = 0;
        do {
            final var x = position.x() + currentExpansion;
            lastColumn = IntStream.rangeClosed(position.y() - currentExpansion, position.y() + currentExpansion)
                .boxed()
                .map(y -> new Position(x, y))
                .toList();
            if (lastColumn.stream().allMatch(Predicate.not(this::isOutOfBounds))) {
                shot.addAll(lastColumn);
            }
            currentExpansion++;
        } while (!lastColumn.stream().anyMatch(p -> isOutOfBounds(p) || hasHitTarget(p)));
        shots.add(shot);
        return lastColumn.stream().anyMatch(this::hasHitTarget);
    }

    @Override
    public Cell getCell(final Position position) {
        if (position.equals(target)) {
            return Cell.TARGET;
        }
        return shots.stream().anyMatch(s -> s.contains(position)) ?
            Cell.ACTIVE :
            Cell.EMPTY;
    }

    private boolean isOutOfBounds(final Position position) {
        return
            position.x() < 0 || position.x() >= width ||
            position.y() < 0 || position.y() >= height;
    }

    private boolean hasHitTarget(final Position position) {
        return position.equals(target);
    }

}
