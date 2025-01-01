package a04.sol2;

import java.util.*;
import java.util.stream.Collectors;

public class LogicImpl implements Logic {

    private final int size;
    private Set<Position> marks = new HashSet<>();
    
    public LogicImpl(int size) {
        this.size = size;
        this.marks.add(new Position(new Random().nextInt(size), 0));
    }

    @Override
    public boolean hit(Position position) {
        if (position.y() == 0) {
            return false;
        }
        this.marks.add(position);
        return true;
    }

    @Override
    public boolean isSelected(Position position) {
        return this.marks.contains(position);
    }

    private Set<Position> reachedAtRow(int i){
        if (i == 0){
            return this.marks.stream().filter(p -> p.y() == 0).collect(Collectors.toSet());
        }
        var s = reachedAtRow(i-1);
        System.out.println((i-1) + ":" + s);
        return s.stream().flatMap(p -> this.marks.stream().filter(q -> q.y() == i).filter(q -> Math.abs(p.x() - q.x()) == 1)).collect(Collectors.toSet());
    }

    @Override
    public boolean isOver() {
        return !reachedAtRow(this.size-1).isEmpty();
    }

}
