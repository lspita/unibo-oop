package a01a.e2;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {
    
    private final Map<Position, CellState> cells;

    public LogicsImpl(final int size) {
        cells = Stream.iterate(0, x -> x < size, x -> x + 1)
            .flatMap(
                x -> Stream.iterate(0, y -> y < size, y -> y + 1)
                    .map(y -> new Position(x, y))
            ).collect(Collectors.toMap(Function.identity(), (pos) -> CellState.EMPTY));
    }

    @Override
    public void hit(final Position position) {
        getPin().ifPresentOrElse(
            pin -> Stream.iterate(Math.min(pin.x(), position.x()), x -> x <= Math.max(pin.x(), position.x()), x -> x + 1)
                .flatMap(
                    x -> Stream.iterate(Math.min(pin.y(), position.y()), y -> y <= Math.max(pin.y(), position.y()), y -> y + 1)
                        .map(y -> new Position(x, y))
                ).forEach(pos -> cells.put(pos, CellState.ACTIVE)), 
            () -> cells.put(position, CellState.PIN)
        );
        if (cells.values().stream().allMatch(CellState.ACTIVE::equals)) {
            cells.keySet().forEach(pos -> cells.put(pos, CellState.BLOCKED));
        }
    }

    @Override
    public CellState getState(final Position position) {
        return cells.get(position);
    }

    private Optional<Position> getPin() {
        return cells.entrySet().stream()
            .filter(e -> e.getValue() == CellState.PIN)
            .map(Map.Entry::getKey)
            .findFirst();
    }

}
