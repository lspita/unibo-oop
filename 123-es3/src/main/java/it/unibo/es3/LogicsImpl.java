package it.unibo.es3;

import java.util.List;
import java.util.random.RandomGenerator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {

    private static final int N_INITIAL_FULL_CELLS = 3;

    private class Cell {
        
        private static final CellState INITIAL_CELL_STATE = CellState.EMPTY;
        
        private CellState state = INITIAL_CELL_STATE;
        private final Pair<Integer, Integer> position;

        public Cell(final Pair<Integer, Integer> position) {
            this.position = position;
        }

        public Pair<Integer, Integer> getPosition() {
            return this.position;
        }

        public CellState getState() {
            return this.state;
        }

        public void setState(final CellState state) {
            this.state = state;
        }

        public boolean adjacent(final Cell other) {
            final var pos = this.getPosition();
            final var otherPos = other.getPosition();
            return (
                otherPos.getX() >= pos.getX() - 1 &&
                otherPos.getX() <= pos.getX() + 1 &&
                otherPos.getY() >= pos.getY() - 1 &&
                otherPos.getY() <= pos.getY() + 1
            );
        }

    }

    private List<Cell> cells;

    LogicsImpl() {};

    @Override
    public void initGrid(final int size) {
        this.cells = Stream.iterate(0, i -> i + 1)
            .limit(size)
            .flatMap(
                i -> IntStream.range(0, size)
                    .mapToObj(j -> new Cell(new Pair<Integer,Integer>(i, j)))
            )
            .toList();
        
        final var rg = RandomGenerator.getDefault();
        rg.ints(N_INITIAL_FULL_CELLS, 0, size * size)
            .forEach(i -> this.cells.get(i).setState(CellState.FULL));
    }

    @Override
    public void expand() {
        this.cells.stream()
            .filter(cell -> cell.getState().equals(CellState.FULL))
            .flatMap(cell -> this.cells.stream().filter(cell::adjacent))
            .toList() // collect cells that need change before changing to avoid adjacent of adjacents
            .forEach(cell -> cell.setState(CellState.FULL));
    }

    @Override
    public List<CellState> getCellStates() {
        return this.cells.stream()
            .map(Cell::getState)
            .toList();
    }

    @Override
    public boolean isGridFull() {
        return this.cells.stream()
            .map(Cell::getState)
            .allMatch(CellState.FULL::equals);
    }
    
}
