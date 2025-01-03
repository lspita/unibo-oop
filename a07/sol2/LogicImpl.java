package a07.sol2;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class LogicImpl implements Logic {

    private final Set<Pair<Integer,Integer>> cells = new HashSet<>();
    private final int size;

    public LogicImpl(int size) {
        this.size = size;
    }

    private Stream<Pair<Integer,Integer>> neighbourhood(int x, int y){
        return Stream.of(-1,0,1)
                .flatMap(xx -> Stream.of(-1,0,1).map(yy -> new Pair<>(x+xx,y+yy)))
                .filter(p -> p.getX()>=0)
                .filter(p -> p.getY()>=0)
                .filter(p -> p.getX()<size)
                .filter(p -> p.getY()<size);
    }

    @Override
    public boolean toggle(int x, int y) {
        long dim = neighbourhood(x, y).count();
        boolean toEnable = neighbourhood(x, y).filter(cells::contains).count() < dim/2;
        if (toEnable) {
            neighbourhood(x,y).forEach(cells::add);
        } else {
            neighbourhood(x, y).forEach(cells::remove);
        }
        return cells.size() > size*size/2;
    }

    @Override
    public boolean enabled(int x, int y) {
        return this.cells.contains(new Pair<>(x,y));
    }

    @Override
    public void reset() {
        this.cells.clear();
    }

              
}
