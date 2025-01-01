package a05.sol2;

public interface Logic {

    boolean hit(Position position);

    Position getPlayer();

    void moveEnemy();

    Position getEnemy();

    boolean isOver();

}
