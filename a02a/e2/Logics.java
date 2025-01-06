package a02a.e2;

import java.util.Optional;

public interface Logics {
    
    record Position(int x, int y) {
        public Position sum(final Position other) {
            return new Position(this.x + other.x, this.y + other.y);
        }
    }

    boolean next();

    Optional<Integer> getValue(Position position);

}
