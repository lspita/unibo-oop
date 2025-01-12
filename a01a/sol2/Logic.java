package a01a.sol2;

public interface Logic {

    boolean hit(Position position);

    boolean isSelected(Position value);

    boolean isVertex(Position value);

    boolean isOver();

    void reset();

}
