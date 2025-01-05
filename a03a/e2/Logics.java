package a03a.e2;

import java.util.List;

public interface Logics {
    
    record Position(int x, int y) {}

    enum Cell {
        TRAIL,
        GOAL,
        EMPTY
    }

    boolean shoot(Position from);

    Cell getType(Position position);

}
