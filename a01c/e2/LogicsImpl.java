package a01c.e2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.stream.IntStream;

public class LogicsImpl implements Logics {
    
    private static final int MAX_VERETEXES = 4;

    private final List<Position> vertexes = new ArrayList<>(MAX_VERETEXES);
    private final List<BiPredicate<Position, Position>> conditions = List.of(
        (pos, vertex) -> pos.y() == vertex.y() && pos.x() > vertex.x(),
        (pos, vertex) -> pos.x() == vertex.x() && pos.y() > vertex.y(),
        (pos, vertex) -> pos.y() == vertex.y() && pos.x() == vertexes.getFirst().x()
    );
    private int currentCondition = -1;

    private Optional<List<Position>> square = Optional.empty();

    @Override
    public List<Position> hit(final Position position) {
        if (square.isPresent()) {
            reset();
        } else if (
            vertexes.isEmpty() ||
            conditions.get(currentCondition).test(position, vertexes.getLast())
        ) {
            vertexes.add(position);
            currentCondition++;
            if (vertexes.size() == MAX_VERETEXES) {
                createSquare();
            }
        } else {
            reset();
        }

        return vertexes;
    }

    @Override
    public Optional<List<Position>> getSquare() {
        return square;
    }

    private void reset() {
        vertexes.clear();
        square = Optional.empty();
        currentCondition = -1;
    }

    private void createSquare() {
        final var ne = vertexes.get(0);
        final var nw = vertexes.get(1);
        final var sw = vertexes.get(2);

        square = Optional.of(
            IntStream.range(ne.x() + 1, nw.x()).boxed()
                .flatMap(
                    x -> IntStream.range(ne.y() + 1, sw.y()).mapToObj(y -> new Position(x, y))
                ).toList()
        );
    }

}
