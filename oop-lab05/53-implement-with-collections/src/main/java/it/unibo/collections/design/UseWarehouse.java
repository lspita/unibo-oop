package it.unibo.collections.design;

import it.unibo.collections.design.api.Product;
import it.unibo.collections.design.api.Warehouse;

import java.util.Set;

import static it.unibo.collections.test.Assertions.assertContentEqualsInAnyOrder;

/**
 * Simple test class for {@link Warehouse}.
 *
 */
public final class UseWarehouse {

    private UseWarehouse() {
    }

    /**
     * @param args
     *            unused
     */
    public static void main(final String[] args) {
        /*
         * Realize the classes:
         *
         * 1. WarehouseImpl implements Warehouse. Such class should use internally
         * a java.util.LinkedHashSet. Differently from HashSet the iteration order
         * of LinkedHashSet is predictable and reproducible even when using object
         * that don't implement a custom hashCode.
         * Additionally, LinkedHashSet and HashSet have similar performances:
         * https://www.artima.com/weblogs/viewpost.jsp?thread=122295
         */
        /*
         * 2. ProductImpl implements Product. Remember that two products are the
         * same if their name is the same.
         *
         * Read VERY carefully the comments in the interfaces and use them to prepare the implementations.
         */
        /*
         * 3. Fix the construction of objects in this test.
         */
        final Product p1 = null; // new ProductImpl("p1", 100);
        final Product p2 = null; // new ProductImpl("p2", 30);
        final Product p3 = null; // new ProductImpl("p3", 10);
        final Warehouse warehouse = null; // new WarehouseImpl();
        warehouse.addProduct(p3);
        assertContentEqualsInAnyOrder(Set.of(p3), warehouse.allProducts());
        warehouse.addProduct(p1);
        assertContentEqualsInAnyOrder(Set.of(p1, p3), warehouse.allProducts());
        warehouse.addProduct(p2);
        assertContentEqualsInAnyOrder(Set.of(p1, p2, p3), warehouse.allProducts());
        assertContentEqualsInAnyOrder(Set.of(p1.getName(), p2.getName(), p3.getName()), warehouse.allNames());
        // Changes to the returned set should not affect the warehouse
        final var modifiedProductSet = warehouse.allProducts();
        modifiedProductSet.remove(p1);
        assertContentEqualsInAnyOrder(Set.of(p1.getName(), p2.getName(), p3.getName()), warehouse.allNames());
        assertContentEqualsInAnyOrder(Set.of(p1, p2, p3), warehouse.allProducts());
        assertContentEqualsInAnyOrder(Set.of(p2, p3), modifiedProductSet);
    }
}
