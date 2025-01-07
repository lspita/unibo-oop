package a01a.e2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.random.RandomGenerator;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {

    private static final RandomGenerator RANDOM = RandomGenerator.getDefault();

    private int tries = 5;
    private final List<Position> boat;
    private final List<Position> firedCells = new ArrayList<>();

    public LogicsImpl(final int size, final int boatSize) {
        final var boatPivot = new Position(RANDOM.nextInt(size - boatSize + 1), RANDOM.nextInt(size));
        System.out.println(boatPivot);
        boat = Stream.iterate(boatPivot.x(), x -> x < boatPivot.x() + boatSize, x -> x + 1)
            .map(x -> new Position(x, boatPivot.y()))
            .toList();

        System.out.println(boat);
    }

    @Override
    public boolean fire(final Position position) {
        if (firedCells.contains(position) || getGameState().isPresent()) {
            throw new IllegalArgumentException();
        }

        firedCells.add(position);
        tries--;
        return boat.contains(position);
    }

    @Override
    public Optional<Ending> getGameState() {
        return firedCells.containsAll(boat) ?
            Optional.of(Ending.VICTORY) :
            tries == 0 ? Optional.of(Ending.GAME_OVER) : Optional.empty();
    }
    
}
