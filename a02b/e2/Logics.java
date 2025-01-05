package a02b.e2;

import java.util.List;

public interface Logics {
    
    record Position(int x, int y) implements Comparable<Position> {

        @Override
        public int compareTo(final Position arg0) {
            final var xCompare = Integer.compare(x, arg0.x);
            return xCompare == 0 ? Integer.compare(y, arg0.y) : xCompare;
        }
    }

    boolean hit(Position position);

    List<Position> getActiveCells();

}
