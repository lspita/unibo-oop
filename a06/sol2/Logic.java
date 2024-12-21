package a06.sol2;

import java.util.Optional;
import java.util.Set;

public interface Logic {
    
    Optional<Integer> value(int x, int y);

    void fire();

    boolean isOver();
}
