package a01c.sol2;

import java.util.Optional;

public interface Logic {

    Optional<Integer> hit(Position position);

    boolean isSelected(Position value);

    boolean isOver();

    void reset();

}
