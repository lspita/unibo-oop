package a02b.e2;

public interface Logics {
    
    record Position(int x, int y) {}
    
    enum CellState {
        ACTIVE,
        BLOCKED,
        EMPTY
    }

    CellState hit(Position position);

    void checkReset();

    CellState getCell(Position position);

}
