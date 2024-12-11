package it.unibo.es2;

public interface Logics {
    public enum CellState {
        EMPTY,
        FULL
    }

    public void toggle(Pair<Integer, Integer> pos);
    
    public CellState getState(Pair<Integer, Integer> pos);

    public boolean gameFinished();
}
