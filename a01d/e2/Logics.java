package a01d.e2;

public interface Logics {
    
    public record Position(int x, int y) {
        public Position sum(final Position other) {
            return new Position(x + other.x, y + other.y);
        }
    }

    boolean hit(Position position);

    boolean isActive(Position position);

}
