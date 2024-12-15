package a01a.e2;

import java.util.List;

public interface Logics {

    static Logics getDefault(final int size) {
        return new LogicsImpl(size);
    }
    
    List<Cell> hitCell(Cell cell);

    boolean toQuit();

}
