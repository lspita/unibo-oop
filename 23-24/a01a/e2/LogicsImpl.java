package a01a.e2;

import java.util.ArrayList;
import java.util.List;

public class LogicsImpl implements Logics {

    private List<Cell> activeCells = new ArrayList<>();
    private boolean moving = false;
    private final int size;

    LogicsImpl(final int size) {
        this.size = size;
    }

    @Override
    public List<Cell> hitCell(final Cell cell) {
        if (this.moving || this.activeCells.stream().anyMatch(cell::isAdjacent)) {
            this.moving = true;
            this.activeCells = this.activeCells.stream()
                .map(c -> new Cell(c.getX() + 1, c.getY() - 1))
                .toList();
        }
        else {
            this.activeCells.add(cell);
        }
        
        return this.activeCells.stream().filter(this::isInsideBounds).toList();
    }

    @Override
    public boolean toQuit() {
        return this.activeCells.stream().anyMatch(this::isOutOfBounds);
    }

    private boolean isOutOfBounds(final Cell cell) {
        return cell.isOutOfBounds(this.size);
    }

    private boolean isInsideBounds(final Cell cell) {
        return !cell.isOutOfBounds(this.size);
    }
    
}
