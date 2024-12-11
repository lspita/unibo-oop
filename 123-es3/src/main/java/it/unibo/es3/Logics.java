package it.unibo.es3;

import java.util.List;

public interface Logics {
    public enum CellState {
        EMPTY,
        FULL
    }

    public static Logics getDefault() {
        return new LogicsImpl();
    }
    
    public void initGrid(int size);

    public void expand();

    public List<CellState> getCellStates();
    
    public boolean isGridFull();

}
