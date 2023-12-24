package a01d.sol2;

import java.util.Optional;

public class LogicImpl implements Logic {

    private final int size;
    private Optional<Position> position = Optional.empty();

    public LogicImpl(int size) {
        this.size = size;
    }

    @Override
    public boolean hit(Position position) {
        if (this.position.isEmpty()){
            this.position = Optional.of(position);
            return true;
        }
        var newP = getDirection(position).flatMap(p -> this.position.map(q -> new Position(p.x()+q.x(), p.y()+q.y())));
        if (newP.isPresent()){
            this.position = newP;
            return true;
        }
        return false;
    }

    private Optional<Position> getDirection(Position position){
        if (position.x() == 0){
            return Optional.of(new Position(-1, 0));
        }
        if (position.x() == this.size-1){
            return Optional.of(new Position(1, 0));
        }
        if (position.y() == 0){
            return Optional.of(new Position(0, -1));
        }
        if (position.y() == this.size-1){
            return Optional.of(new Position(0, +1));
        }
        return Optional.empty();
    }

    private boolean neighbours(Position p1, Position p2){
        return Math.abs(p1.x()-p2.x()) <= 2 && Math.abs(p1.y()-p2.y()) <= 2;
    }

    @Override
    public boolean getMark(Position position) {
        return this.position.isPresent() && this.neighbours(position, this.position.get());
    }

    @Override
    public boolean isOver() {
        return this.position.isPresent() && (!this.inRange(this.position.get().x()) || !this.inRange(this.position.get().y()));
    }

    private boolean inRange(int x) {
        return x >= 2 && x < this.size-2;
    }
}
