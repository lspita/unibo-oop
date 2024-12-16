package a01b.e2;

import java.util.Optional;

public interface Logics {

    static Logics getDefault(final int size) {
        return new LogicsImpl(size);
    }

    void hitCell(final Cell cell);
    
    Optional<Integer> getCellValue(final Cell cell);

    boolean isOver();

}
