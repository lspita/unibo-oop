package a01b.sol2;

public interface Logic {

    boolean hit(Position position);

    boolean isSelected(Position value);

    boolean isOver();

    void reset();

}
