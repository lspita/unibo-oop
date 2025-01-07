package a01b.e2;

import java.util.Optional;

public interface Logics {
    
    record Position(int x, int y) {}

    Optional<Long> hit(Position position);

}
