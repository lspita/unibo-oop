package a02a.e2;

public interface Logics {
    
    enum Cell {
        BISHOP,
        BLOCKED,
        EMPTY
    }

    record Position(int x, int y) {}

    void hit(Position position);

    Cell getType(Position position);

}
