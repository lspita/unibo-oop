package a02c.e2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.random.RandomGenerator;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {
    
    private static final RandomGenerator RANDOM = RandomGenerator.getDefault();

    private Position ball;
    private final Set<Position> walls = new HashSet<>();
    private final int size;

    public LogicsImpl(final int size) {
        this.size = size;
        resetBall();
        while (walls.size() < 20) {
            walls.add(new Position(RANDOM.nextInt(size), RANDOM.nextInt(1, size)));
        }
    }

    @Override
    public boolean next() {
        final var leftRight = new ArrayList<Position>(2);
        leftRight.add(new Position(ball.x() - 1, ball.y() + 1));
        leftRight.add(new Position(ball.x() + 1, ball.y() + 1));
        Collections.shuffle(leftRight);
        final var nextPos = Stream.of(
                new Position(ball.x(), ball.y() + 1),
                leftRight.getFirst(),
                leftRight.getLast()
            ).filter(this::isInBounds)
            .filter(p -> getCell(p) == Cell.EMPTY)
            .findFirst();
        nextPos.ifPresent(n -> {
            ball = n;
            if (ball.y() == size - 1) {
                resetBall();
            }
        });
        return nextPos.isEmpty();
    }

    private void resetBall() {
        ball = new Position(RANDOM.nextInt(size), 0);
    }

    private boolean isInBounds(final Position pos) {
        return
            pos.x() >= 0 && pos.x() < size &&
            pos.y() >= 0 && pos.y() < size;
    }

    @Override
    public Cell getCell(final Position position) {
        if (position.equals(ball)) {
            return Cell.BALL;
        }
        if (walls.contains(position)) {
            return Cell.WALL;
        }
        return Cell.EMPTY;
    }

}
