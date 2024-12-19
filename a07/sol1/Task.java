package a07.sol1;

/**
 * This interface models a task, resettable, executable step-by-step, and with the ability
 * of showing the partial result obtained so far. An example task is a counter, or a task to sort 
 * a list, or a task to create a fibonacci sequence.
 * 
 * T is the type of the result of computation.
 */
public interface Task<T> {

    /**
     * Resets the task: it starts again from beginning.
     */
    void reset();

    /**
     * It executes a single computation step, which has a side-effect on the task state.
     */
    void computationStep();

    /**
     * @return the current result of task computation
     */
    T temporaryResult();

}
