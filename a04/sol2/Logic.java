package a04.sol2;

public interface Logic {

    boolean rookMove(int x, int y);

    void kingMove();

    boolean isOver();

    boolean hasRook(int x, int y);

    boolean hasKing(int x, int y);

    void reset(); 
}
