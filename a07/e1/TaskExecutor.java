package a07.e1;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Predicate;

public interface TaskExecutor {

    /**
     * @param <T>
     * @param task
     * @param resultCondition
     * @param bound
     * @return the final result of executing a sequence of steps over the input task.
     * Execution carries on until the result passes the resultCondition, in which case the result is
     * put in an optional, or when a bound number of steps have been executed, and in this
     * case the empty optional is returned.
     * Before starting execution, the task should be reset. 
     */
    <T> Optional<T> executeUntilConditionOrBound(Task<T> task, Predicate<T> resultCondition, int bound);

    /**
     * @param <T>
     * @param task
     * @return an infinite iterator over the results one gets indefinitely executing steps of the task 
     * Before starting execution, the task should be reset. 
     */
    <T> Iterator<T> executeForever(Task<T> task);

}
