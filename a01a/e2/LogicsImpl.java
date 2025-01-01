package a01a.e2;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {

    enum Status {
        ADDING,
        MOVING
    }

    private static final int LOWER_BOUND = 0;
    private final int size;
    private List<Position> activePositions = new LinkedList<>();
    private Status status = Status.ADDING;

    public LogicsImpl(final int size) {
        this.size = size;
    }

    private boolean isOutOfBounds(final Position position) {
        return 
            position.x() < LOWER_BOUND || position.x() >= this.size ||
            position.y() < LOWER_BOUND || position.y() >= this.size;
    }

    private Position northEastNeighbour(final Position position) {
        return new Position(position.x() + 1, position.y() - 1);
    }

    private Stream<Position> neighboursStream(final Position position) {
        return Stream.iterate(position.x() - 1, x -> x <= position.x() + 1, x -> x + 1)
            .flatMap(
                x -> Stream.iterate(position.y() - 1, y -> y <= position.y() + 1, y -> y + 1)
                        .map(y -> new Position(x, y))
            )
            .filter(Predicate.not(position::equals));
    }

    @Override
    public List<Position> hit(final Position position) {
        if (this.isOver()) {
            throw new IllegalStateException("Game is already over");
        }
        if (this.isOutOfBounds(position)) {
            throw new IllegalArgumentException(
                new StringBuilder()
                    .append("Positon ")
                    .append(position)
                    .append(" is out of bounds [")
                    .append(LOWER_BOUND)
                    .append(", ")
                    .append(this.size - 1)
                    .append("]")
                    .toString()
            );
        }

        switch (this.updateStatus(position)) {
            case ADDING -> this.addPostion(position);
            case MOVING -> this.movePositions();
        }

        return this.activePositions.stream().filter(Predicate.not(this::isOutOfBounds)).toList();
    }

    private Status updateStatus(final Position position) {
        if (
            this.status == Status.ADDING &&
            this.activePositions.stream()
                .flatMap(this::neighboursStream)
                .anyMatch(position::equals)
        ) {
            this.status = Status.MOVING;
        }

        return this.status;
    }

    private void addPostion(final Position position) {
        this.activePositions.add(position);
    }

    private void movePositions() {
        this.activePositions = this.activePositions.stream()
            .map(this::northEastNeighbour)
            .toList();
    } 

    @Override
    public boolean isOver() {
        return 
            this.status == Status.MOVING &&
            this.activePositions.stream().anyMatch(this::isOutOfBounds);
    }
    
}
