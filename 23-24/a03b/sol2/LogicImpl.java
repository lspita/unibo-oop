package a03b.sol2;

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
        this.goal = new Position(random.nextInt(this.width), random.nextInt(this.height));
    }

    private void resetMarks() {
        this.marked.clear();
    }

    @Override
    public void hit(Position position) {
        this.marked.add(position);
        for (int x = 1; x < this.width; x++){
            Set<Position> column = new HashSet<>();
            for (int y = 0; y < this.height; y++){
                if (Math.abs(position.y() - y) <= x){
                    column.add(new Position(position.x() + x, y));
                }
            }
            this.marked.addAll(column);
            if (column.stream().filter(p -> p.y() == 0 || p.y() == this.height-1).findAny().isPresent()){
                return;
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
