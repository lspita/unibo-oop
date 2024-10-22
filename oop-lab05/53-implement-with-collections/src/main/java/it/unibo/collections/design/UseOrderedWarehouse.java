package it.unibo.collections.design;

import it.unibo.collections.design.api.Product;
import it.unibo.collections.design.api.Warehouse;

import java.util.List;
import java.util.Set;

import static it.unibo.collections.test.Assertions.assertContentEqualsInAnyOrder;
import static it.unibo.collections.test.Assertions.assertContentEqualsInOrder;

/**
 *
 */
public final class UseOrderedWarehouse {

    private UseOrderedWarehouse() { }

    /**
     * @param args
     *            unused
     */
    public static void main(final String[] args) {
        /*
         * 4. First, solve the points in UseWarehouse, and ask the teacher for a correction.
         * Then continue with these exercises.
         */
        /*
         * 5. Implement a new class ComparableProduct implements Comparable<Product>
         */
        /*
         * 6. Implement OrderedWarehouse in such a way that `allProducts()` returns the products in lexicographic
         * order. Prefer solutions that reuse existing code.
         */
        /*
         * 7. Fix the construction of objects and make sure that the test passes, before asking for a correction.
         */
        final Product p1 = null; // new ComparableProduct("Apple", 100);
        final Product p2 = null; // new ComparableProduct("Banana", 30);
        final Product p3 = null; // new ComparableProduct("Watermelon", 10);
        final Warehouse warehouse = null; // new OrderedWarehouse();
        // Populate the warehouse
        warehouse.addProduct(p3);
        assertContentEqualsInOrder(List.of(p3), warehouse.allProducts());
        warehouse.addProduct(p1);
        assertContentEqualsInOrder(List.of(p1, p3), warehouse.allProducts());
        warehouse.addProduct(p2);
        // Check that the products get sorted correctly
        assertContentEqualsInOrder(List.of(p1, p2, p3), warehouse.allProducts());
        assertContentEqualsInAnyOrder(Set.of(p1.getName(), p2.getName(), p3.getName()), warehouse.allNames());
        // Changes to the returned set should not affect the warehouse
        final var modifiedProductSet = warehouse.allProducts();
        modifiedProductSet.remove(p1);
        assertContentEqualsInAnyOrder(List.of(p2, p3), modifiedProductSet);
        assertContentEqualsInAnyOrder(Set.of(p1.getName(), p2.getName(), p3.getName()), warehouse.allNames());
        assertContentEqualsInOrder(List.of(p1, p2, p3), warehouse.allProducts());
    }
}
