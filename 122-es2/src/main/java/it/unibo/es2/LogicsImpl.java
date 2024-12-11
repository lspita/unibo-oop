package it.unibo.es2;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {

    private final int size;
    private final List<List<CellState>> cells;

    public LogicsImpl(final int size) {
        this.size = size;
        this.cells = 
            IntStream.range(0, size)
                .mapToObj(
                    i -> IntStream.range(0, size)
                            .mapToObj(j -> CellState.EMPTY)
                            .collect(Collectors.toList())
                )
                .collect(Collectors.toList());
    }

    private boolean positionOutOfBounds(final Pair<Integer, Integer> pos) {
        return 
            pos.getY() < 0 || pos.getY() > this.size || 
            pos.getX() < 0 || pos.getX() > this.size;
    }

    private void checkValidPosition(final Pair<Integer, Integer> pos) {
        Objects.requireNonNull(pos);
        if (positionOutOfBounds(pos)) {
            throw new IllegalArgumentException(pos + " is out of bounds");
        }
    }

    private void setState(final Pair<Integer, Integer> pos, final CellState state) {
        checkValidPosition(pos);
        this.cells.get(pos.getY()).set(pos.getX(), state);
    }

    @Override
    public void toggle(final Pair<Integer, Integer> pos) {
        setState(pos, switch (getState(pos)) {
            case EMPTY -> CellState.FULL;
            case FULL -> CellState.EMPTY;
        });
    }

    @Override
    public CellState getState(Pair<Integer, Integer> pos) {
        checkValidPosition(pos);
        return this.cells.get(pos.getY()).get(pos.getX());
    }

    private boolean isLineFull(final List<CellState> line) {
        return line.stream().allMatch(CellState.FULL::equals);
    }

    @Override
    public boolean gameFinished() {
        return Stream.concat(
            this.cells.stream(), // rows
            IntStream.range(0, this.size) // columns
                .mapToObj(i -> 
                    this.cells.stream()
                        .map(row -> row.get(i))
                        .toList()
                )
        ).anyMatch(this::isLineFull);
    }
    
}
