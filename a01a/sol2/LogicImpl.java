package a01a.sol2;

import java.util.*;

public class LogicImpl implements Logic {

    private final Set<Position> marks = new HashSet<>();
    private boolean over = false;

    @Override
    public boolean hit(Position position) {
        if (this.marks.contains(position)){
            this.over = true;
            return false;
        }
        this.marks.add(position);
        return true;
    }

    @Override
    public void reset() {
        this.over = false;
        this.marks.clear();
    }

    private Position v1() {
        return new Position(
            this.marks.stream().map(Position::x).min(Integer::compareTo).get()-1,
            this.marks.stream().map(Position::y).min(Integer::compareTo).get()-1
        );
    }

    private Position v2() {
        return new Position(
            this.marks.stream().map(Position::x).max(Integer::compareTo).get()+1,
            this.marks.stream().map(Position::y).max(Integer::compareTo).get()+1
        );
    }

    @Override
    public boolean isSelected(Position value) {
        return !isVertex(value) && ( 
                    ((value.x() == v1().x() || value.x() == v2().x()) && 
                    value.y() >= v1().y() && value.y() <= v2().y()) 
                ||
                    ((value.y() == v1().y() || value.y() == v2().y()) && 
                    value.x() >= v1().x() && value.x() <= v2().x())
                );  
    }

    @Override
    public boolean isOver() {
        return this.over;
    }

    @Override
    public boolean isVertex(Position value) {
        return value.equals(v1()) || value.equals(v2()) ||
            value.equals(new Position(v1().x(), v2().y())) ||
            value.equals(new Position(v2().x(), v1().y()));      
    }
}