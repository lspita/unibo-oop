package a02c.e2;

public interface Logics {

    enum Cell {
        BALL,
        WALL,
        EMPTY
    }

    boolean next();

    Cell getCell(Position position);

}