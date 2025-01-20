package a03b.e2;

public interface Logics {
    
    enum Cell {
        EMPTY,
        ACTIVE,
        TARGET
    }

    boolean hit(Position position);

    Cell getCell(Position position);

}
