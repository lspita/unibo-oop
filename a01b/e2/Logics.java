package a01b.e2;

import java.util.List;
import java.util.Optional;

public interface Logics {
    
    record Position(int x, int y) {}

    List<Position> hit(Position position);

    Optional<List<Position>> getComputed();

}
