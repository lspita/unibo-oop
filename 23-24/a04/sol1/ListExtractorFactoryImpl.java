package a04.sol1;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class ListExtractorFactoryImpl implements ListExtractorFactory {

    abstract class AbstractListExtractor<X,Y> implements ListExtractor<X,Y> {

        @Override
        public Y extract(List<X> list) {
            boolean extracting = false;
            Y output = initialResult();
            for (var x: list){
                if (!extracting && start(x)){
                    extracting = true;
                }
                if (extracting && stop(output, x)){
                    break;
                } 
                if (extracting){
                    output = compute(output, x);
                }    
            }
            return output;
        }

        protected abstract Y compute(Y output, X x);

        protected abstract boolean stop(Y output, X x);

        protected abstract boolean start(X x);

        protected abstract Y initialResult();
    }

    @Override
    public <X> ListExtractor<X, Optional<X>> head() {
        return new AbstractListExtractor<X,Optional<X>>() {

            @Override
            protected Optional<X> compute(Optional<X> output, X x) {
                return Optional.of(x);
            }

            @Override
            protected boolean stop(Optional<X> output, X x) {
                return output.isPresent();
            }

            @Override
            protected boolean start(X x) {
                return true;
            }

            @Override
            protected Optional<X> initialResult() {
                return Optional.empty();
            }           
        };
    }

    @Override
    public <X, Y> ListExtractor<X, List<Y>> collectUntil(Function<X, Y> mapper, Predicate<X> stopCondition) {
        return new AbstractListExtractor<X,List<Y>>() {

            @Override
            protected List<Y> compute(List<Y> output, X x) {
                output.add(mapper.apply(x));
                return output;
            }

            @Override
            protected boolean stop(List<Y> output, X x) {
                return stopCondition.test(x);
            }

            @Override
            protected boolean start(X x) {
                return true;
            }

            @Override
            protected List<Y> initialResult() {
                return new LinkedList<>();
            }
        };
    }

    @Override
    public <X> ListExtractor<X, List<List<X>>> scanFrom(Predicate<X> startCondition) {
        return new AbstractListExtractor<X,List<List<X>>>() {

            @Override
            protected List<List<X>> compute(List<List<X>> output, X x) {
                var newList = new LinkedList<>(output.isEmpty()? new LinkedList<>() : output.get(output.size()-1));
                newList.add(x);
                output.add(newList);
                return output;
            }

            @Override
            protected boolean stop(List<List<X>> output, X x) {
                return false;
            }

            @Override
            protected boolean start(X x) {
                return startCondition.test(x);
            }

            @Override
            protected List<List<X>> initialResult() {
                return new LinkedList<>();
            }
        };
    }

    @Override
    public <X> ListExtractor<X, Integer> countConsecutive(X x0) {
        return new AbstractListExtractor<X,Integer>() {

            @Override
            protected Integer compute(Integer output, X x) {
                return output + 1;
            }

            @Override
            protected boolean stop(Integer output, X x) {
                return !x0.equals(x);
            }

            @Override
            protected boolean start(X x) {
                return x.equals(x0);
            }

            @Override
            protected Integer initialResult() {
                return 0;
            }
            
        };        
    }

}
