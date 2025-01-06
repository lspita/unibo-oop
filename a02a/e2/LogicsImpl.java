package a02a.e2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.random.RandomGenerator;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {
    
    private static final RandomGenerator RANDOM = RandomGenerator.getDefault();
    private static final Position UP = new Position(0, -1);
    private static final Position DOWN = new Position(0, 1);
    private static final Position LEFT = new Position(-1, 0);
    private static final Position RIGHT = new Position(1, 0);


    private final int size;
    private final List<Position> snake = new LinkedList<>();
    private Optional<Position> movement = Optional.of(UP);

    public LogicsImpl(final int size) {
        this.size = size;
    }

    @Override
    public boolean next() {
        if (snake.size() == 0) {
            final var position = new Position(RANDOM.nextInt(0, size), RANDOM.nextInt(0, size));
            snake.add(position);
            return false;
        }

        movement.ifPresent(m -> {
            final var nextPosition = nextPosition(m);
            nextPosition.ifPresent(snake::add);
        });

        return movement.isEmpty();
    }

    @Override
    public Optional<Integer> getValue(final Position position) {
        final var index = snake.indexOf(position);
        return index == -1 ? Optional.empty() : Optional.of(index);
    }

    private Optional<Position> nextPosition(final Position currentMovement) {
        record MovementDestinationPair(Position movement, Position destination) {}

        final var movements = new ArrayList<Position>(4);
        Collections.addAll(movements, UP, DOWN, LEFT, RIGHT);
        Collections.shuffle(movements);
        final var pair = Stream.concat(Stream.of(currentMovement), movements.stream())
            .map(m -> new MovementDestinationPair(m, snake.getLast().sum(m)))
            .filter(p -> isInBounds(p.destination()))
            .filter(p -> !snake.contains(p.destination()))
            .findFirst();
        
        movement = pair.map(MovementDestinationPair::movement);
        return pair.map(MovementDestinationPair::destination);
    }

    private boolean isInBounds(final Position position) {
        return 
            position.x() >= 0 && position.x() < size &&
            position.y() >= 0 && position.y() < size;
    }

}
