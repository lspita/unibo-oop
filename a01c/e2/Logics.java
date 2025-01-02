package a01c.e2;

import java.util.Optional;

public interface Logics {

    enum CellType {
        VERTEX_1,
        VERTEX_2,
        EXPANDED
    }

    void hit(Position position);

    Optional<CellType> getType(Position position);

    boolean isOver();

}
