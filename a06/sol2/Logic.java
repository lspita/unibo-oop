package a06.sol2;

import java.util.*;

public interface Logic {

    void hit(Position p);

    Optional<Integer> found(Position p);

    Optional<Integer> temporary(Position p);

    boolean isOver();

}
