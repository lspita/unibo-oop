package a06.sol2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class LogicImpl implements Logic {

    private final List<List<Integer>> rows;

    public LogicImpl(int size) {
        this.rows = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < size; i++){
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < size; j++){
                row.add(random.nextInt(2)+1);
            }
            this.rows.add(row);
        }   
    }

    private int size(){
        return this.rows.size();
    }

    @Override
    public Optional<Integer> value(int x, int y) {
        return Optional.of(size()-y-1)
                .filter(e -> this.rows.get(x).size() > e)
                .map(e -> this.rows.get(x).get(e));
    }

    private Optional<Integer> firstFire(List<Integer> row){
        for (int i = 0; i < row.size() - 1;  i++){
            if (row.get(i) == row.get(i+1)){
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    private void fire(List<Integer> row){
        this.firstFire(row).ifPresent(i -> {
            row.remove((int)i); // it forces to remove at index i, not element i!
            row.set(i, row.get(i)*2);
        });    
    }

    @Override
    public void fire() {
        this.rows.forEach(this::fire);
    }

    @Override
    public boolean isOver() {
        return !this.rows.stream().anyMatch(r -> this.firstFire(r).isPresent());
    }

}
