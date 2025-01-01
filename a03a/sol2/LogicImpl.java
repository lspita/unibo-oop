package a03a.sol2;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class LogicImpl implements Logic {

    private final int height;
    private final int width;
    private final Set<Position> marked = new HashSet<>();
    private final Random random = new Random();
    private final Position goal;

    public LogicImpl(int width, int height) {
        this.width = width;
        this.height = height;
        this.resetMarks();
        this.goal = new Position(this.width-1, random.nextInt(this.height));
    }

    private void resetMarks() {
        this.marked.clear();
    }

    @Override
    public void hit(Position position) {
        this.resetMarks();
        this.marked.add(position);
        var p = position;
        var yDirection = -1;
        for (int x = position.x()+1; x < this.width; x++){
            p = new Position(x, p.y() + yDirection);
            this.marked.add(p);
            if (p.y() == 0 || p.y() == this.height - 1){
                yDirection *= -1;
            }
        }
    }

    @Override
    public boolean isMarked(Position position) {
        return this.marked.contains(position);
    }

    @Override
    public boolean isOver() {
        return this.marked.contains(this.goal);
    }

    @Override
    public Position getGoal() {
        return this.goal;
    }
}
