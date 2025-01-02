package a01c.e2;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {

    enum Action {
        SELECT_VERTEX_1,
        SELECT_VERTEX_2,
        INIT_SQUARE,
        EXPAND_SQUARE,
    }

    private Action currentAction = Action.SELECT_VERTEX_1;
    private final int lowerBound = 0;
    private final int upperBound;
    private Optional<Position> vertex1 = Optional.empty();
    private Optional<Position> vertex2 = Optional.empty();
    private Optional<Position> nextVertex1 = Optional.empty();
    private Optional<Position> nextVertex2 = Optional.empty();
    private List<Position> expandedPositions = Collections.emptyList();

    public LogicsImpl(final int size) {
        this.upperBound = size - 1;
    }

    @Override
    public void hit(final Position position) {
        if (this.isOver()) {
            throw new IllegalStateException("Game is already over");
        }
        if (this.isOutOfBounds(position)) {
            throw new IllegalArgumentException(
                new StringBuilder("Position ").append(position).append(" is out of bounds").toString()
            );
        }

        this.updateStatus();
        switch (this.currentAction) {
            case SELECT_VERTEX_1 -> this.vertex1 = Optional.ofNullable(position);
            case SELECT_VERTEX_2 -> this.vertex2 = Optional.ofNullable(position);
            case INIT_SQUARE -> this.initSquare();
            case EXPAND_SQUARE -> this.expandSquare();
        }
    }

    @Override
    public Optional<CellType> getType(final Position position) {
        if (this.vertex1.equals(Optional.ofNullable(position))) {
            return Optional.of(CellType.VERTEX_1);
        }
        else if (this.vertex2.equals(Optional.ofNullable(position))) {
            return Optional.of(CellType.VERTEX_2);
        }
        
        return this.expandedPositions.stream().filter(position::equals).findAny().map(p -> CellType.EXPANDED);
    }

    @Override
    public boolean isOver() {
        return this.currentAction == Action.EXPAND_SQUARE && this.isGridFilled();
    }
    
    private void initSquare() {
        final var vertices = this.getOrderedVertices();
        this.nextVertex1 = Optional.of(vertices.getX());
        this.nextVertex2 = Optional.of(vertices.getY());
        this.expandSquare();
    }

    private void expandSquare() {
        final var vertex1 = this.nextVertex1.get();
        final var vertex2 = this.nextVertex2.get();
        this.expandedPositions = Stream.iterate(vertex1.x(), x -> x <= vertex2.x(), x -> x + 1)
            .flatMap(x -> IntStream.rangeClosed(vertex1.y(), vertex2.y()).mapToObj(y -> new Position(x, y)))
            .filter(this::isVertex)
            .collect(Collectors.toList());

        this.nextVertex1 = Optional.of(new Position(vertex1.x() - 1, vertex1.y() - 1));
        this.nextVertex2 = Optional.of(new Position(vertex2.x() - 1, vertex2.y() + 1));
    }

    private Pair<Position, Position> getOrderedVertices() {
        var vertex1 = this.vertex1.get();
        var vertex2 = this.vertex2.get();
        if (vertex1.x() >= vertex2.x() && vertex1.y() >= vertex2.y()) {
            final var tmp = vertex1;
            vertex1 = vertex2;
            vertex2 = tmp;
        }
        return new Pair<>(vertex1, vertex2);
    }

    private boolean isVertex(final Position position) {
        return Optional.of(position).equals(vertex1) || Optional.of(position).equals(vertex2);
    }

    private boolean isOutOfBounds(final Position position) {
        return 
            position.x() < this.lowerBound || position.x() > this.upperBound ||
            position.y() < this.lowerBound || position.y() > this.upperBound;
    }

    private void updateStatus() {
        this.currentAction = switch (this.currentAction) {
            case SELECT_VERTEX_1 -> this.vertex1.isPresent() ? Action.SELECT_VERTEX_2 : this.currentAction;
            case SELECT_VERTEX_2 -> this.vertex2.isPresent() ? Action.INIT_SQUARE : this.currentAction;
            case INIT_SQUARE -> Action.EXPAND_SQUARE;
            default -> this.currentAction;
        };
    }

    private boolean isGridFilled() {
        return false;
    }

}
