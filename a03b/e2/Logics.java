package a03b.e2;

public interface Logics {
    
    record Position(int x, int y) {
        public Position sum(final int x, final int y) {
            return new Position(x + this.x, y + this.y);
        }
    }

    enum Cell {
        PLAYER,
        CPU,
        EMPTY
    }

    void hit(Position position);

    Cell getCell(Position position);

}
