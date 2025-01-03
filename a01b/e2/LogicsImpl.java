package a01b.e2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {

    private static final int ANGLES_DISTANCE = 1;

    private final Map<Position, Boolean> positions = new HashMap<>();
    private Optional<Position> lastHit = Optional.empty();

    public LogicsImpl(final int size) {
        Stream.iterate(0, x -> x < size, x -> x + 1)
            .flatMap(x -> Stream.iterate(0, y -> y < size, y -> y + 1).map(y -> new Position(x, y)))
            .forEach(pos -> positions.put(pos, false));
    }

    @Override
    public List<Position> hit(final Position position) {
        if (isOver()) {
            throw new IllegalStateException("Game is already over");
        }
        if (isOutOfBounds(Objects.requireNonNull(position))) {
            throw new IllegalArgumentException(
                new StringBuilder("Position ").append(position).append(" is out of bounds").toString()
            );
        }

        getAngles(position).forEach(pos -> positions.put(pos, !positions.get(pos)));
        lastHit = Optional.ofNullable(position);
        return positions.entrySet().stream()
            .filter(Map.Entry::getValue)
            .map(Map.Entry::getKey)
            .toList();
    }

    @Override
    public boolean isOver() {
        if (lastHit.isEmpty()) {
            return false;
        }
        final var angles = getAngles(lastHit.get());
        return 
            angles.size() == 4 && 
            angles.stream().filter(positions::get).count() == 1 &&
            angles.stream().filter(Predicate.not(positions::get)).count() == 3;
    }

    private boolean isOutOfBounds(final Position position) {
        return !positions.containsKey(position);
    }
    
    private boolean isInBounds(final Position position) {
        return !isOutOfBounds(position);
    }

    private List<Position> getAngles(final Position position) {
        return Stream.of(position.x() - ANGLES_DISTANCE, position.x() + ANGLES_DISTANCE)
            .flatMap(x -> Stream.of(position.y() - ANGLES_DISTANCE, position.y() + ANGLES_DISTANCE).map(y -> new Position(x, y)))
            .filter(this::isInBounds)
            .toList();
    }

}
