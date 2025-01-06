package a07.e2;

public interface Logics {
    
    record Position(int x, int y) {}

    void hit(Position position);

    boolean isOver();

    boolean isActive(Position position);

}
