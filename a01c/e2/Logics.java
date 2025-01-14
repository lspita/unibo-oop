package a01c.e2;

import java.util.Optional;

public interface Logics {
    
    boolean hit(Position position);

    Optional<Integer> getVertexIndex(Position position);

    boolean isActive(Position position);

}
