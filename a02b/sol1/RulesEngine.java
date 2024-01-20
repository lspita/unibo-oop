package a02b.sol1;

import java.util.List;

/**
 * Models a "rules engine", which essentially creates many solutions (lists) out of a single input (list).
 * The input is provided by a method @resetInput, and the output is given by an iteration (with methods having same 
 * semantics of next/hasNext). The rules to apply are assumed to be embedded in the engine when it is created -- see the factory.
 * By definition, an engine applies the rules in all possible ways and until it can, and gives all possible solutions.
 */
public interface RulesEngine<T> {

    /**
     * @param input is a new input to handle: the iteration starts again
     */
    void resetInput(List<T> input);

    /**
     * @return whether there is another solution to produce (same semantics of Iterator.hasNext)
     */
    boolean hasOtherSolutions();

    /**
     * @return the next solution found (same semantics of Iterator.next)
     */
    List<T> nextSolution();
}
