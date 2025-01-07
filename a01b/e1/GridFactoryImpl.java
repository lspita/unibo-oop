package a01b.e1;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

public class GridFactoryImpl implements GridFactory {

    // IntStream.range() but as normal stream
    private static Stream<Integer> rangeStream(final int start, final int end) {
        return Stream.iterate(start, i -> i < end, i -> i + 1);
    }

    @Override
    public <E> Grid<E> create(final int rows, final int cols) {
        record Position(int x, int y) {}

        final var grid = new Grid<E>() {

            private final Map<Position, E> cells = new HashMap<>();

            @Override
            public int getRows() {
                return rows;
            }

            @Override
            public int getColumns() {
                return cols;
            }

            @Override
            public E getValue(final int row, final int column) {
                return cells.get(new Position(column, row));
            }

            private void setCell(final Position position, final E value) {
                cells.put(position, value);
            }

            private void setCells(final Stream<Position> positions, final E value) {
                positions.forEach(pos -> setCell(pos, value));
            }

            @Override
            public void setColumn(final int column, final E value) {
                setCells(rangeStream(0, rows).map(y -> new Position(column, y)), value);
            }

            @Override
            public void setRow(final int row, final E value) {
                setCells(rangeStream(0, cols).map(x -> new Position(x, row)), value);
            }

            @Override
            public void setBorder(final E value) {
                setRow(0, value);
                setRow(rows - 1, value);
                setColumn(0, value);
                setColumn(cols - 1, value);
            }

            @Override
            public void setDiagonal(final E value) {
                setCells(rangeStream(0, Math.min(rows, cols)).map(i -> new Position(i, i)), value);
            }

            @Override
            public Iterator<Cell<E>> iterator(final boolean onlyNonNull) {
                return cells.entrySet().stream()
                    .filter(e -> !onlyNonNull || e.getValue() != null)
                    .map(e -> new Cell<>(e.getKey().y, e.getKey().x, e.getValue()))
                    .sorted((c1, c2) -> {
                        final var rowCompare = Integer.compare(c1.getRow(), c2.getRow());
                        return rowCompare == 0 ? Integer.compare(c1.getColumn(), c2.getColumn()) : rowCompare;
                    }).iterator();
            }
            
        };
        rangeStream(0, cols)
            .flatMap(x -> rangeStream(0, cols).map(y -> new Position(x, y)))
            .forEach(pos -> grid.setCell(pos, null));
        return grid;
    }

}
