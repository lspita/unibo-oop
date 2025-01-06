package a07.e1;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class TaskExecutorImpl implements TaskExecutor {

    private <T> Stream<T> executionStream(final Task<T> task) {
        return Stream.generate(() -> {
            task.computationStep();
            return task.temporaryResult();
        });
    }

    @Override
    public <T> Optional<T> executeUntilConditionOrBound(final Task<T> task, final Predicate<T> resultCondition, final int bound) {
        return executionStream(task).limit(bound).filter(resultCondition::test).findFirst();
    }

    @Override
    public <T> Iterator<T> executeForever(final Task<T> task) {
        return executionStream(task).iterator();
    }

}
