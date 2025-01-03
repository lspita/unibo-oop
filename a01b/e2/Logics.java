package a01b.e2;

import java.util.List;

public interface Logics {
    
    record Position(int x, int y) { }

    List<Position> hit(Position position);

    boolean isOver();

}
