package a07.sol1;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class TaskExecutorImpl implements TaskExecutor {

    @Override
    public <T> Optional<T> executeUntilConditionOrBound(Task<T> task, Predicate<T> resultCondition, int bound) {
        task.reset();
        for (int i=0; i<bound; i++){
            task.computationStep();
            var temporaryResult = task.temporaryResult();
            if (resultCondition.test(temporaryResult)){
                return Optional.of(temporaryResult);
            }
        }
        return Optional.empty();
    }

    @Override
    public <T> Iterator<T> executeForever(Task<T> task) {
        task.reset();
        return Stream.generate(()->{ task.computationStep(); return task.temporaryResult();}).iterator();
    }

}
