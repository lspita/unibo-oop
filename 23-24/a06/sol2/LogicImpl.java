package a06.sol2;

import java.util.*;

public class LogicImpl implements Logic {
    private final Map<Position,Integer> values = new HashMap<>();
    private final Set<Position> shown = new HashSet<>();
    private List<Position> selected = new ArrayList<>();

    public LogicImpl(int size) {
        Random random = new Random();
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                values.put(new Position(i, j), random.nextInt(6)+1);
            }
        }
    }

    @Override
    public void hit(Position p) {
        if (this.selected.size() == 2) {
            this.selected.clear();
        }
        this.selected.add(p);
        if (this.selected.size() == 2) {
            if (this.values.get(selected.get(0)).equals(this.values.get(this.selected.get(1)))){
                this.shown.add(this.selected.get(0));
                this.shown.add(this.selected.get(1));
            }
        }
    }

    @Override
    public Optional<Integer> found(Position p) {
        return Optional.of(p).filter(pp -> this.shown.contains(pp)).map(this.values::get);
    }

    @Override
    public Optional<Integer> temporary(Position p) {
        return Optional.of(p).filter(pp -> this.selected.contains(pp)).map(this.values::get);
    }

    @Override
    public boolean isOver() {
        List<Integer> remaining = values.keySet().stream().filter(p -> !this.shown.contains(p)).map(values::get).toList();
        return remaining.stream().distinct().count() == remaining.size();
    }
}
