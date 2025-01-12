package a01a.e2;

public record Position(int x, int y) {

    public int compareX(final Position other) {
        return Integer.compare(x, other.x);
    }
    public int compareY(final Position other) {
        return Integer.compare(y, other.y);
    }

    public Position sum(final int x, final int y) {
        return new Position(this.x + x, this.y + y);
    }
}
