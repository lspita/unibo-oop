package a01b.e2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LogicsImpl implements Logics {

    private enum Status {
        SELECTING,
        LEFT,
        RIGHT
    }

    private static final int MAX_CELLS = 5;
    private List<Cell> activeCells = new ArrayList<>(MAX_CELLS);
    private Status status = Status.SELECTING;
    
    private final int size;

    LogicsImpl(final int size) {
        this.size = size;
    }

    @Override
    public void hitCell(Cell cell) {
        if (this.isOver()) {
            return;
        }

        switch (this.status) {
            case SELECTING -> {
                if (this.activeCells.contains(cell)) {
                    return;
                }

                this.activeCells.add(cell);
                if (this.activeCells.size() == MAX_CELLS) {
                    this.status = Status.LEFT;
                }
            }
            case LEFT -> {
                this.activeCells = this.activeCells.stream()
                    .map(Cell::getLeft)
                    .toList();
                if (this.activeCells.stream().anyMatch(this::isOnBorder)) {
                    this.status = Status.RIGHT;
                }
            }
            case RIGHT -> {
                this.activeCells = this.activeCells.stream()
                    .map(Cell::getRight)
                    .toList();
            }
        }

    }

    @Override
    public Optional<Integer> getCellValue(Cell cell) {
        return this.activeCells.stream()
            .filter(cell::equals)
            .findFirst()
            .map(c -> this.activeCells.indexOf(c) + 1);
    }

    private boolean isOutOfBounds(final Cell cell) {
        return (
            cell.getX() < 0 || cell.getX() > this.size - 1 ||
            cell.getY() < 0 || cell.getY() > this.size - 1
        );
    }

    private boolean isOnBorder(final Cell cell) {
        return (
            cell.getX() == 0 || cell.getX() == this.size - 1 ||
            cell.getY() == 0 || cell.getY() == this.size - 1
        );
    }

    @Override
    public boolean isOver() {
        return this.status.equals(Status.RIGHT) && this.activeCells.stream().anyMatch(this::isOutOfBounds);
    }
    
}
