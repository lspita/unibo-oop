package a01a.e1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class AcceptorFactoryAdvancedImpl implements AcceptorFactory {

    @Override
    public Acceptor<String, Integer> countEmptyStringsOnAnySequence() {
        return generalised(
            0, 
            (s,i) -> Optional.of(i + (s.length() == 0 ? 1 : 0)), 
            s -> Optional.of(s)
        );
    }

    @Override
    public Acceptor<Integer, String> showAsStringOnlyOnIncreasingSequences() {
        return generalised(
            new ArrayList<Integer>(), 
            (i, l) -> {
                l.add(i);
                return Optional.of(l).filter(ll -> {
                    final var sorted = new ArrayList<>(ll);
                    Collections.sort(sorted);
                    return ll.equals(sorted);
                });
            }, 
            l -> Optional.of(String.join(":", l.stream().map(String::valueOf).toList()))
        );
    }

    @Override
    public Acceptor<Integer, Integer> sumElementsOnlyInTriples() {
        return generalised(
            new ArrayList<Integer>(),
            (i,l) -> { 
                l.add(i); 
                return Optional.of(l).filter(ll -> ll.size()<=3);
            }, 
            l -> Optional.of(l)
                .filter(ll -> ll.size()==3)
                .map(ll -> ll.stream().reduce(0, Integer::sum))
        );
    }

    @Override
    public <E, O1, O2> Acceptor<E, Pair<O1, O2>> acceptBoth(final Acceptor<E, O1> a1, final Acceptor<E, O2> a2) {
        return generalised(
            true,
            (e, ok) -> a1.accept(e) && a2.accept(e) ? Optional.of(true) : Optional.empty(),
            ok -> {
                final var end1 = a1.end();
                final var end2 = a2.end();
                return end1.isPresent() && end2.isPresent() ? 
                    Optional.of(new Pair<>(end1.get(), end2.get())) : 
                    Optional.empty();
            }
        );
    }

    @Override
    public <E, O, S> Acceptor<E, O> generalised(
        final S initial,
        final BiFunction<E, S, Optional<S>> stateFun,
        final Function<S, Optional<O>> outputFun
    ) {
        return new Acceptor<E,O>() {
            Optional<S> state = Optional.of(initial);
            @Override
            public boolean accept(final E e) {
                state.ifPresent(s -> state = stateFun.apply(e, s));
                return state.isPresent();
            }
            @Override
            public Optional<O> end() {
                return state.isPresent() ? outputFun.apply(state.get()) : Optional.empty();
            }
        };
    }

}
