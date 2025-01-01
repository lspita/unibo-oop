package a01b.sol2;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LogicImpl implements Logic {

    enum Move {
        LEFT, RIGHT
    }

    private final static int NUMBER_OF_MARKS = 5;
    private Move direction = Move.LEFT;
    private final int size;
    private List<Position> marks = new LinkedList<>();

    public LogicImpl(int size) {
        this.size = size;
    }

    @Override
    public Optional<Integer> hit(Position position) {
        if (this.isOver()){
            return Optional.empty();
        }
        if (moving()){
            this.moveMarks();
            return Optional.empty();
        }
        if (this.marks.contains(position)){
            return Optional.of(this.marks.indexOf(position));
        } 
        this.marks.add(position);
        return Optional.of(this.marks.size());
    }

    private boolean moving() {
        return this.marks.size() == NUMBER_OF_MARKS;
    }

    private void moveMarks() {
        if (this.direction == Move.LEFT && this.marks.stream().anyMatch(p -> p.x()==0)){
            this.direction = Move.RIGHT;
        }
        this.marks = this.marks
                .stream()
                .map(p -> new Position(p.x()+ (this.direction == Move.LEFT ? -1 : +1), p.y()))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<Integer> getMark(Position position) {
        return Optional.of(this.marks.indexOf(position)).filter(i -> i>=0).map(i -> i+1);
    }

    @Override
    public boolean isOver() {
        return this.direction == Move.RIGHT && this.marks.stream().anyMatch(p -> p.x() == this.size);
    }
}
