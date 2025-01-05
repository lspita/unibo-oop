package a06.e2;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {
    
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 2;
    private static final RandomGenerator RANDOM = RandomGenerator.getDefault();

    private final Map<Position, Integer> cells;
    private final int size;

    public LogicsImpl(final int size) {
        this.size = size;
        cells = Stream.iterate(0, x -> x < size, x -> x + 1)
            .flatMap(
                x -> Stream.iterate(0, y -> y < size, y -> y + 1)
                    .map(y -> new Position(x, y))
            ).collect(Collectors.toMap(Function.identity(), pos -> RANDOM.nextInt(MIN_VALUE, MAX_VALUE + 1)));
    }

    @Override
    public boolean fire() {
        final var selectedPair = columns()
            .map(
                c -> Stream.iterate(0, i -> i < c.size() - 1, i -> i + 1).map(i -> new Pair<>(c.get(i), c.get(i + 1))).findFirst()
            ).filter(Optional::isPresent)
            .map(Optional::get)
            .filter(pair -> cells.get(pair.getX()) == cells.get(pair.getY()))
            .peek(p -> {
                final var toRemove = p.getY();
                final var toUpdate = p.getX();
                cells.put(p.getY(), cells.get(toRemove) + cells.get(toUpdate));
                cells.remove(toRemove);
                final var positionsOnTop = columns()
                    .filter(c -> c.contains(toUpdate))
                    .findFirst().get().stream() // position comes from a column, so there is a column with it
                    .filter(pos -> pos.y() < toUpdate.y())
                    .map(pos -> Map.entry(pos, cells.get(pos)))
                    .toList();
                
                positionsOnTop.stream().map(Map.Entry::getKey).forEach(cells::remove);
                Stream.iterate(0, i -> i  < positionsOnTop.size() , i -> i + 1)
                    .forEach(i -> cells.put(
                        new Position(toUpdate.x(), toUpdate.y() - 1 - i), 
                        positionsOnTop.get(i).getValue()
                    ));
            }).findAny();

        return selectedPair.isEmpty();
    }

    private Stream<List<Position>> columns() {
        return cells.keySet().stream()
            .collect(Collectors.groupingBy(Position::x))
            .values().stream()
            .map(c -> c.stream().sorted((p1, p2) -> - Integer.compare(p1.y(), p2.y())).toList());
    }

    @Override
    public Optional<Integer> getValue(Position position) {
        return Optional.ofNullable(cells.get(position));
    }

}
