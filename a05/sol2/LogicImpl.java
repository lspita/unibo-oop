package a05.sol2;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogicImpl implements Logic {
    private Pair<Integer,Integer> human;
    private Pair<Integer,Integer> computer;
    private final Random random = new Random();
    private final int size;

    public LogicImpl(int size) {
        this.size = size;
    }

    @Override
    public void reset() {
        this.human = new Pair<>(this.random.nextInt(size/2)*2, size-1);
        int y = this.random.nextInt(2);
        this.computer = new Pair<>(this.random.nextInt(size/2)*2+y-1, y);
    }

    private Set<Pair<Integer,Integer>> moves(boolean up, Pair<Integer,Integer> mover){
        return Stream.of(new Pair<>(0, up ? -1 : 1)) 
            .flatMap(p -> Stream.of(new Pair<>(1, p.getY()),new Pair<>(-1, p.getY())))
            .map(p -> new Pair<>(p.getX() + mover.getX(), p.getY() + mover.getY()))
            .filter(p -> p.getX() >= 0 && p.getX() < size)
            .filter(p -> p.getY() >= 0 && p.getY() < size)
            .collect(Collectors.toSet());
    }

    @Override
    public boolean humanMove(int x, int y) {
        var moves = moves(true, this.human);
        if (moves.contains(new Pair<>(x,y))){
            this.human = new Pair<>(x,y);
            return true;
        }
        return false;
    }

    @Override
    public void computerMove() {
        var moves = moves(false, this.computer);
        System.out.println(moves);
        if (moves.contains(this.human)){
            this.computer = this.human;
        } else {
            this.computer = new ArrayList<>(moves).get(random.nextInt(moves.size()));
        }

    }

    @Override
    public boolean isOver() {
        return this.human.equals(this.computer) || ( this.human.getY()==0 || this.computer.getY()==size-1);
    }

    @Override
    public boolean hasHuman(int x, int y) {
        return this.human.equals(new Pair<>(x,y));
    }

    @Override
    public boolean hasComputer(int x, int y) {
        return this.computer.equals(new Pair<>(x,y));
    }
}
