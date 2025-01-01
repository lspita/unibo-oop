package a02b.sol2;

import java.util.Optional;

public class LogicImpl implements Logic {

    private Optional<Position> position = Optional.empty();
    private boolean isOver = false;
    private final int size;

    public LogicImpl(int size) {
        this.size = size;
    }

    @Override
    public boolean hit(Position position) {
        if (this.position.isEmpty() && position.holdInBoth(i -> i > 2 && i < this.size - 3)){
            this.position = Optional.of(position);
            return true;
        }
        if (this.position.isPresent() && this.inDiagonalProximity(this.position.get().delta(position))){
            this.position = Optional.of(this.targetPosition(this.position.get().delta(position)));
            return true;
        }
        if (this.position.isPresent() && this.position.get().equals(position)){
            this.isOver = true;
            return true;
        }
        return false;
    }

    private Position targetPosition(Position delta) {
        return delta.applyToBoth(x -> x == 1 ? 2 : this.size - 3);
    }

    private boolean inDiagonalProximity(Position delta) {
        return delta.holdInBoth(i -> Math.abs(i) == 1);
    }

    @Override
    public boolean isEnbled(Position position) {
        return this.position.map(p -> this.inBox(p.delta(position))).get();
    }

    private boolean inBox(Position delta) {
        return delta.holdInBoth(i -> Math.abs(i) <= 2) && !inDiagonalProximity(delta);
    }

    @Override
    public boolean isOver() {
        return this.isOver;
    }
}
