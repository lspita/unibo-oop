package a05.sol2;

import java.util.*;
import java.util.stream.Stream;

public class LogicImpl implements Logic {

    private final int size;
    private Position player;
    private Position enemy;

    public LogicImpl(int size) {
        this.size = size;
        final Random random = new Random();
        this.player = new Position(random.nextInt(size), random.nextInt(size));
        do {
            this.enemy = new Position(random.nextInt(size), random.nextInt(size));
        } while (validNeighbours(this.enemy).anyMatch(p -> p.equals(this.player)));
    }

    private Stream<Position> validNeighbours(Position position){
        return Stream.of(
            new Position(1,0), new Position(0,1), new Position(-1,0), new Position(0,-1),
            new Position(1,1), new Position(-1,1),  new Position(-1,-1), new Position(1,-1)
        ).map(p -> new Position(position.x() + p.x(), position.y() + p.y()))
        .filter(p -> p.x() >= 0 && p.y() >= 0)
        .filter(p -> p.x() < this.size && p.y() < this.size);
    }

    @Override
    public boolean hit(Position position) {
        if (this.validNeighbours(this.player).anyMatch(p -> p.equals(position))){
            this.player = position;
            return true;
        }
        return false;
    }

    @Override
    public Position getPlayer() {
        return this.player;
    }

    @Override
    public void moveEnemy() {
        if (validNeighbours(this.enemy).anyMatch(p -> p.equals(this.player))){
            var moves = new ArrayList<>(
                validNeighbours(this.enemy)
                    .filter(p -> !p.equals(this.player))
                    .filter(p -> !validNeighbours(this.player).anyMatch(p2 -> p2.equals(p)))
                    .toList());
            if (moves.size() > 0){
                Collections.shuffle(moves);
                this.enemy = moves.get(0);
            }
        }
    }

    @Override
    public Position getEnemy() {
        return this.enemy;
    }

    @Override
    public boolean isOver() {
        return this.player.equals(this.enemy);
    }
}
