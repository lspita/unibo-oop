package a02c.e2;

public record Position(int x, int y) {
    
    public Position sum(final Position other) {
        return new Position(x + other.x, y + other.y);
    }

    public Position sumX(final Position other) {
        return sum(new Position(other.x, 0));
    }

    public Position sumY(final Position other) {
        return sum(new Position(0, other.y));
    }

}
