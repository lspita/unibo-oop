package a01a.e2;

public class Cell extends Pair<Integer, Integer> {
    
    public Cell(final int x, final int y) {
        super(x, y);
    }

    public boolean isOutOfBounds(final int size) {
        return (
            this.getX() < 0 || this.getX() > size - 1 ||
            this.getY() < 0 || this.getY() > size - 1
        );
    }

    public boolean isAdjacent(final Cell other) {
        return (
            other.getX() >= this.getX() - 1 && other.getX() <= this.getX() + 1 &&
            other.getY() >= this.getY() - 1 && other.getY() <= this.getY() + 1
        );
    }
}
