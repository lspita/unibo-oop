package a01a.e2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class LogicsImpl implements Logics {
    
    private final int size;
    private final List<Position> activeCells = new ArrayList<>();
    private Optional<List<Position>> vertexes = Optional.empty();

    public LogicsImpl(final int size) {
        this.size = size;
    }

    @Override
    public List<Position> hit(final Position position) {
        if (vertexes.isPresent()) {
            activeCells.clear();
            vertexes = Optional.empty();
        } else if (isOnBorder(position)) {
            return activeCells;
        } else if (!activeCells.contains(position)) {
            activeCells.add(position);
        } else {
            final var minX = activeCells.stream()
                .min(Position::compareX)
                .get().x() - 1;
            final var maxX = activeCells.stream()
                .max(Position::compareX)
                .get().x() + 1;
            final var minY = activeCells.stream()
                .min(Position::compareY)
                .get().y() - 1;
            final var maxY = activeCells.stream()
                .max(Position::compareY)
                .get().y() + 1;
            
            vertexes = Optional.of(List.of(
                new Position(minX, minY), // NE
                new Position(maxX, minY), // NW
                new Position(minX, maxY), // SE
                new Position(maxX, maxY)  // SW
            ));
        }
        return activeCells;
    }

    @Override
    public Optional<List<Position>> getVertexes() {
        return vertexes;
    }

    @Override
    public Optional<List<Position>> getRectangle() {
        return vertexes.map(v -> {
            final var ne = v.getFirst();
            final var sw = v.getLast();
            return IntStream.rangeClosed(ne.x(), sw.x()).boxed()
                .flatMap(
                    x -> IntStream.rangeClosed(ne.y(), sw.y())
                        .mapToObj(y -> new Position(x, y))
                ).filter(
                    pos -> 
                        pos.x() > ne.x() && pos.x() < sw.x() && (pos.y() == ne.y() || pos.y() == sw.y()) ||
                        pos.y() > ne.y() && pos.y() < sw.y() && (pos.x() == ne.x() || pos.x() == sw.x())
                ).toList();
        });
    }

    private boolean isOnBorder(final Position position) {
        return
            position.x() == 0 || position.x() == size - 1 ||
            position.y() == 0 || position.y() == size - 1;
    }

}
