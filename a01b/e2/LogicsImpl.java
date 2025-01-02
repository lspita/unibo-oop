package a01b.e2;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {
    private static final int MAX_ACTIVE_CELLS = 5;

    enum Status {
        SELECTING,
        MOVING_LEFT,
        MOVING_RIGHT
    }
    
    private Status status = Status.SELECTING;
    private final int lowerBound = 0;
    private final int upperBound;
    private List<Position> activePositions = Collections.emptyList();

    public LogicsImpl(final int size) {
        this.upperBound = size - 1;
    }

    @Override
    public List<Position> hit(Position position) {
        if (this.isOver()) {
            throw new IllegalStateException("Game is already over");
        }
        if (this.isOutOfBounds(position)) {
            throw new IllegalArgumentException(new StringBuilder("Position ").append(position).append(" is out of bounds").toString());
        }

        this.updateStatus();
        this.activePositions = switch (this.status) {
            case SELECTING -> this.addPosition(position);
            case MOVING_LEFT -> this.moveLeft();
            case MOVING_RIGHT -> this.moveRight();
        };
        return this.activePositions.stream().filter(Predicate.not(this::isOutOfBounds)).toList();
    }

    @Override
    public boolean isOver() {
        return this.status == Status.MOVING_RIGHT && this.activePositions.stream().anyMatch(this::isOutOfBounds);
    }

    private boolean isOutOfBounds(final Position position) {
        return 
            position.x() < this.lowerBound || position.x() > this.upperBound ||
            position.y() < this.lowerBound || position.y() > this.upperBound;
    }

    private boolean isOnLeftBorder(final Position position) {
        return position.x() == this.lowerBound;
    }

    private void updateStatus() {
        if (this.status == Status.SELECTING && this.activePositions.size() == MAX_ACTIVE_CELLS) {
            this.status = Status.MOVING_LEFT;
        }
        else if (this.status == Status.MOVING_LEFT && this.activePositions.stream().anyMatch(this::isOnLeftBorder)) {
            this.status = Status.MOVING_RIGHT;
        }
    }

    private List<Position> addPosition(final Position position) {
        return this.activePositions.stream().anyMatch(position::equals) ? 
            this.activePositions : 
            Stream.concat(this.activePositions.stream(), Stream.of(position)).toList();
    }
    
    private List<Position> moveLeft() {
        return this.activePositions.stream()
            .map(pos -> new Position(pos.x() - 1, pos.y()))
            .toList();
    }
    
    private List<Position> moveRight() {
        return this.activePositions.stream()
            .map(pos -> new Position(pos.x() + 1, pos.y()))
            .toList();
    }
    
}
