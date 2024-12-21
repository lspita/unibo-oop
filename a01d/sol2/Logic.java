package a01d.sol2;

public interface Logic {

    boolean hit(Position position); // yields false if we are starting to move selections

    boolean getMark(Position position);

    boolean isOver();
}
