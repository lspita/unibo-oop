package a02c.e2;

import java.util.List;

public interface Logics {
    
    record Position(int x, int y) implements Comparable<Position> {
        @Override
        public int compareTo(Position arg0) {
            final var yCompare = Integer.compare(y, arg0.y);
            return yCompare == 0 ? Integer.compare(x, arg0.x) : yCompare;
        }
    }

    List<Position> hit(Position position);

    boolean isOver();

}
