package a01b.e2;

public class Cell extends Pair<Integer, Integer> {
    
    public Cell(final int x, final int y) {
        super(x, y);
    }

    public Cell getLeft() {
        return new Cell(this.getX() - 1, this.getY());
    }

    public Cell getRight() {
        return new Cell(this.getX() + 1, this.getY());
    }

}
