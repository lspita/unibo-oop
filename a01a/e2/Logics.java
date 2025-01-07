package a01a.e2;

import java.util.Optional;

public interface Logics {
    
    record Position(int x, int y) {}

    enum Ending {
        VICTORY,
        GAME_OVER
    }

    boolean fire(Position position);

    Optional<Ending> getGameState();

}
