package a01c.e2;

public interface Logics {
    
    record Position(int x, int y) implements Comparable<Position> {

        @Override
        public int compareTo(final Position arg0) {
            final var yCompare = Integer.compare(y, arg0.y);
            return yCompare == 0 ? Integer.compare(x, arg0.x) : yCompare;
        }

    }

    void hit(Position position);

    boolean isActive(Position position);

}
