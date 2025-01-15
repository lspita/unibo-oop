package a02a.e2;

public interface Logics {
    
    enum Cell {
        EMPTY,
        BLOCKED,
        ACTIVE
    }

    boolean hit(Position position);

    Cell getCell(Position position);

}
