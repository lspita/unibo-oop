package a07.e1;

import java.util.List;
import java.util.Set;

public interface TaskFactory {
    
    /**
     * @return a task that simply increments each time a counter starting from 0
     */
    Task<Integer> counter();

    /**
     * @return a task that computes element-by-element the fibonacci sequence
     * i.e.: 0,1,1,2,3,5,8,13,21,34,...
     */
    Task<List<Integer>> fibonacciSequenceCreator();

    /**
     * @param set
     * @param bound
     * @return a task that removes from the input set all elements bigger than bound --
     * one at a time is removed, starting from the bigger
     */
    Task<Set<Integer>> removeBiggerThan(Set<Integer> set, int bound); 

}
