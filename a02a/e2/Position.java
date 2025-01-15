package a02a.e2;

public record Position(int x, int y) implements Comparable<Position> {

    @Override
    public int compareTo(final Position o) {
        final var yCompare = Integer.compare(y, o.y);
        return yCompare == 0 ? Integer.compare(x, o.x) : yCompare;
    }

}
