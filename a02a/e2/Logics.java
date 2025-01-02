package a02a.e2;

import java.util.Optional;

public interface Logics {
    
    enum CellType {
        BLOCKED,
        ACTIVE
    }

    void hit(Position position);

    Optional<CellType> getType(Position position);

    boolean isOver();

}
