package a06.e2;

import java.util.Optional;

public interface Logics {

    record Position(int x, int y) {}

    boolean fire();

    Optional<Integer> getValue(Position position);

}
