package a02c.sol1;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class ReplacersFactoryImpl implements ReplacersFactory {

    private <T> List<T> replaceAtPosition(int index, List<T> source, List<T> destination){
        var l = new LinkedList<>(source);
        l.remove(index);
        var it = destination.listIterator(destination.size());
        while (it.hasPrevious()){
            l.add(index, it.previous());
        }
        return l;
    }

    /**
     * This is the key function of this solution
     * @param <T>
     * @param left is the simbol to be replaced
     * @param right is a supplier that gives the list to put in place of {@code right}
     * @param where is a function taking the indexes where {@code left} occurs, and returning the index where to replace
     * @return the resulting replacer
     */
    private <T> Replacer<T> generalReplacer(T left, Supplier<List<T>> right, Function<List<Integer>, List<Integer>> where){
        return (input, t) -> {
            var whereToReplace = where.apply(Stream
                    .iterate(0, i -> i < input.size(), i -> i+1)
                    .flatMap(i -> Stream.of(i).filter(j -> input.get(j).equals(t)))
                    .toList());
            return whereToReplace.stream().map(index -> replaceAtPosition(index, input, right.get())).toList();
        };
    }

    @Override
    public <T> Replacer<T> noReplacement() {
        return (input, t) -> generalReplacer(t, ()->List.of(), l ->List.of()).replace(input, t);
    }

    @Override
    public <T> Replacer<T> duplicateFirst() {
        return (input, t) -> generalReplacer(t, ()->List.of(t, t), l -> l.isEmpty() ? l : l.subList(0, 1)).replace(input, t);
    }

    @Override
    public <T> Replacer<T> translateLastWith(List<T> target) {
        return (input, t) -> generalReplacer(t, ()->target, l -> l.isEmpty() ? l : l.subList(l.size()-1, l.size())).replace(input, t);
    }

    @Override
    public <T> Replacer<T> removeEach() {
        return (input, t) -> generalReplacer(t, ()->List.of(), l -> l).replace(input, t);
    }

    @Override
    public <T> Replacer<T> replaceEachFromSequence(List<T> sequence) {
        return (input, t) -> {
            var it = sequence.iterator();
            return generalReplacer(t, ()->List.of(it.next()), l -> l.subList(0, Math.min(l.size(), sequence.size()))).replace(input, t);
        };
    }

}
