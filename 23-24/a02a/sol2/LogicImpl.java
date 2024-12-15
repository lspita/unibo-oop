package a02a.sol2;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LogicImpl implements Logic {

    private final List<Position> dynamic = new LinkedList<>();
    
    private boolean isStatic(Position position){
        return position.x() % 2 == 1 || position.y() % 2 == 1;
    }

    private boolean isDynamic(Position position){
        return this.dynamic.contains(position);
    }

    @Override
    public boolean hit(Position position) {
        if (!this.isStatic(position) && !this.isDynamic(position)){
            this.dynamic.add(position);
            return true;
        }
        return false;
    }


    @Override
    public CellType getMark(Position position) {
        return this.isStatic(position) ? CellType.STATIC : 
                this.isDynamic(position) ? CellType.DYNAMIC : CellType.EMPTY;
    }


    @Override
    public boolean isOver() {
        return this.dynamic.size() >= 4 &&
                this.overRelation(this.dynamic.subList(this.dynamic.size()-4, this.dynamic.size()));
    }

    private boolean overRelation(List<Position> positions) {
        return overRelationSeparated(positions, Position::x) && overRelationSeparated(positions, Position::y);
    }
    
    private boolean overRelationSeparated(List<Position> positions, Function<Position, Integer> projection) {
        var list = positions.stream().map(projection).distinct().sorted().toList();
        return list.size() == 2 && list.get(0).equals(list.get(1) - 2);
    }
}
