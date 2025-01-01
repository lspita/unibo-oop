package a03b.sol2;

public interface Logic {

    void hit(Position position);

    boolean isMarked(Position position);

    Position getGoal();

    boolean isOver();
}
