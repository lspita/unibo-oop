package a01b.e2;

import java.util.List;

public interface Logics {
    
    static Logics newDefault(final int size) {
        return new LogicsImpl(size);
    }

    List<Position> hit(Position position);

    boolean isOver();

}
