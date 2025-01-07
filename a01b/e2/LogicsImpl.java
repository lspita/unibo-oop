package a01b.e2;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LogicsImpl implements Logics {

    private static final RandomGenerator RANDOM = RandomGenerator.getDefault();

    private final List<Position> mines;
    private final Map<Position, Boolean> grid;

    public LogicsImpl(final int size, final int mines) {
        
        this.mines = RANDOM.ints(0, size)
            .distinct()
            .limit(mines)
            .mapToObj(x -> new Position(x, RANDOM.nextInt(0, size)))
            .toList();
        System.out.println(this.mines);
        
        grid = IntStream.range(0, size).boxed()
            .flatMap(
                x -> IntStream.range(0, size)
                    .mapToObj(y -> new Position(x, y))
            )
            .filter(pos -> !this.mines.contains(pos))
            .collect(Collectors.toMap(Function.identity(), pos -> false));
    }

    @Override
    public Optional<Long> hit(final Position position) {
        if (mines.contains(position)) {
            System.out.println("Game over");
            return Optional.empty();
        }

        grid.put(position, true);
        if (grid.values().stream().allMatch(discovered -> discovered)) {
            System.out.println("Victory");
            return Optional.empty();
        }
        
        return Optional.of(
            IntStream.rangeClosed(position.x() - 1, position.x() + 1).boxed()
                .flatMap(
                    x -> IntStream.rangeClosed(position.y() - 1, position.y() + 1)
                        .mapToObj(y -> new Position(x, y))
                ).filter(mines::contains)
                .count()
        );
    }

}
