package a01c.sol1;

/**
 * Models an iterator. Typically infinite, but possibly also finite (via raising an exception)
 */
public interface SimpleIterator<X> {
    
    /**
     * @throws NoSuchElementException if the iteration is over
     * @return the next element of the iteration
     */
    X next();
}
    
