package a01b.sol2;

import java.util.*;

public class LogicImpl implements Logic {

    private List<Position> vertices = new LinkedList<>();

    @Override
    public boolean hit(Position position) {
        if (this.vertices.size()==2){
            this.reset();
        }
        this.vertices.add(position);
        if (this.areVerticesCorrect()) {
            return true;
        }
        this.reset();
        return false;
    }
    
    private boolean areVerticesCorrect() {
        return switch (this.vertices.size()){
            case 1 -> true;
            case 2 -> this.vertices.get(0).x() == this.vertices.get(1).x() && 
                    (this.vertices.get(0).y() - this.vertices.get(1).y()) % 2 == 0;
            default -> false;
        };
    }

    @Override
    public void reset() {
        this.vertices.clear();
    }

    @Override
    public boolean isSelected(Position value) {
        var up = this.vertices.get(this.vertices.get(0).y() > this.vertices.get(1).y() ? 1 : 0);
        var down = this.vertices.get(this.vertices.get(0).y() > this.vertices.get(1).y() ? 0 : 1);
        return this.vertices.size() == 2 &&
                value.y() > up.y() && value.y() < down.y() && 
                value.x()-value.y() <= up.x()-up.y() && 
                value.x()-value.y() >= down.x()-down.y() &&
                value.x()+value.y() >= up.x()+up.y() && 
                value.x()+value.y() <= down.x()+down.y(); 
    }

    @Override
    public boolean isOver() {
        return this.vertices.size()==2;
    }
}