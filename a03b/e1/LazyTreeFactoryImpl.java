package a03b.e1;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class LazyTreeFactoryImpl implements LazyTreeFactory {

    @Override
    public <X> LazyTree<X> constantInfinite(final X value) {
        return cons(Optional.of(value), () -> constantInfinite(value), () -> constantInfinite(value));
    }

    @Override
    public <X> LazyTree<X> fromMap(final X root, final Map<X, Pair<X, X>> map) {
        return cons(
            Optional.of(root),
            () -> map.containsKey(root) ? fromMap(map.get(root).getX(), map) : empty(), 
            () -> map.containsKey(root) ? fromMap(map.get(root).getY(), map) : empty()
        );
    }

    private <X> LazyTree<X> empty() {
        return cons(Optional.empty(), this::empty, this::empty);
    }

    @Override
    public <X> LazyTree<X> cons(
        final Optional<X> root, 
        final Supplier<LazyTree<X>> leftSupp, 
        final Supplier<LazyTree<X>> rightSupp
    ) {
        return new LazyTree<X>() {
            @Override
            public boolean hasRoot() {
                return root.isPresent();
            }
            @Override
            public X root() {
                return root.get();
            }
            @Override
            public LazyTree<X> left() {
                return hasRoot() ? leftSupp.get(): empty();
            }
            @Override
            public LazyTree<X> right() {
                return hasRoot() ? rightSupp.get() : empty();
            }
        };
    }

    @Override
    public <X> LazyTree<X> fromTwoIterations(final X root, final UnaryOperator<X> leftOp, final UnaryOperator<X> rightOp) {
        return cons(
            Optional.of(root), 
            () -> fromTwoIterations(leftOp.apply(root), leftOp, rightOp), 
            () -> fromTwoIterations(rightOp.apply(root), leftOp, rightOp)
        );
    }

    @Override
    public <X> LazyTree<X> fromTreeWithBound(final LazyTree<X> tree, final int bound) {
        return tree.hasRoot() && bound > 0 ?
            cons(
                Optional.of(tree.root()), 
                () -> fromTreeWithBound(tree.left(), bound - 1), 
                () -> fromTreeWithBound(tree.right(), bound - 1)
            ) :
            empty();
    }

}
