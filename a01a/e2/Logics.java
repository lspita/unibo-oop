package a01a.e2;

public interface Logics {
    
    record Position(int x, int y) {}

    enum CellState {
        PIN,
        ACTIVE,
        EMPTY,
        BLOCKED
    }

    void hit(Position position);

    CellState getState(Position position);

}
