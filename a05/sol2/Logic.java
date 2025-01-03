package a05.sol2;

public interface Logic {

    boolean humanMove(int x, int y);

    void computerMove();

    boolean isOver();

    boolean hasHuman(int x, int y);

    boolean hasComputer(int x, int y);

    void reset(); 
}
