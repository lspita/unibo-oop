package a01c.sol2;

import java.util.Optional;

public interface Logic {

    Optional<Integer> hit(Position position); 

    Optional<Integer> getMark(Position position);

    boolean isOver();
}
