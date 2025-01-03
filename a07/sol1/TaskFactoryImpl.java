package a07.sol1;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TaskFactoryImpl implements TaskFactory {

    @Override
    public Task<Integer> counter() {
        return new Task<>(){

            private int counter = -1;

            @Override
            public void computationStep() {
                counter = counter + 1;
            }

            @Override
            public Integer temporaryResult() {
                return counter;
            }

            @Override
            public void reset() {
                counter = -1;
            }};
    }

    @Override
    public Task<List<Integer>> fibonacciSequenceCreator() {
        return new Task<>() {

            private final List<Integer> list = new LinkedList<>();

            @Override
            public void reset() {
                list.clear();
            }

            @Override
            public void computationStep() {
                list.add(list.size() < 2 ? list.size() : list.get(list.size()-1) + list.get(list.size()-2));
            }

            @Override
            public List<Integer> temporaryResult() {
                return Collections.unmodifiableList(list);
            }
        };

    }

    @Override
    public Task<Set<Integer>> removeBiggerThan(Set<Integer> initialSet, int bound) {
        return new Task<>(){

            private final Set<Integer> set = new HashSet<>(initialSet);

            @Override
            public void reset() {
                set.clear();
                set.addAll(initialSet);
            }

            @Override
            public void computationStep() {
                this.set.stream().max((i,j)->i-j).filter(i -> i>=bound).ifPresent(this.set::remove);
            }

            @Override
            public Set<Integer> temporaryResult() {
                return Collections.unmodifiableSet(this.set);
            }

        };
    }

    

}
