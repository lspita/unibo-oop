package a01a.e2;

import java.util.List;
import java.util.Optional;

public interface Logics {
    
    List<Position> hit(Position position);

    Optional<List<Position>> getVertexes();

    Optional<List<Position>> getRectangle();

}
