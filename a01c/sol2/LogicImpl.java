package a01c.sol2;

import java.util.*;

public class LogicImpl implements Logic {

    private final List<Position> marks = new LinkedList<>();

    @Override
    public Optional<Integer> hit(Position position) {
        if (this.marks.size()==4){
            this.reset();
        }
        this.marks.add(position);
        if (this.areMarksCorrect()) {
            return Optional.of(this.marks.size());
        }
        this.reset();
        return Optional.empty();
    }
    
    private boolean areMarksCorrect() {
        return switch (this.marks.size()){
            case 1 -> true;
            case 2 -> this.marks.get(0).y() == this.marks.get(1).y() && 
                    this.marks.get(0).x() < this.marks.get(1).x();
            case 3 -> this.marks.get(1).x() == this.marks.get(2).x() && 
                    this.marks.get(1).y() < this.marks.get(2).y();
            case 4 -> this.marks.get(2).y() == this.marks.get(3).y() && 
                    this.marks.get(2).x() > this.marks.get(3).x();
            default -> false;
        };
    }

    @Override
    public void reset() {
        this.marks.clear();
    }

    @Override
    public boolean isSelected(Position value) {
        return this.marks.size() ==4 && 
                value.x() > this.marks.stream().map(Position::x).min(Integer::compareTo).get() &&
                value.x() < this.marks.stream().map(Position::x).max(Integer::compareTo).get() &&
                value.y() > this.marks.stream().map(Position::y).min(Integer::compareTo).get() &&
                value.y() < this.marks.stream().map(Position::y).max(Integer::compareTo).get();
    }

    @Override
    public boolean isOver() {
        return this.marks.size()==4;
    }
}