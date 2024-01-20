package a02c.sol2;

import java.util.*;
import java.util.function.*;

public class LogicImpl implements Logic {

    private Optional<Position> cornerMin = Optional.empty();
    private Optional<Position> cornerMax = Optional.empty();
    private final int size;

    public LogicImpl(int size) {
        this.size = size;
    }

    @Override
    public boolean hit(Position position) {
        if (this.cornerMin.isEmpty() && position.holdInBoth(i -> i > 1 && i < this.size - 2)) {
            this.cornerMin = Optional.of(position.add(new Position(-1, -1)));
            this.cornerMax = Optional.of(position.add(new Position(1, 1)));
            return true;
        }
        if (this.inCorner(position)) {
            this.updateCorners(position);
            return true;
        }
        return false;
    }

    private boolean checkCorners(BiPredicate<Position, Position> condition) {
        return this.cornerMin.flatMap(min -> this.cornerMax.map(max -> condition.test(min, max))).get();
    }

    private void updateCorners(Position position) {
        Position min = cornerMin.get();
        Position max = cornerMax.get();
        if (min.x() == position.x()) {
            min = min.add(new Position(-1, 0));
        }
        if (min.y() == position.y()) {
            min = min.add(new Position(0, -1));
        }
        if (max.x() == position.x()) {
            max = max.add(new Position(1, 0));
        }
        if (max.y() == position.y()) {
            max = max.add(new Position(0, 1));
        }
        this.cornerMin = Optional.of(min);
        this.cornerMax = Optional.of(max);
    }

    private boolean inCorner(Position position) {
        return checkCorners((min, max) -> List.of(min.x(), max.x()).contains(position.x())
                && Set.of(min.y(), max.y()).contains(position.y()));
    }

    @Override
    public boolean isEnbled(Position position) {
        return checkCorners((min, max) -> List.of(min.x(), max.x()).contains(position.x()) && inRange(position.y(), min.y(), max.y())) ||
        checkCorners((min, max) -> List.of(min.y(), max.y()).contains(position.y()) && inRange(position.x(), min.x(), max.x()));
    }

    private boolean inRange(int e, int min, int max) {
        return e >= min && e <= max;
    }

    @Override
    public boolean isOver() {
        return checkCorners((min, max) -> List.of(min.x(), min.y()).contains(0) || List.of(max.x(), max.y()).contains(this.size-1));
    }
}
