package a02c.sol2;

import java.util.function.*;

public record Position(int x, int y){
    
    public Position add(Position p){
        return new Position(x + p.x(), y + p.y());
    }

    public boolean holdInBoth(Predicate<Integer> pred){
        return pred.test(x) && pred.test(y);
    }
}
