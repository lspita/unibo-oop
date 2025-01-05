package a02b.e2;

public interface Logics {
 
    record Position(int x, int y) {}

    enum Cell {
        MAIN,
        LEFT,
        RIGHT,
        EMPTY
    }

    boolean hit(Position position);

    Cell getCell(Position position);

}
